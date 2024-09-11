<%@ page import="utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Khata System</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"/>
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

        .body-content .container{
            background: white;
            width: 600px;
            padding: 25px;
            margin: 50px auto 50px;
            border-top: 5px solid blue;
            box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.5);
        }

        .body-content .container h2{
            font-size: 24px;
            line-height: 24px;
            padding: 20px;
            text-align: center;
        }

        .input-name {
            width: 90%;
            position: relative;
            margin: 20px auto;
        }

        .lock{
            padding: 12px 11px;
        }

        .fname, .lname{
            width: 46%;
            padding: 8px 0 8px 50px;
        }

        .input-name span{
            margin-left: 35px;
        }

        .email, .number{
            width: 100%;
            padding: 8px 0 8px 50px;
        }

        .input-name i{
            position: absolute;
            font-size: 20px;
            color: blue;
            border-right: 1px solid #cccccc;
        }

        .envelope, .phoneNumber{
            padding: 12px 11px;
        }

        .input-name input{
            font-size: 17px;
            border: 1px solid #cccccc;
            outline: none;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
            -ms-transition: all 0.30s ease-in-out;
            transition: all 0.30s ease-in-out;
        }

        input:hover{
            background-color: #fafafa;
        }

        input:focus{
            border: 1px solid #0866ff;
        }

        .btn{
            width: auto;
            padding: 5px 20px;
            cursor: pointer;
            background: #007bff;
            color: white;
            font-weight: 500;
        }

        .btn:hover{
            background-color: #0056b3;
        }

        .login-link p {
            font-size: 0.9rem;
        }

        .login-link a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }

        .login-link a:hover {
            text-decoration: underline;
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
        <h2>Add New Customer</h2>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/add-customer" method="post">
                <div class="input-name">
                    <i class="fa-solid fa-user lock"></i>
                    <input type="text" placeholder="First Name" class="fname" name="firstName" value="<%= (request.getParameter("firstName") != null ) ? request.getParameter("firstName"): ""%>">
                    <span>
                        <i class="fa-solid fa-user lock"></i>
                        <input type="text" placeholder="Last Name" class="lname" name="lastName" value="<%= (request.getParameter("lastName") != null ) ? request.getParameter("lastName"): ""%>">
                    </span>
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-envelope envelope"></i>
                    <input type="text" placeholder="Email" class="email" name="emailAddress" value="<%= (request.getParameter("emailAddress") != null ) ? request.getParameter("emailAddress"): ""%>">

                </div>

                <div class="input-name">
                    <i class="fa-solid fa-phone phoneNumber"></i>
                    <input type="text" placeholder="Phone Number" class="number" name="phoneNumber" value="<%= (request.getParameter("phoneNumber") != null ) ? request.getParameter("phoneNumber"): ""%>">
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-location-dot phoneNumber"></i>
                    <input type="text" placeholder="Address" class="number" name="address" value="<%= (request.getParameter("address") != null ) ? request.getParameter("address"): ""%>">
                </div>

                <div class="input-name">
                    <input type="submit" class="btn" value="Save Customer">
                </div>
            </form>
        </div>

    </div>
</div>

</body>
</html>
