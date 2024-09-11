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

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.CUSTOMER_PROFILE_SERVLET)
public class UpdateCustomerData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDao customerDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.customerDao = new CustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id != null){
            int customerId = Integer.parseInt(id);
            Customer customer = customerDao.getCustomer(customerId);
            req.getSession().setAttribute("customer", customer);
        }
        req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String emailAddress = req.getParameter("emailAddress");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        String customerIDStr = req.getParameter("customerID");

        // Check for empty fields and return an error message if any field is empty
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                emailAddress == null || emailAddress.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMPTY_FIELD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for First Name
        if(!SignUpValidation.isValidFirstName(firstName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.FIRST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Last Name
        if(!SignUpValidation.isValidLastName(lastName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.LAST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Email Address
        if(!SignUpValidation.isValidEmail(emailAddress.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMAIL_ADDRESS_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Phone Number
        if(!SignUpValidation.isValidNumber(phoneNumber.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        int customerID = Integer.parseInt(customerIDStr);
        Customer customer = new Customer();
        customer.setCustomerID(customerID);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(emailAddress);
        customer.setPhone(phoneNumber);
        customer.setAddress(address);

        int result = customerDao.updateCustomerDetails(customer);
        switch (result) {
            case 1:
                req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.CUSTOMER_DETAILS_UPDATE_SUCCESSFULLY_MESSAGE);
                resp.sendRedirect(req.getContextPath() + StringUtils.CUSTOMER_PROFILE_SERVLET);
                break;

            case 0:
            case -1:
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.USER_PROFILE_UPDATE_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.CUSTOMER_PROFILE_PAGE).forward(req, resp);
                break;
        }
    }
}
