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

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.USER_PROFILE_SERVLET)
public class UserProfileUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDao.getUserDetails();
        req.getSession().setAttribute(StringUtils.USER_OBJECT, user);
        req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userID = req.getParameter(StringUtils.USERID);
        int userIDInt = Integer.parseInt(userID);
        String firstName = req.getParameter(StringUtils.FIRST_NAME);
        String lastName = req.getParameter(StringUtils.LAST_NAME);
        String emailAddress = req.getParameter(StringUtils.EMAIL_ADDRESS);
        String phoneNumber = req.getParameter(StringUtils.PHONE_NUMBER);
        String businessName = req.getParameter(StringUtils.BUSINESS_NAME);
        String address = req.getParameter(StringUtils.ADDRESS);

        // Check for empty fields and return an error message if any field is empty
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                emailAddress == null || emailAddress.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty() ||
                businessName == null || businessName.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMPTY_FIELD_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for First Name
        if(!SignUpValidation.isValidFirstName(firstName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.FIRST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Last Name
        if(!SignUpValidation.isValidLastName(lastName.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.LAST_NAME_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Email Address
        if(!SignUpValidation.isValidEmail(emailAddress.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.EMAIL_ADDRESS_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        // Validation for Phone Number
        if(!SignUpValidation.isValidNumber(phoneNumber.trim())){
            req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
            req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);
        user.setPhoneNumber(phoneNumber);
        user.setBusinessName(businessName);
        user.setAddress(address);
        user.setUserID(userIDInt);

        int result = userDao.updateUserProfile(user);
        switch (result) {
            case 1:
                req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.USER_DETAILS_UPDATE_SUCCESSFULLY_MESSAGE);
                resp.sendRedirect(req.getContextPath() + StringUtils.USER_PROFILE_SERVLET);
                break;

            case 0:
            case -1:
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.USER_PROFILE_UPDATE_ERROR_MESSAGE);
                req.getRequestDispatcher(StringUtils.USER_PROFILE_PAGE).forward(req, resp);
                break;
        }

    }
}
