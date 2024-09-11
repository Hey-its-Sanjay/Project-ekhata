<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <title>E-Khata System</title>
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

        /* navbar */
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            left: 0;
            background-color: var(--white-color);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px 30px;
            z-index: 1000;
            box-shadow: 0 0 2px var(--grey-color-light);
        }

        .logo_item {
            display: flex;
            align-items: center;
            column-gap: 10px;
            font-size: 22px;
            font-weight: 500;
            color: var(--blue-color);
        }

        .navbar img {
            width: 35px;
            height: 35px;
            border-radius: 50%;
        }

        .search_bar input {
            height: 100%;
            width: 100%;
            border-radius: 25px;
            font-size: 18px;
            outline: none;
            background-color: var(--white-color);
            color: var(--grey-color);
            border: 1px solid var(--grey-color-light);
            padding: 0 20px;
        }

        .navbar_content {
            display: flex;
            align-items: center;
            column-gap: 25px;
        }

        .navbar_content i {
            cursor: pointer;
            font-size: 20px;
            color: var(--grey-color);
        }

        /* sidebar */
        .sidebar {
            background-color: var(--white-color);
            width: 260px;
            position: fixed;
            top: 0;
            left: 0;
            height: 100%;
            padding: 80px 20px;
            z-index: 100;
            overflow-y: scroll;
            box-shadow: 0 0 1px var(--grey-color-light);
            transition: all 0.5s ease;
        }

        .sidebar.close {
            padding: 60px 0;
            width: 80px;
        }

        .sidebar::-webkit-scrollbar {
            display: none;
        }

        .menu_content {
            position: relative;
        }

        .menu_items {
            padding: 0;
            list-style: none;
        }

        .navlink_icon {
            position: relative;
            font-size: 22px;
            min-width: 50px;
            line-height: 40px;
            display: inline-block;
            text-align: center;
            border-radius: 6px;
        }

        .navlink_icon::before {
            content: "";
            position: absolute;
            height: 100%;
            width: calc(100% + 100px);
            left: -20px;
        }

        .navlink_icon:hover {
            background: var(--blue-color);
        }

        .sidebar .nav_link {
            display: flex;
            align-items: center;
            width: 100%;
            padding: 12px 0;
            border-radius: 8px;
            text-decoration: none;
            color: var(--grey-color);
            white-space: nowrap;
        }

        .sidebar.close .navlink {
            display: none;
        }

        .nav_link:hover {
            color: var(--white-color);
            background: var(--blue-color);
        }

        .sidebar.close .nav_link:hover {
            background: var(--white-color);
        }

        .submenu_item {
            cursor: pointer;
        }

        .submenu {
            display: none;
        }

        .submenu_item .arrow-left {
            position: absolute;
            right: 10px;
            display: inline-block;
            margin-right: auto;
        }

        .sidebar.close .submenu {
            display: none;
        }

        .show_submenu ~ .submenu {
            display: block;
        }

        .show_submenu .arrow-left {
            transform: rotate(90deg);
        }

        .submenu .sublink {
            padding: 15px 15px 15px 52px;
        }

        .sidebar.close .bottom span {
            display: none;
        }

        .sidebar.hoverable .collapse_sidebar {
            display: none;
        }

        #sidebarOpen {
            display: none;
        }


    </style>
</head>
<body>

<!-- Top Navbar -->
<nav class="navbar">
    <div class="logo_item">
        <i class="bx bx-menu" id="sidebarOpen"></i>
        <img src="${pageContext.request.contextPath}/assets/static_images/logo.png" alt="">E-Khata System
    </div>

    <div class="navbar_content">
        <i class="bi bi-grid"></i>
        <i class="bx bx-sun" id="darkLight"></i>
        <img src="${pageContext.request.contextPath}/assets/static_images/user-pic.jpg" alt="" class="profile" />
    </div>
</nav>

<!-- sidebar -->
<nav class="sidebar">
    <div class="menu_content">
        <ul class="menu_items">

            <li class="item">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav_link">
                      <span class="navlink_icon">
                        <i class="bx bx-receipt"></i>
                      </span>
                    <span class="navlink">Dashboard</span>
                </a>
            </li>


            <li class="item">
                <div href="#" class="nav_link submenu_item">
                        <span class="navlink_icon">
                            <i class="bx bx-user"></i>
                        </span>
                    <span class="navlink">Customer</span>
                    <i class="bx bx-chevron-right arrow-left"></i>
                </div>

                <ul class="menu_items submenu">
                    <a href="${pageContext.request.contextPath}/add-customer" class="nav_link sublink">Add New Customer</a>
                    <a href="${pageContext.request.contextPath}/customer-list" class="nav_link sublink">View Customer</a>
                </ul>
            </li>


            <li class="item">
                <a href="${pageContext.request.contextPath}/notification" class="nav_link">
                      <span class="navlink_icon">
                        <i class="bx bx-bell"></i>
                      </span>
                    <span class="navlink">Notifications</span>
                </a>
            </li>

            <li class="item">
                <a href="${pageContext.request.contextPath}/user-profile" class="nav_link">
                      <span class="navlink_icon">
                        <i class="bx bx-user"></i>
                      </span>
                    <span class="navlink">Profile</span>
                </a>
            </li>

            <li class="item" id="logout">
                <a href="${pageContext.request.contextPath}/logout" class="nav_link">
                      <span class="navlink_icon">
                        <i class="bx bx-log-out"></i>
                      </span>
                    <span class="navlink">Log Out</span>
                </a>
            </li>

        </ul>
    </div>
</nav>
<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/js/SideBar.js"></script>
</body>
</html>
