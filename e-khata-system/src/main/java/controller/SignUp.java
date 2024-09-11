package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserDao;
import utils.SignUpValidation;
import utils.StringUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.SIGNUP_SERVLET)
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instance of UserDao to handle database operations
    private UserDao userDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Initialize UserDao when the servlet is first loaded
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
    }

    // Handle POST requests (usually used to submit the signup form)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Retrieve form input values from the request
        String firstName = req.getParameter(StringUtils.FIRST_NAME);
        String lastName = req.getParameter(StringUtils.LAST_NAME);
        String emailAddress = req.getParameter(StringUtils.EMAIL_ADDRESS);
        String phoneNumber = req.getParameter(StringUtils.PHONE_NUMBER);
        String businessName = req.getParameter(StringUtils.BUSINESS_NAME);
        String address = req.getParameter(StringUtils.ADDRESS);
        String password = req.getParameter(StringUtils.PASSWORD);
        String confirmPassword = req.getParameter(StringUtils.CONFIRM_PASSWORD);

        // Check for empty fields and return an error message if any field is empty
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            emailAddress == null || emailAddress.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
                businessName == null || businessName.trim().isEmpty() ||
            address == null || address.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMPTY_FIELD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation for First Name
        if(!SignUpValidation.isValidFirstName(firstName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.FIRST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation for Last Name
        if(!SignUpValidation.isValidLastName(lastName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.LAST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation for Email Address
        if(!SignUpValidation.isValidEmail(emailAddress.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMAIL_ADDRESS_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation for Phone Number
        if(!SignUpValidation.isValidNumber(phoneNumber.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation for Strong Password
        if(!SignUpValidation.isValidPassword(password.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.PASSWORD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Validation to check if the password and confirm password match
        if (!password.equals(confirmPassword)) {
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.CONFIRM_PASSWORD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
            return;
        }

        // Create a new User object with the provided data
        User newUser = new User(firstName, lastName, emailAddress, phoneNumber, businessName, address, password);

        // Attempt to register the new user
        int result = userDao.registerUser(newUser);

        // Handle the result of the registration process
        switch (result){
            case 1:
                // Success: Redirect to the login page with a success message
                req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.USER_REGISTERED_SUCCESSFULLY_MESSAGE);
                resp.sendRedirect(req.getContextPath() + StringUtils.LOGIN_SERVLET);
                break;

            case -1:
                // Error: Email address already exists
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.DUPLICATE_EMAIL_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
                break;

            case -2:
                // Error: Phone number already exists
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.DUPLICATE_PHONE_NUMBER_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
                break;

            case -3:
                // Error: Business name already exists
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.DUPLICATE_BUSINESS_NAME_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
                break;

            case -4:
                // Error: SQL error during registration
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.USER_REGISTERED_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.SIGN_UP_PAGE).forward(req, resp);
                break;
        }

    }
}
