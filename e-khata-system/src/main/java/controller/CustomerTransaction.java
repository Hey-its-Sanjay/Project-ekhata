package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Customer;
import model.CustomerFinancialSummary;
import model.Transaction;
import service.CustomerDao;
import utils.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.CUSTOMER_TRANSACTIONS_SERVLET)
@MultipartConfig
public class CustomerTransaction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DAO object for interacting with customer data
    private CustomerDao customerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.customerDao = new CustomerDao(); // Initialize the DAO object
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customerId = req.getParameter(StringUtils.CUSTOMER_ID);
        if (customerId != null && !customerId.isEmpty()) {
            int customerIdInt = Integer.parseInt(customerId);

            // Fetch the customer details
            Customer customer = customerDao.getCustomer(customerIdInt);
            req.getSession().setAttribute(StringUtils.CUSTOMER_OBJECT, customer);

            // Fetch the financial summary for the customer
            CustomerFinancialSummary summary = customerDao.getCustomerFinancialSummary(customerIdInt);
            req.getSession().setAttribute(StringUtils.CUSTOMER_SUMMARY_OBJECT, summary);

            // Fetch the transaction history for the customer
            List<Transaction> transaction = customerDao.getTransaction(customerIdInt);
            req.getSession().setAttribute(StringUtils.TRANSACTION_SUMMARY_OBJECT, transaction);
        }

        // Forward the request to the customer transaction page
        req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String transactionType = req.getParameter(StringUtils.TRANSACTION_TYPE);

        if ("Credit".equals(transactionType)) {
            LocalDateTime transactionDate = LocalDateTime.now();
            String amountStr = req.getParameter(StringUtils.TRANSACTION_AMOUNT);
            String remarks = req.getParameter(StringUtils.REMARKS);
            String customerIdStr = req.getParameter(StringUtils.CUSTOMER_ID);
            int customerIdInt = Integer.parseInt(customerIdStr);
            String paymentMode = req.getParameter(StringUtils.PAYMENT_MODE);
            Part image = req.getPart(StringUtils.TRANSACTION_IMAGE);

            String imageName = "";
            if (!"cash".equalsIgnoreCase(paymentMode) && image != null && image.getSize() > 0) {
                imageName = image.getSubmittedFileName();
                try (FileOutputStream fos = new FileOutputStream(StringUtils.IMAGE_DIRECTORY + imageName);
                     InputStream is = image.getInputStream()) {
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    fos.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();  // Consider logging this instead of printStackTrace
                    req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, "Failed to save image.");
                    req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                    return;
                }
            }

            // Calculate the new net amount
            double previousNetAmount = customerDao.getPreviousNetBalance(customerIdInt);
            double newNetAmount = previousNetAmount - Double.parseDouble(amountStr);

            // Create a new transaction object
            Transaction newTransaction = new Transaction(transactionDate, Double.parseDouble(amountStr), newNetAmount, transactionType, remarks, imageName, customerIdInt, paymentMode);

            // Save the transaction and handle the result
            int result = customerDao.saveCustomerTransaction(newTransaction);
            switch (result) {
                case 1:
                    req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.TRANSACTION_ADDED_SUCCESSFULLY_MESSAGE);
                    resp.sendRedirect(req.getContextPath() + StringUtils.CUSTOMER_TRANSACTIONS_SERVLET);
                    break;
                case 0:
                case -1:
                    req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.TRANSACTION_ADDED_ERROR_MESSAGE);
                    req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                    break;
            }
        } else {
            LocalDateTime transactionDate = LocalDateTime.now();
            String reminderDateStr = req.getParameter(StringUtils.REMINDER_DATE);
            String amountStr = req.getParameter(StringUtils.TRANSACTION_AMOUNT);
            String remarks = req.getParameter(StringUtils.REMARKS);
            String customerIdStr = req.getParameter(StringUtils.CUSTOMER_ID);
            int customerIdInt = Integer.parseInt(customerIdStr);
            Part image = req.getPart(StringUtils.TRANSACTION_IMAGE);

            // Parse and validate the reminder date
            LocalDate reminderDate;
            try {
                reminderDate = LocalDate.parse(reminderDateStr);
            } catch (DateTimeParseException e) {
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, "Invalid reminder date format.");
                req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                return;
            }

            // Check if the reminder date is in the past
            if (reminderDate.isBefore(LocalDate.now())) {
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, "Reminder date cannot be in the past.");
                req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                return;
            }

            // Save the image if provided
            String imageName = "";
            if (image != null && image.getSize() > 0) {
                imageName = image.getSubmittedFileName();
                try (FileOutputStream fos = new FileOutputStream(StringUtils.IMAGE_DIRECTORY + imageName);
                     InputStream is = image.getInputStream()) {
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    fos.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();  // Consider logging this instead of printStackTrace
                    req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, "Failed to save image.");
                    req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                    return;
                }
            }

            // Calculate the new net amount
            double previousNetAmount = customerDao.getPreviousNetBalance(customerIdInt);
            double newNetAmount = previousNetAmount + Double.parseDouble(amountStr);

            // Create a new transaction object
            Transaction newTransaction = new Transaction(transactionDate, Double.parseDouble(amountStr), newNetAmount, transactionType, remarks, imageName, customerIdInt);

            // Save the transaction and handle the result
            int result = customerDao.saveCustomerDebitTransaction(newTransaction, reminderDate);
            switch (result) {
                case 1:
                    req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.TRANSACTION_ADDED_SUCCESSFULLY_MESSAGE);
                    resp.sendRedirect(req.getContextPath() + StringUtils.CUSTOMER_TRANSACTIONS_SERVLET);
                    break;
                case 0:
                case -1:
                    req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.TRANSACTION_ADDED_ERROR_MESSAGE);
                    req.getRequestDispatcher(StringUtils.CUSTOMER_TRANSACTION_PAGE).forward(req, resp);
                    break;
            }
        }
    }
}
