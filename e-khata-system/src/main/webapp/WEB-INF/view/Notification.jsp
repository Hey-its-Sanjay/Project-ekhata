<%@ page import="java.util.List" %>
<%@ page import="model.ReminderNotification" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Transaction</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        /* Import Google font - Poppins */
        @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap");
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Poppins", sans-serif;
        }

        :root {
            --white-color: #fff;
            --blue-color: #4070f4;
            --grey-color: #707070;
            --grey-color-light: #aaa;
        }

        body {
            background-color: #e7f2fd;
            transition: all 0.5s ease;
        }

        body.dark {
            background-color: #333;
        }

        body.dark {
            --white-color: #333;
            --blue-color: #fff;
            --grey-color: #f2f2f2;
            --grey-color-light: #aaa;
        }

        /* Styling Body Content */
        .body-content {
            margin-left: 260px;
            margin-top: 70px;
            padding: 20px;
            background-color: #e7f2fd;
            width: calc(100% - 260px);
        }

        .deadline-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: var(--white-color);
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .deadline-table thead {
            background-color: var(--blue-color);
            color: var(--white-color);
        }

        .deadline-table th, .deadline-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid var(--grey-color-light);
        }

        .deadline-table tbody tr:hover {
            background-color: #f1f1f1;
        }

        .deadline-table th {
            font-weight: 500;
            font-size: 16px;
        }

        .deadline-table td {
            font-weight: 400
        }
    </style>
    <%
        List<ReminderNotification> notifications = (List<ReminderNotification>) request.getAttribute("notifications");
    %>
</head>
<body>

<%@ include file="Sidebar.jsp"%>
<!-- Body Content -->
<div class="body-content">
    <h2>Reminders for Today</h2>
    <table class="deadline-table">
        <thead>
        <tr>
            <th>Notification</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (notifications != null) {
                for (ReminderNotification notification : notifications) {
        %>
        <tr>
            <td>
                <%=notification.getCustomerName() + " has to pay " +
                    ("credit".equals(notification.getTransactionType()) ? "an amount of " : "back an amount of ") +
                    notification.getAmount() + "."
                %>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td>No reminders for today.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>