<%@ page import="model.Customer" %>
<%@ page import="utils.StringUtils" %>
<%@ page import="model.CustomerFinancialSummary" %>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Transaction</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <%
        Customer customer = (Customer) session.getAttribute(StringUtils.CUSTOMER_OBJECT);
        CustomerFinancialSummary summary = (CustomerFinancialSummary) session.getAttribute(StringUtils.CUSTOMER_SUMMARY_OBJECT);
        List<Transaction> transactions = (List<Transaction>) session.getAttribute(StringUtils.TRANSACTION_SUMMARY_OBJECT);
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
            margin-left: 270px;
            margin-top: 70px;
            padding: 20px;
            background-color: #e7f2fd;
            width: calc(100% - 260px);
        }

        .top-navbar{
            width: 100%;
            display: flex;
            justify-content: start;
            align-items: center;
            gap: 25px;
        }

        .top-navbar a{
            text-decoration: none;
            color: black;
            font-size: 20px;
        }

        .top-navbar .customer-name{
            font-size: 25px;
            font-weight: 500;
        }

        .cash-button {
            display: flex;
            width: 100%;
            justify-content: space-between;
            margin: 20px 0;
            padding: 0 40px;
        }

        .search-bar{
            margin-right: 100px;
        }

        .search-bar form {
            position: relative;
            display: flex;
            align-items: center;
            border-radius: 5px;
            overflow: hidden;
        }

        .search-bar input {
            width: 600px;
            padding: 10px;
            border: none;
            outline: none;
            font-size: 16px;
        }

        .search-bar .search {
            position: absolute;
            margin: 0;
            right: 5%;
            border: none;
            background: none;
            color: black;
            cursor: pointer;
        }

        .search-bar .search i {
            font-size: 19px;
            color: #aaa;
        }

        .transaction-button{
            display: flex;
        }

        .transaction-button button {
            padding: 10px 20px;
            margin-right: 20px;
            font-size: 16px;
            font-weight: 500;
            border: none;
            cursor: pointer;
            color: #fff;
            background-color: var(--blue-color);
            border-radius: 5px;
            display: flex;
            align-items: center;
            gap: 10px;
            transition: background-color 0.3s, transform 0.2s;
        }

        .transaction-button button:hover {
            background-color: #3059d1;
            transform: scale(1.05);
        }

        .transaction-button button i {
            font-size: 14px;
            margin-right: 8px;
        }

        #cashInBtn {
            background-color: #28a745;
        }

        #cashOutBtn {
            background-color: #dc3545;
        }

        /* Popup styling */
        .hide{
            display: none;
        }
        .popup {
            position: fixed;
            top: 0;
            right: 0;
            transform: translateX(100%);
            width: 500px;
            height: 500px;
            background-color: white;
            padding: 20px;
            border-radius: 5px 0 0 5px;
            box-shadow: -2px 0 10px rgba(0, 0, 0, 0.2);
            display: flex;
            flex-direction: column;
            z-index: 1000;
            transition: transform 0.5s ease-in-out, opacity 0.3s ease-in-out;
            opacity: 0;
        }

        .popup.show {
            transform: translateX(0);
            opacity: 1;
        }

        .popup.hide {
            transform: translateX(100%);
            opacity: 0;
        }

        .popup-content {
            flex-grow: 1;
            overflow-y: auto;
        }

        .popup-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .popup-header h2 {
            color: rgb(238, 124, 48);
            margin: 0;
            font-size: 20px;
        }

        .popup-header .closePopup {
            background: none;
            border: none;
            font-size: 18px;
            cursor: pointer;
        }

        .popup-content hr{
            margin-top: 10px;
        }

        /* Styling Transaction Amount Section */
        .transaction-amount {
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 93.7%;
            height: 100px;
            margin-top: 30px;
            margin-bottom: 30px;
            margin-left: 40px;
            padding: 0 20px;
            border: 1px solid rgb(201, 201, 201);
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.08);
            background-color: #fff;
        }

        .cashin-amount,
        .cashout-amount,
        .net-amount{
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .cashin-amount i{
            color: green;
            background: greenyellow;
            padding: 10px;
            border-radius: 50%;
            font-size: 16px;
        }

        .cashout-amount i{
            color: orange;
            background: orangered;
            padding: 10px;
            border-radius: 50%;
            font-size: 16px;
        }

        .net-amount i{
            color: rgb(87, 205, 241);
            background: rgb(19, 75, 75);
            padding: 10px;
            border-radius: 50%;
            font-size: 16px;
        }

        .content{
            line-height: 1.7;
        }
        .content p{
            font-size: 18px;
        }

        .content span{
            font-size: 20px;
            font-weight: 550;
        }

        .popup-body {
            padding: 20px;
        }

        .cashInForm {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .cashInForm label {
            font-size: 16px;
            font-weight: 500;
            margin-bottom: 5px;
        }

        .cashInForm input[type="number"],
        .cashInForm select,
        .cashInForm textarea,
        .cashInForm input[type="file"],
        .cashInForm input[type="date"] {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        #cashInForm textarea {
            resize: vertical;
        }



        .submit-button {
            padding: 10px 15px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            align-self: flex-start;
        }

        .submit-button:hover {
            background-color: #0056b3;
        }

        .transaction-table {
            width: 93.7%;
            border-collapse: collapse;
            margin-left: 40px;
        }

        .transaction-table th,
        .transaction-table td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: left;
        }

        .transaction-table th {
            background-color: #f8f9fa;
            font-weight: 600;
        }

        .transaction-table tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .transaction-table tbody tr:hover {
            background-color: #f1f1f1;
        }

        .transaction-table td img {
            width: 50px;
            height: 50px;
            border-radius: 4px;
        }

        .transaction-table td.positive {
            font-weight: bold;
            color: green;
        }

        .transaction-table td.negative {
            font-weight: bold;
            color: red;
        }

        .transaction-table .no-transactions {
            text-align: center;
            font-style: italic;
            color: #888;
            padding: 20px;
        }

        .message-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px;
            width: 400px;
            border-radius: 5px;
            margin: 10px auto 0;
        }

        .message-container.err{
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            margin-bottom: 10px;
        }

        .message-container.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            margin-bottom: 10px;
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
    <% }
        assert customer != null;%>

    <div class="top-navbar">
        <a href="${pageContext.request.contextPath}/customer-list">
            <i class="fa-solid fa-arrow-right fa-rotate-180"></i>
        </a>
        <p class="customer-name"><% %>
            <%= customer.getFirstName() + " " + customer.getLastName() %>
            <% %>
        </p>
    </div>
    <div class="cash-button">
        <div class="search-bar">
            <form>
                <input type="text" placeholder="Search by date...">
                <button type="submit" class="search">
                    <i class="fa-solid fa-search"></i>
                </button>
            </form>
        </div>
        <div class="transaction-button">
            <button id="cashInBtn">
                <i class="fa-solid fa-plus"></i> Cash In
            </button>
            <button id="cashOutBtn">
                <i class="fa-solid fa-minus"></i> Cash Out
            </button>
        </div>
    </div>
    <div class="transaction-amount">
        <div class="cashin-amount">
            <i class="fa-solid fa-plus"></i>
            <div class="content">
                <p class="amount">Cash In</p>
                <span><%= summary.getTotalCreditAmount()%></span>
            </div>
        </div>
        <div class="cashout-amount">
            <i class="fa-solid fa-minus"></i>
            <div class="content">
                <p class="amount">Cash Out</p>
                <span><%= summary.getTotalDebitAmount()%></span>
            </div>
        </div>
        <div class="net-amount">
            <i class="fa-solid fa-equals"></i>
            <div class="content">
                <p class="amount">Net Amount</p>
                <span><%= summary.getNetBalance()%></span>
            </div>
        </div>
    </div>
    <table class="transaction-table">
        <thead>
        <tr>
            <th>Date</th>
            <th>Details</th>
            <th>Mode</th>
            <th>Bill</th>
            <th>Amount</th>
            <th>Remaining Balance</th>
        </tr>
        </thead>
        <tbody>
        <% if (transactions == null || transactions.isEmpty()) { %>
        <tr>
            <td colspan="6" class="no-transactions">No transactions available</td>
        </tr>
        <% } else { %>
        <%
            // Define the DateTimeFormatter for formatting date and time
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            for (Transaction transaction : transactions) {
                String amountClass = "positive";
                double amount = transaction.getAmount();

                if ("Debit".equalsIgnoreCase(transaction.getTransactionType())) {
                    amountClass = "negative";
                } else if ("Credit".equalsIgnoreCase(transaction.getTransactionType())) {
                    amountClass = "positive";
                }
                // Format the date and time separately
                String formattedDate = transaction.getTransactionDate().format(dateFormatter);
                String formattedTime = transaction.getTransactionDate().format(timeFormatter);
        %>
        <tr>
            <td><%= formattedDate %><br> <%= formattedTime %></td>
            <td><%= transaction.getRemarks() %></td>
            <td><%= transaction.getPaymentMethod() %></td>
            <td class="image">
                <% if (transaction.getImage() != null && !transaction.getImage().isEmpty()) { %>
                <a href="${pageContext.request.contextPath}/assets/transaction_images/<%= transaction.getImage() %>" target="_blank">
                    <img src="${pageContext.request.contextPath}/assets/transaction_images/<%= transaction.getImage() %>" alt="Bill Image">
                </a>
                <% } %>
            </td>
            <td class="<%= amountClass %>"><%= amount %></td>
            <td><%= transaction.getNetAmount() %></td>
        </tr>
        <% } %>
        <% } %>
        </tbody>


    </table>
</div>

<!-- Cash In Popup -->
<div id="cashInPopup" class="popup hide">
    <div class="popup-content">
        <div class="popup-header">
            <h2>Add Cash In Entry</h2>
            <button class="closePopup">X</button>
        </div>
        <hr>
        <div class="popup-body">
            <form class="cashInForm" action="${pageContext.request.contextPath}/transaction" method="post" enctype="multipart/form-data">
                <input type="hidden" name="transactionType" value="Credit">
                <div class="form-group">
                    <label for="amount">Amount:</label>
                    <input type="number" id="amount" class="amount" name="transactionAmount" placeholder="Enter amount" required min="1" value="<%= (request.getParameter("transactionAmount") != null) ? request.getParameter("transactionAmount") : "" %>">
                </div>
                <div class="form-group">
                    <label for="paymentMode">Payment Mode:</label>
                    <select class="paymentMode" id="paymentMode" name="paymentMode" required >
                        <option value="" selected>Select payment mode</option>
                        <option value="cash">Cash</option>
                        <option value="e-sewa">E-Sewa</option>
                        <option value="bankTransfer">Bank Transfer</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="remarks">Remarks:</label>
                    <textarea class="remarks" id="remarks" name="remarks" placeholder="Enter remarks" rows="3" required></textarea>
                </div>
                <div class="form-group" id="imageField">
                    <label for="image">Attach Statement:</label>
                    <input type="file" id="image" class="image" name="transactionImage" accept="image/*" required>
                </div>
                <div class="form-group">
                    <input type="hidden" value="<%= customer.getCustomerID()%>" name="customerId">
                    <button type="submit" class="submit-button">Add Entry</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%--Cash Out Popup --%>
<div id="cashOutPopup" class="popup hide">
    <div class="popup-content">
        <div class="popup-header">
            <h2>Add Cash Out Entry</h2>
            <button class="closePopup">X</button>
        </div>
        <hr>
        <div class="popup-body">
            <form class="cashInForm" action="${pageContext.request.contextPath}/transaction" method="post" enctype="multipart/form-data">
                <input type="hidden" name="transactionType" value="Debit">
                <div class="form-group">
                    <label for="amount">Amount:</label>
                    <input type="number" class="amount" name="transactionAmount" placeholder="Enter amount" required min="1" value="<%= (request.getParameter("transactionAmount") != null) ? request.getParameter("transactionAmount") : "" %>">
                </div>
                <div class="form-group">
                    <label for="remarks">Remarks:</label>
                    <textarea class="remarks" name="remarks" placeholder="Enter remarks" rows="3" required></textarea>
                </div>
                <div class="form-group">
                    <label for="image">Attach Bill:</label>
                    <input type="file" class="image" name="transactionImage" accept="image/*" required>
                </div>
                <div class="form-group">
                    <label for="reminderDate">Set Reminder Date:</label>
                    <input type="date" id="reminderDate" class="reminderDate" name="reminder" required>
                </div>
                <div class="form-group">
                    <input type="hidden" value="<%= customer.getCustomerID()%>" name="customerId">
                    <button type="submit" class="submit-button">Add Entry</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/Popup.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const paymentModeSelect = document.getElementById('paymentMode');
        const imageField = document.getElementById('imageField');
        const imageInput = document.getElementById('image');

        function toggleImageField() {
            if (paymentModeSelect.value === 'cash') {
                imageField.style.display = 'none';
                imageInput.disabled = true; // Disable file input
            } else {
                imageField.style.display = 'block';
                imageInput.disabled = false; // Enable file input
            }
        }

        // Initial check
        toggleImageField();

        // Add event listener for changes to the payment mode select
        paymentModeSelect.addEventListener('change', toggleImageField);
    });

</script>
</body>
</html>
