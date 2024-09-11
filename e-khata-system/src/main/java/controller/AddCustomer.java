package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.CustomerDao;
import utils.SignUpValidation;
import utils.StringUtils;

import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.ADD_CUSTOMER_SERVLET)
public class AddCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDao customerDao;  //for interacting with the customer database.

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Initialize CustomerDao instance for database operations
        this.customerDao = new CustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward GET request to the Add Customer page
        req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);  //display the form for adding a new customer
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = req.getParameter(StringUtils.FIRST_NAME);
        String lastName = req.getParameter(StringUtils.LAST_NAME);
        String email = req.getParameter(StringUtils.EMAIL_ADDRESS);
        String phone = req.getParameter(StringUtils.PHONE_NUMBER);
        String address = req.getParameter(StringUtils.ADDRESS);

        // Check for empty fields and return an error message if any field is empty
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMPTY_FIELD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
            return;
        }

        // Validation for First Name
        if(!SignUpValidation.isValidFirstName(firstName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.FIRST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
            return;
        }

        // Validation for Last Name
        if(!SignUpValidation.isValidLastName(lastName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.LAST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
            return;
        }

        // Validation for Email Address
        if(!SignUpValidation.isValidEmail(email.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMAIL_ADDRESS_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
            return;
        }

        // Validation for Phone Number
        if(!SignUpValidation.isValidNumber(phone.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
            return;
        }

        // Create a new Customer object with the validated data
        Customer newCustomer = new Customer(firstName, lastName, email, phone, address);

        // Add the new customer using CustomerDao
        int result = customerDao.addCustomer(newCustomer);

        // Handle the result of the customer addition operation
        switch (result) {
            case 1:
                // Success: Set a success message and redirect to the servlet
                req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.CUSTOMER_ADDED_SUCCESSFULLY_MESSAGE);
                resp.sendRedirect(req.getContextPath() + StringUtils.ADD_CUSTOMER_SERVLET);
                break;

            case -1:
                // Error: Email already exists
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.CUSTOMER_EMAIL_ADDRESS_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
                break;

            case -2:
                // Error: Phone number already exists
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.CUSTOMER_PHONE_NUMBER_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
                break;

            case -4:
                // Error: General error occurred
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.CUSTOMER_ADDED_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.ADD_CUSTOMER_PAGE).forward(req, resp);
                break;
        }
    }
}
