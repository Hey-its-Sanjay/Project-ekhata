<%@ page import="java.util.ArrayList" %>
<%@ page import="utils.StringUtils" %>
<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"/>
    <title>Customer List</title>
    <%
        @SuppressWarnings("unchecked")
        List<Customer> customers = (List<Customer>) session.getAttribute(StringUtils.CUSTOMER_OBJECT);
    %>

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

        /* Styling Body Content */
        .body-content {
            margin-left: 260px;
            margin-top: 70px;
            padding: 20px;
            background-color: #e7f2fd;
            width: calc(100% - 260px);
        }

        .search-container {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 10px;
        }

        .search-wrapper {
            position: relative;
            width: 100%;
            max-width: 600px;
            display: flex;
            align-items: center;
        }

        .search-bar {
            font-size: 16px;
            width: 400px;
            padding: 10px 40px 10px 10px;
            border-radius: 25px;
            box-sizing: border-box;
            border: none;
            outline: none;
        }

        .search-bar::placeholder {
            color: #888;
            font-size: 16px;
        }

        .search-button {
            position: absolute;
            top: 50%;
            right: 2%;
            transform: translateY(-50%);
            background: transparent;
            border: none;
            cursor: pointer;
            padding: 10px;
        }

        .search-button i {
            color: #333;
            font-size: 16px;
        }

        .search-bar:focus {
            outline: none;
            border-color: #8AAAE5;
        }

        .reset-button {
            padding: 10px 20px;
            background-color: var(--blue-color);
            color: var(--white-color);
            border: none;
            border-radius: 25px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            transition: background-color 0.3s ease;
        }

        .reset-button:hover {
            background-color: #335dc2;
        }

        .body-content .container {
            background: white;
            width: 800px;
            padding: 25px;
            margin: 20px auto 50px;
            border-top: 5px solid blue;
            box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.5);
            max-height: 500px;
            position: relative;
        }

        .table-wrapper {
            max-height: 445px; /* Adjust height to fit the table */
            overflow-y: auto;
        }

        /* Styling for the table */
        .customer-table {
            width: 100%;
            border-collapse: collapse;
            table-layout: fixed;
        }

        .customer-table thead {
            background-color: #f2f2f2;
        }

        .customer-table th, .customer-table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .customer-table th {
            font-weight: bold;
            color: #333;
        }

        .customer-table td {
            color: #333;
        }

        .customer-table .customer-name a{
            text-decoration: none;
            color: black;
        }

        .customer-table .customer-name a:hover {
            text-decoration: underline;
            color: black;
        }

        /* Column widths */
        .customer-table th:nth-child(1),
        .customer-table td:nth-child(1) {
            width: 10%;
        }

        .customer-table th:nth-child(2),
        .customer-table td:nth-child(2) {
            width: 40%;
        }

        .customer-table th:nth-child(3),
        .customer-table td:nth-child(3),
        .customer-table th:nth-child(4),
        .customer-table td:nth-child(4) {
            width: 25%;
        }

        /* Styling for action buttons */
        .customer-table form {
            display: inline-block;
            margin: 0;
        }

        .customer-table form .edit-btn, .customer-table form .delete-btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 14px;
            width: 70px;
        }

        .customer-table .edit-btn {
            background-color: #4CAF50;
            color: white;
            margin-bottom: 10px;
        }

        .customer-table .delete-btn {
            background-color: #f44336;
            color: white;
        }

        .customer-table .edit-btn:hover, .customer-table .delete-btn:hover {
            opacity: 0.8;
        }

        /* Scrollbar for table wrapper */
        .table-wrapper {
            max-height: 445px; /* Adjust height to fit the table */
            overflow-y: auto;
        }

        .table-wrapper::-webkit-scrollbar {
            width: 8px;
        }

        .table-wrapper::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        .table-wrapper::-webkit-scrollbar-thumb {
            background: #888;
            border-radius: 5px;
        }

        .table-wrapper::-webkit-scrollbar-thumb:hover {
            background: #555;
        }

        .message-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px;
            width: 600px;
            border-radius: 5px;
            margin: 10px auto 0;
        }
        .message-container.err{
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .message-container.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .message-container p {
            margin: 0;
        }

        .message-container .close-btn {
            cursor: pointer;
            font-size: 1.2em;
        }

        .close-btn i {
            color: inherit;
        }
    </style>
</head>
<body>
<%@include file="Sidebar.jsp"%>

<!-- Body Content -->
<div class="body-content">
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/customer-list" method="get">
            <div class="search-wrapper">
                <input type="text" placeholder="Search" class="search-bar" name="search">
                <button class="search-button" type="submit">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
            <button type="reset" class="reset-button" onclick="window.location.href='${pageContext.request.contextPath}/customer-list'">Reset Filter</button>
        </form>
    </div>
     <% String errorMessage = (String) request.getAttribute(StringUtils.ERROR_MESSAGE_KEY);
         if (errorMessage != null && !errorMessage.isEmpty()) { %>
     <div class="message-container err">
         <p><%= errorMessage %></p>
         <span class="close-btn" onclick="this.parentElement.style.display='none';"><i class="fa-solid fa-x"></i></span>
     </div>
     <% } %>

     <% String successMessage = (String) session.getAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY);
         if (successMessage != null && !successMessage.isEmpty()) { %>
     <div class="message-container success">
         <span><%= successMessage %></span>
         <span class="close-btn" onclick="this.parentElement.style.display='none';"><i class="fa-solid fa-x"></i></span>
     </div>
     <%
         session.removeAttribute(StringUtils.COMMON_SUCCESS_MESSAGE_KEY);
     %>
     <% } %>
    <div class="container">
        <div class="table-wrapper">
            <table class="customer-table">
                <thead>
                <tr>
                    <th>S.N.</th>
                    <th>Customer Name</th>
                    <th>Phone Number</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(customers != null && !customers.isEmpty()) {
                        int symbolNumber = 1;
                %>
                <% for(Customer customer: customers) { %>
                <tr>
                    <td><%= symbolNumber %></td>
                    <td class="customer-name">
                        <a href="${pageContext.request.contextPath}/customer-profile?id=<%= customer.getCustomerID()%>">
                            <%= customer.getFirstName() + " " + customer.getLastName() %>
                        </a>
                    </td>

                    <td><%= customer.getPhone() %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/transaction" method="get">
                            <input type="hidden" value="<%= customer.getCustomerID()%>" name="customerId">
                            <button class="edit-btn" type="submit">Edit</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/delete-customer" method="post">
                            <input type="hidden" value="<%= customer.getCustomerID()%>" name="customerId">
                            <button class="delete-btn" type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        symbolNumber++;
                    } %>
                <% } else { %>
                <tr>
                    <td colspan="4" style="text-align: center">No Customers</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
