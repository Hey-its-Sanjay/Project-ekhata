package utils;

public class StringUtils {

    // Database Configuration
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/ekhata";
    public static final String USER = "root";
    public static final String PASS = "";

    // Variables to save image in local directory
    public static final String IMAGE_DIRECTORY = "C:/Users/User/e-khata-system/src/main/webapp/assets/transaction_images/";

    // Servlets Mapping
    public static final String SIGNUP_SERVLET = "/signup";
    public static final String LOGIN_SERVLET = "/login";
    public static final String LOGOUT_SERVLET = "/logout";
    public static final String DASHBOARD_SERVLET = "/dashboard";
    public static final String ADD_CUSTOMER_SERVLET = "/add-customer";
    public static final String CUSTOMER_LIST_SERVLET = "/customer-list";
    public static final String CUSTOMER_TRANSACTIONS_SERVLET = "/transaction";
    public static final String DELETE_CUSTOMER_SERVLET = "/delete-customer";
    public static final String NOTIFICATION_SERVLET = "/notification";
    public static final String USER_PROFILE_SERVLET = "/user-profile";
    public static final String CUSTOMER_PROFILE_SERVLET = "/customer-profile";

    // JSP Route
    public static final String LOGIN_PAGE = "/WEB-INF/view/Login.jsp";
    public static final String SIGN_UP_PAGE = "/WEB-INF/view/SignUp.jsp";
    public static final String DASHBOARD_PAGE = "/WEB-INF/view/Dashboard.jsp";
    public static final String ADD_CUSTOMER_PAGE = "/WEB-INF/view/AddCustomer.jsp";
    public static final String VIEW_CUSTOMER_PAGE = "/WEB-INF/view/ViewCustomer.jsp";
    public static final String CUSTOMER_TRANSACTION_PAGE = "/WEB-INF/view/CustomerTransaction.jsp";
    public static final String NOTIFICATION_PAGE = "/WEB-INF/view/Notification.jsp";
    public static final String USER_PROFILE_PAGE = "/WEB-INF/view/UserProfile.jsp";
    public static final String CUSTOMER_PROFILE_PAGE = "/WEB-INF/view/CustomerProfile.jsp";

    // Parameters
    public static final String USERID = "userID";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String BUSINESS_NAME = "businessName";
    public static final String ADDRESS = "address";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    public static final String USER_OBJECT = "userObject";
    public static final String CUSTOMER_OBJECT = "customerObject";
    public static final String CUSTOMER_ID = "customerId";
    public static final String TRANSACTION_AMOUNT = "transactionAmount";
    public static final String PAYMENT_MODE = "paymentMode";
    public static final String REMARKS = "remarks";
    public static final String REMINDER_DATE = "reminder";
    public static final String TRANSACTION_IMAGE = "transactionImage";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String CUSTOMER_SUMMARY_OBJECT = "customerSummaryObject";
    public static final String TRANSACTION_SUMMARY_OBJECT = "transactionSummaryObject";

    // Error Message - Key
    public static final String ERROR_MESSAGE_KEY = "errorMessage";

    // Success Message - Key
    public static final String COMMON_SUCCESS_MESSAGE_KEY = "commonSuccessMessage";
    public static final String LOGIN_SUCCESS_MESSAGE_KEY = "loginSuccessMessage";

    // Error Message - Value
    public static final String SERVER_ERROR_MESSAGE = "Unexpected server error";
    public static final String EMPTY_FIELD_ERROR_MESSAGE = "Please fill all the field";
    public static final String FIRST_NAME_ERROR_MESSAGE = "Please enter valid first name";
    public static final String LAST_NAME_ERROR_MESSAGE = "Please enter valid last name";
    public static final String EMAIL_ADDRESS_ERROR_MESSAGE = "Please enter valid email address";
    public static final String PHONE_NUMBER_ERROR_MESSAGE = "Please enter valid phone number";
    public static final String PASSWORD_ERROR_MESSAGE = "Please enter strong password";
    public static final String CONFIRM_PASSWORD_ERROR_MESSAGE = "Password does not match";
    public static final String USER_REGISTERED_ERROR_MESSAGE = "Unable to register user";
    public static final String USER_LOGGED_IN_ERROR_MESSAGE = "Unable to log in";
    public static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "Email address already in use";
    public static final String DUPLICATE_PHONE_NUMBER_ERROR_MESSAGE = "Phone number already in use";
    public static final String DUPLICATE_BUSINESS_NAME_ERROR_MESSAGE = "Business name already in use";
    public static final String INCORRECT_PASSWORD_ERROR_MESSAGE = "Incorrect password";
    public static final String ACCOUNT_DOES_NOT_EXISTS_ERROR_MESSAGE = "Account does not exist";
    public static final String CUSTOMER_ADDED_ERROR_MESSAGE = "Unable to add customer";
    public static final String CUSTOMER_EMAIL_ADDRESS_ERROR_MESSAGE = "Customer already existed with this email address";
    public static final String CUSTOMER_PHONE_NUMBER_ERROR_MESSAGE = "Customer already existed with this phone number";
    public static final String TRANSACTION_ADDED_ERROR_MESSAGE = "Unable to perform transaction";
    public static final String CUSTOMER_DELETED_ERROR_MESSAGE = "Customer cannot be deleted";
    public static final String USER_PROFILE_UPDATE_ERROR_MESSAGE = "Unexpected error occur";

    // Success Message - Value
    public static final String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully";
    public static final String USER_LOGGED_IN_SUCCESSFULLY_MESSAGE = "User logged in successfully";
    public static final String CUSTOMER_ADDED_SUCCESSFULLY_MESSAGE = "Customer added successfully";
    public static final String CUSTOMER_DELETED_SUCCESSFULLY_MESSAGE = "Customer deleted successfully";
    public static final String TRANSACTION_ADDED_SUCCESSFULLY_MESSAGE = "Transaction added successfully";
    public static final String USER_DETAILS_UPDATE_SUCCESSFULLY_MESSAGE = "User details update successfully";
    public static final String CUSTOMER_DETAILS_UPDATE_SUCCESSFULLY_MESSAGE = "Customer details update successfully";

}
