<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Khata System</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <style>
        /* Styling Body Content */
        .body-content {
            margin-left: 260px;
            margin-top: 70px;
            padding: 20px;
            background-color: #e7f2fd;
            width: calc(100% - 260px);
        }

        .dashboard-summary {
            display: flex;
            gap: 20px;
            justify-content: center;
            align-items: center;
            padding: 10px;
        }

        .customer-summary {
            display: flex;
            align-items: center;
            background: #ffffff;
            border-radius: 10px;
            width: 300px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .customer-summary i {
            font-size: 30px;
            padding: 10px;
            border-radius: 50%;
            color: #fff;
            background: #007bff;
            margin-right: 20px;
        }

        .customer-summary:nth-child(1) {
            background: #f8d7da;
        }

        .customer-summary:nth-child(2) {
            background: #d4edda;
        }

        .customer-summary:nth-child(3) {
            background: #d1ecf1;
        }

        .customer-summary:nth-child(4) {
            background: #fff3cd;
        }

        .customer-summary .customer-numbers h3 {
            margin: 0;
            font-size: 16px;
            color: #333;
        }

        .customer-summary .customer-numbers span {
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }

        .static-images {
            display: flex;
            gap: 100px;
            justify-content: center;
            margin-top: 20px;
        }

        .static-images img {
            width: 500px;
            height: auto;
            object-fit: contain;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

    </style>
</head>
<body>

<%@ include file="Sidebar.jsp"%>

<!-- Body Content -->
<div class="body-content">
    <div class="dashboard-summary">
        <div class="customer-summary">
            <i class="bx bxs-user"></i>
            <div class="customer-numbers">
                <h3>Total Customers</h3>
                <span>20000</span>
            </div>
        </div>

        <div class="customer-summary">
            <i class="bx bx-trending-up"></i>
            <div class="customer-numbers">
                <h3>Total Sales</h3>
                <span>$<strong>50000000</strong></span>
            </div>
        </div>

        <div class="customer-summary">
            <i class="bx bxs-user"></i>
            <div class="customer-numbers">
                <h3>Total Customers</h3>
                <span>20000</span>
            </div>
        </div>

        <div class="customer-summary">
            <i class="bx bxs-user"></i>
            <div class="customer-numbers">
                <h3>Total Customers</h3>
                <span>20000</span>
            </div>
        </div>

    </div>

    <div class="static-images">
        <img src="${pageContext.request.contextPath}/assets/static_images/bar-chart.png" alt="">
        <img src="${pageContext.request.contextPath}/assets/static_images/bar-chart2.png" alt="">
    </div>
</div>
</body>
</html>
