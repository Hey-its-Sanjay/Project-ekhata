<%@ page import="utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | E-Khata System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body{
            font-family: "Roboto", sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            <%--background-image: url(${pageContext.request.context}/assets/static_images/background_image.jpg);--%>
            background-repeat: no-repeat;
            background-size: cover;
        }

        .login-section{
            display: flex;
            width: 1000px;
            height: 600px;
            background-color: #fff;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            margin: auto;
        }

        .form{
            position: relative;
            flex-basis: 40%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .form-container {
            width: 100%;
        }

        .form h2{
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

        .username, .password{
            width: 100%;
            padding: 8px 0 8px 45px;
        }

        .input-name i{
            position: absolute;
            font-size: 21px;
            color: blue;
            border-right: 1px solid #cccccc;
        }

        .user, .pass{
            padding: 8px;
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
            width: 100%;
            line-height: 1.6;
            padding: 5px 20px;
            cursor: pointer;
            background: #007bff;
            color: white;
            font-weight: 500;
        }

        .btn:hover{
            background-color: #0056b3;
        }

        .input-name a{
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
            transition: 0.2s;
        }

        .input-name a:hover{
            text-decoration: underline;
        }

        label{
            color: #706e6e;
        }

        input[type="checkbox"]{
            cursor: pointer;
        }

        #username-error, #password-error{
            display: inline-block;
            color: red;
            font-size: 15px;
            margin: 6px 0;
            font-weight: 600;
            opacity: 0.5;
        }

        #pass{
            position: relative;
        }

        #pass .showPass{
            position: absolute;
            top: 6%;
            right: 0;
            font-size: 16px;
            color: blue;
            padding: 8px;
            border: none;
            cursor: pointer;
            transition: color 0.3s ease;
        }

        #pass .showPass:hover {
            color: #0056b3;
        }

        .social-icon{
            position: absolute;
            bottom: 0;
            margin: 20px;
        }

        .social-icon i{
            margin-right: 20px;
            font-size: 20px;
            padding: 10px;
            border-radius: 50%;
            background: rgb(207, 206, 206);
            cursor: pointer;
            transition: color 0.3s;
        }

        .message-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px;
            width: 90%;
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

        .image-container{
            flex-basis: 60%;
        }

        .image-container img{
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

    </style>
</head>
<body>

<div class="login-section">

    <!-- Form Section -->
    <div class="form">

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

        <h2>Log In</h2>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/login" method="post" id="form">

                <div class="input-name">
                    <i class="fa-solid fa-user user"></i>
                    <input type="text" placeholder="Email address" class="username" name="emailAddress" id="username" value="<%= (request.getParameter("emailAddress") != null ) ? request.getParameter("emailAddress"): ""%>">
                </div>

                <div class="input-name" id="pass">
                    <i class="fa-solid fa-lock pass"></i>
                    <input type="password" placeholder="Password" class="password" name="password" id="password">
                    <i class="fa-solid fa-eye showPass"></i>
                </div>

                <div class="input-name">
                    <input type="submit" class="btn" value="Login">
                </div>

                <div class="input-name">
                    <p>Don't have account? <a href="${pageContext.request.contextPath}/signup">Create new account </a></p>
                </div>

            </form>
        </div>
    </div>

    <!-- Image Section -->
    <div class="image-container">
        <img src="${pageContext.request.contextPath}/assets/static_images/gif.gif">
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/LoginValidation.js"></script>
</body>
</html>