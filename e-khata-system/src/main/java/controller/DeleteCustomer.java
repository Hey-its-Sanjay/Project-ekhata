package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerDao;
import utils.StringUtils;

import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.DELETE_CUSTOMER_SERVLET)
public class DeleteCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Declare an instance of CustomerDao to interact with the database.
    private CustomerDao customerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Initialize the customerDao object for use in this servlet.
        this.customerDao = new CustomerDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the customer ID parameter from the request.
        String customerID = req.getParameter(StringUtils.CUSTOMER_ID);

        // Convert the customer ID from String to an integer.
        int customerIDInt = Integer.parseInt(customerID);

        // Call the deleteCustomer method from customerDao to delete the customer.
        int result = customerDao.deleteCustomer(customerIDInt);

        // Handle the result of the delete operation.
        switch (result) {
            case 1:
                // If deletion is successful, set a success message in the session.
                req.getSession().setAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY, StringUtils.CUSTOMER_DELETED_SUCCESSFULLY_MESSAGE);
                // Redirect to the customer list page.
                resp.sendRedirect(req.getContextPath() + StringUtils.CUSTOMER_LIST_SERVLET);
                break;
            case -1:
                // If deletion fails (e.g., customer has a non-zero balance), set an error message.
                req.setAttribute(StringUtils.ERROR_MESSAGE_KEY, StringUtils.CUSTOMER_DELETED_ERROR_MESSAGE);
                // Forward the request back to the customer view page to show the error.
                req.getRequestDispatcher(StringUtils.VIEW_CUSTOMER_PAGE).forward(req, resp);
                break;
        }
    }
}
