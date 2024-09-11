package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReminderNotification;
import service.CustomerDao;
import utils.StringUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.NOTIFICATION_SERVLET)
public class Notification extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerDao customerDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.customerDao = new CustomerDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ReminderNotification> notifications = customerDao.getTodaysReminders();
        req.setAttribute("notifications", notifications);
        req.getRequestDispatcher(StringUtils.NOTIFICATION_PAGE).forward(req, resp);
    }
}

