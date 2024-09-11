<%@ page import="utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SingUp | E-Khata System</title>
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
            background-repeat: no-repeat;
            background-size: cover;
        }

        .signup-section{
            display: flex;
            width: 1000px;
            height: 650px;
            background-color: #fff;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            margin: 100px auto;
        }

        .form{
            position: relative;
            flex-basis: 60%;
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

        .lock{
            padding: 10px 11px;
        }

        .fname, .lname{
            width: 45%;
            padding: 8px 0 8px 45px;
        }

        .input-name span{
            margin-left: 35px;
        }

        .email, .password, .confirmPassword, .number{
            width: 100%;
            padding: 8px 0 8px 45px;
        }

        .input-name i{
            position: absolute;
            font-size: 18px;
            color: blue;
            border-right: 1px solid #cccccc;
        }

        .envelope, .pass, .phoneNumber{
            padding: 10px;
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

        .login-link {
            text-align: center;
            margin-top: 20px;
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

        .btn{
            width: 100%;
            line-height: 1.6;
            font-weight: 600;
            padding: 5px 20px;
            cursor: pointer;
            background: #007bff;
            color: white;
        }

        .btn:hover{
            background-color: #0056b3;
        }

        .image-container{
            flex-basis: 60%;
        }

        .image-container img{
            width: 100%;
            height: 100%;
            object-fit: cover;
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
<div class="signup-section">

    <%-- Form Section --%>
    <div class="form">

        <%-- Error Message Section    --%>
        <% String errorMessage = (String) request.getAttribute(StringUtils.ERROR_MESSAGE_KEY);
        if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div class="message-container err">
            <p><%= errorMessage %></p>
            <span class="close-btn" onclick="this.parentElement.style.display='none';"><i class="fa-solid fa-x"></i></span>
        </div>
        <% } %>

        <h2>Sign Up</h2>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/signup" method="post" id="form">

                <div class="input-name">
                    <i class="fa-solid fa-user lock"></i>
                    <input type="text" placeholder="First Name" class="fname" name="firstName" value="<%= (request.getParameter("firstName") != null) ? request.getParameter("firstName"): "" %>">
                    <span>
                            <i class="fa-solid fa-user lock"></i>
                            <input type="text" placeholder="Last Name" class="lname" name="lastName" value="<%= (request.getParameter("lastName") != null) ? request.getParameter("lastName"): "" %>">
                        </span>
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-envelope envelope"></i>
                    <input type="text" placeholder="Email" class="email" name="emailAddress" value="<%= (request.getParameter("emailAddress") != null) ? request.getParameter("emailAddress"): "" %>">

                </div>

                <div class="input-name">
                    <i class="fa-solid fa-phone phoneNumber"></i>
                    <input type="text" placeholder="Phone Number" class="number" name="phoneNumber" value="<%= (request.getParameter("phoneNumber") != null) ? request.getParameter("phoneNumber"): "" %>">
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-landmark phoneNumber"></i>
                    <input type="text" placeholder="Business Name" class="number" name="businessName" value="<%= (request.getParameter("businessName") != null) ? request.getParameter("businessName"): "" %>">
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-location-pin phoneNumber"></i>
                    <input type="text" placeholder="Address" class="number" name="address" value="<%= (request.getParameter("address") != null) ? request.getParameter("address"): "" %>">
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-lock pass"></i>
                    <input type="password" placeholder="Password" class="password" name="password" value="<%= (request.getParameter("password") != null) ? request.getParameter("password"): "" %>">
                </div>

                <div class="input-name">
                    <i class="fa-solid fa-lock pass"></i>
                    <input type="password" placeholder="Confirm Password" class="confirmPassword" name="confirmPassword" value="<%= (request.getParameter("confirmPassword") != null) ? request.getParameter("confirmPassword"): "" %>">
                </div>

                <div class="input-name">
                    <input type="submit" class="btn" value="Sign Up">
                </div>

                <div class="login-link">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a></p>
                </div>

            </form>
        </div>
    </div>

    <!-- Image Section -->
    <div class="image-container">
        <img src="${pageContext.request.contextPath}/assets/static_images/gif.gif">
    </div>
</div>

</body>
</html>