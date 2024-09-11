package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.CustomerDao;
import utils.StringUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.CUSTOMER_LIST_SERVLET)
// Servlet for viewing and searching customers
public class ViewCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDao customerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // Initialize the servlet and instantiate the CustomerDao object
        super.init(config);
        this.customerDao = new CustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the search query from the request
        String searchQuery = req.getParameter("search");
        List<Customer> customers;

        // Check if the search query is provided and not empty
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Search customers by name based on the search query
            customers = customerDao.searchCustomersByName(searchQuery);
        } else {
            // If no search query is provided, get all customers
            customers = customerDao.getAllCustomer();
        }

        // Set the list of customers as an attribute in the request
        req.getSession().setAttribute(StringUtils.CUSTOMER_OBJECT, customers);
        // Forward the request to the view customer page
        req.getRequestDispatcher(StringUtils.VIEW_CUSTOMER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Call to the superclass doPost method, here it doesn't add additional functionality
        super.doPost(req, resp);
    }
}
