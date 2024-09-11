package service;

import model.Customer;
import model.CustomerFinancialSummary;
import model.ReminderNotification;
import model.Transaction;
import utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Data Access Object (DAO) class for managing customer-related database operations
public class CustomerDao {

    // Database connection object
    private Connection con;

    // Constructor to initialize the database connection
    public CustomerDao() {
        try {
            this.con = DatabaseConnection.getDatabaseConnection(); // Obtain a database connection
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle potential exceptions during database connection setup
        }
    }

    // Method to retrieve all customers from the database
    public List<Customer> getAllCustomer() {
        try {
            // SQL query to select all customers ordered by first name
            String query = "SELECT * FROM Customer ORDER BY FirstName";
            PreparedStatement ps = con.prepareStatement(query);

            // Execute the query and obtain the result set
            ResultSet rs = ps.executeQuery();
            List<Customer> customers = new ArrayList<>();

            // Iterate through the result set and create Customer objects
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setPhone(rs.getString("PhoneNumber"));
                customers.add(customer); // Add each customer to the list
            }
            return customers; // Return the list of customers
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return null; // Return null in case of an error
        }
    }

    // Method to search customers by name (either first or last name)
    public List<Customer> searchCustomersByName(String name) {
        try {
            String query;
            PreparedStatement ps;

            // Check if the input contains a space (indicating a full name)
            if (name.contains(" ")) {
                // Split the full name into first and last name
                String[] parts = name.split(" ");
                String firstName = parts[0];
                String lastName = parts.length > 1 ? parts[1] : "";

                // SQL query to search for customers by first and last name
                query = "SELECT * FROM Customer WHERE (FirstName LIKE ? AND LastName LIKE ?) OR (FirstName LIKE ? OR LastName LIKE ?) ORDER BY FirstName";
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + firstName + "%");
                ps.setString(2, "%" + lastName + "%");
                ps.setString(3, "%" + name + "%"); // For matching the whole name in either field
                ps.setString(4, "%" + name + "%"); // For matching the whole name in either field
            } else {
                // Single name search (either first or last name)
                query = "SELECT * FROM Customer WHERE FirstName LIKE ? OR LastName LIKE ? ORDER BY FirstName";
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + name + "%");
                ps.setString(2, "%" + name + "%");
            }

            // Execute the query and obtain the result set
            ResultSet rs = ps.executeQuery();
            List<Customer> customers = new ArrayList<>();

            // Iterate through the result set and create Customer objects
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setPhone(rs.getString("PhoneNumber"));
                customers.add(customer); // Add each customer to the list
            }
            return customers; // Return the list of customers
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return null; // Return null in case of an error
        }
    }

    // Method to check for duplicate fields in the database
    public boolean checkDuplicateField(String value, String columnName) {
        try {
            String query = "SELECT * FROM Customer WHERE " + columnName + " = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);

            // Execute the query and check if a record exists
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if a record is found, false otherwise

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return false; // Return false in case of an error
    }

    // Method to add a new customer to the database
    public int addCustomer(Customer customer) {
        // Check for duplicate email and phone number
        boolean isEmailAddressDuplicated = checkDuplicateField(customer.getEmail(), "EmailAddress");
        boolean isPhoneNumberDuplicated = checkDuplicateField(customer.getPhone(), "PhoneNumber");

        // Return specific error codes if duplicates are found
        if (isEmailAddressDuplicated) {
            return -1; // Email already exists
        } else if (isPhoneNumberDuplicated) {
            return -2; // Phone number already exists
        } else {
            try {
                // SQL query to insert a new customer record
                String query = "INSERT INTO Customer (FirstName, LastName, EmailAddress, PhoneNumber, Address) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);

                // Set parameters for the prepared statement
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getPhone());
                ps.setString(5, customer.getAddress());

                // Execute the update and return the result
                return ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace(); // Handle SQL exceptions
                return -3; // General error occurred
            }
        }
    }

    // Method to retrieves a Customer object from the database based on the provided customerID.
    public Customer getCustomer(int customerID) {
        try {
            // Prepare the SQL query to select a customer based on the given CustomerID
            String query = "SELECT * FROM Customer WHERE CustomerID = ?";
            PreparedStatement ps = con.prepareStatement(query);

            // Set the CustomerID parameter in the query
            ps.setInt(1, customerID);

            // Execute the query and retrieve the results
            ResultSet rs = ps.executeQuery();
            Customer customer = new Customer();

            // Populate the Customer object with data from the ResultSet
            while (rs.next()) {
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setPhone(rs.getString("PhoneNumber"));
                customer.setEmail(rs.getString("EmailAddress"));
                customer.setAddress(rs.getString(("Address")));
            }

            // Return the populated Customer object
            return customer;
        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs and return null
            e.printStackTrace();
            return null;
        }
    }

    public double getPreviousNetBalance(int customerId) {
        double netBalance = 0.0; // Initialize netBalance to 0.0, which will be returned if no transaction is found.

        // SQL query to select the most recent NetAmount for the given CustomerID.
        String query = "SELECT NetAmount FROM transaction WHERE CustomerID = ? ORDER BY TransactionDate DESC LIMIT 1";

        try {
            // Prepare the SQL statement with the provided customer ID.
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId); // Set the customerId parameter in the query.

            // Execute the query and obtain the result set.
            try (ResultSet rs = ps.executeQuery()) {
                // If a result is returned, retrieve the NetAmount value.
                if (rs.next()) {
                    netBalance = rs.getDouble("NetAmount"); // Assign the retrieved NetAmount to netBalance.
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
        }

        // Return the netBalance, which will be 0.0 if no transaction was found, or the last NetAmount if found.
        return netBalance;
    }

    public int saveCustomerTransaction(Transaction transaction) {
        try {
            // SQL query to insert a new transaction record into the Transaction table.
            String query = "INSERT INTO Transaction (TransactionDate, Type, Amount, NetAmount, Remarks, Image, CustomerID, PaymentMode) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the SQL statement with the provided transaction details.
            PreparedStatement ps = con.prepareStatement(query);

            // Convert the transaction date to a Timestamp object to match the SQL type.
            Timestamp timestamp = Timestamp.valueOf(transaction.getTransactionDate());
            ps.setTimestamp(1, timestamp); // Set the transaction date in the query.

            ps.setString(2, transaction.getTransactionType()); // Set the transaction type.
            ps.setBigDecimal(3, BigDecimal.valueOf(transaction.getAmount())); // Set the amount.
            ps.setBigDecimal(4, BigDecimal.valueOf(transaction.getNetAmount())); // Set the net amount.
            ps.setString(5, transaction.getRemarks()); // Set any remarks.
            ps.setString(6, transaction.getImage()); // Set the image if any.
            ps.setInt(7, transaction.getCustomerID()); // Set the customer ID.
            ps.setString(8, transaction.getPaymentMethod()); // Set the payment method.

            // Execute the update and store the result.
            int result = ps.executeUpdate();

            // Check if the transaction was successfully inserted (result == 1).
            if (result == 1) {
                return 1; // Return 1 if the insertion was successful.
            } else {
                return 0; // Return 0 if the insertion failed.
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
            return -1; // Return -1 to indicate an error.
        }
    }

    public int saveCustomerDebitTransaction(Transaction transaction, LocalDate reminderDate) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int transactionId = 0;
        try {

            // SQL query to insert a new debit transaction record into the Transaction table.
            String transactionQuery = "INSERT INTO Transaction (TransactionDate, Type, Amount, NetAmount, Remarks, Image, CustomerID, PaymentMode) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the SQL statement with the provided transaction details.
            ps = con.prepareStatement(transactionQuery, Statement.RETURN_GENERATED_KEYS);

            // Convert the transaction date to a Timestamp object to match the SQL type.
            Timestamp timestamp = Timestamp.valueOf(transaction.getTransactionDate());
            ps.setTimestamp(1, timestamp); // Set the transaction date in the query.
            ps.setString(2, transaction.getTransactionType()); // Set the transaction type.
            ps.setBigDecimal(3, BigDecimal.valueOf(transaction.getAmount())); // Set the amount.
            ps.setBigDecimal(4, BigDecimal.valueOf(transaction.getNetAmount())); // Set the net amount.
            ps.setString(5, transaction.getRemarks()); // Set any remarks.
            ps.setString(6, transaction.getImage()); // Set the image if any.
            ps.setInt(7, transaction.getCustomerID()); // Set the customer ID.
            ps.setString(8, ""); // Set the payment mode as an empty string for debit transactions.

            // Execute the update and retrieve the generated transaction ID.
            int result = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                transactionId = rs.getInt(1); // Get the generated transaction ID
            }

            // Check if the transaction was successfully inserted
            if (result == 1 && transactionId > 0) {
                // Insert reminder if a reminder date is provided
                if (reminderDate != null) {
                    String reminderQuery = "INSERT INTO Reminder (TransactionID, ReminderDate) VALUES (?, ?)";
                    try (PreparedStatement reminderPs = con.prepareStatement(reminderQuery)) {
                        reminderPs.setInt(1, transactionId);
                        reminderPs.setDate(2, java.sql.Date.valueOf(reminderDate));
                        reminderPs.executeUpdate();
                    }
                }
                return 1; // Return 1 if the insertion was successful.
            } else {
                return 0; // Return 0 if the insertion failed.
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
            return -1; // Return -1 to indicate an error.
        }
    }

    public CustomerFinancialSummary getCustomerFinancialSummary(int customerId) {
        double totalCreditAmount = 0.0; // Initialize the total credit amount to 0.0.
        double totalDebitAmount = 0.0; // Initialize the total debit amount to 0.0.
        double netBalance = 0.0; // Initialize the net balance to 0.0.

        // SQL query to sum the total credit amount for the given customer.
        String creditQuery = "SELECT COALESCE(SUM(Amount), 0) AS TotalCreditAmount FROM Transaction WHERE CustomerID = ? AND Type = 'Credit'";

        // SQL query to sum the total debit amount for the given customer.
        String debitQuery = "SELECT COALESCE(SUM(Amount), 0) AS TotalDebitAmount FROM Transaction WHERE CustomerID = ? AND Type = 'Debit'";

        // SQL query to calculate the net balance (total credits minus total debits) for the given customer.
        String balanceQuery = "SELECT COALESCE(SUM(CASE WHEN Type = 'Credit' THEN Amount ELSE -Amount END), 0) AS NetBalance FROM Transaction WHERE CustomerID = ?";

        try {
            // Execute the credit query to calculate the total credit amount.
            try (PreparedStatement stmt = con.prepareStatement(creditQuery)) {
                stmt.setInt(1, customerId); // Set the customer ID parameter in the query.
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalCreditAmount = rs.getDouble("TotalCreditAmount"); // Retrieve the total credit amount.
                    }
                }
            }

            // Execute the debit query to calculate the total debit amount.
            try (PreparedStatement stmt = con.prepareStatement(debitQuery)) {
                stmt.setInt(1, customerId); // Set the customer ID parameter in the query.
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalDebitAmount = rs.getDouble("TotalDebitAmount"); // Retrieve the total debit amount.
                    }
                }
            }

            // Execute the balance query to calculate the net balance.
            try (PreparedStatement stmt = con.prepareStatement(balanceQuery)) {
                stmt.setInt(1, customerId); // Set the customer ID parameter in the query.
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        netBalance = rs.getDouble("NetBalance"); // Retrieve the net balance.
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
        }

        // Return a new CustomerFinancialSummary object containing the total credit amount, total debit amount, and net balance.
        return new CustomerFinancialSummary(totalCreditAmount, totalDebitAmount, netBalance);
    }

    public List<Transaction> getTransaction(int customerID) {
        List<Transaction> transactions = new ArrayList<>(); // Initialize an empty list to hold Transaction objects.

        try {
            // SQL query to select all transactions for the given customer, ordered by transaction date in descending order.
            String query = "SELECT * FROM Transaction WHERE CustomerID = ? ORDER BY TransactionDate DESC";

            // Prepare the SQL statement and set the customer ID parameter.
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerID);

            // Execute the query and retrieve the result set.
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set to process each transaction.
            while (rs.next()) {
                Transaction transaction = new Transaction(); // Create a new Transaction object.

                // Convert the transaction date from SQL Timestamp to LocalDateTime.
                Timestamp timestamp = rs.getTimestamp("TransactionDate");
                LocalDateTime transactionDate = timestamp.toLocalDateTime();
                transaction.setTransactionDate(transactionDate); // Set the LocalDateTime in the transaction object.

                // Set the other transaction details from the result set.
                transaction.setRemarks(rs.getString("Remarks")); // Set the remarks.
                transaction.setPaymentMethod(rs.getString("PaymentMode")); // Set the payment mode.
                transaction.setTransactionType(rs.getString("Type")); // Set the transaction type (Credit/Debit).
                transaction.setImage(rs.getString("Image")); // Set the image associated with the transaction.
                transaction.setAmount(rs.getDouble("Amount")); // Set the amount.
                transaction.setNetAmount(rs.getDouble("NetAmount")); // Set the net amount.

                // Add the transaction object to the transactions list.
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
            return null; // Return null in case of an exception.
        }

        // Return the list of transactions for the given customer.
        return transactions;
    }

    public int deleteCustomer(int customerID) {

        // Get the financial summary for the customer to determine their net balance.
        CustomerFinancialSummary customerFinancialSummary = getCustomerFinancialSummary(customerID);

        // Retrieve the net balance for the customer.
        double netBalance = customerFinancialSummary.getNetBalance();

        // Check if the net balance is not zero.
        if (netBalance != 0.0) {
            return -1; // Return -1 if the customer has a non-zero balance, indicating that the customer cannot be deleted.
        } else {
            try {
                // SQL query to delete the customer from the Customer table based on CustomerID.
                String query = "DELETE FROM Customer WHERE CustomerID = ?";

                // Prepare the SQL statement.
                PreparedStatement ps = con.prepareStatement(query);

                // Set the CustomerID parameter in the query.
                ps.setInt(1, customerID);

                // Execute the query to delete the customer and return the number of rows affected.
                return ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Print the stack trace if an SQL exception occurs.
                return -1; // Return -1 if an exception occurs, indicating that the deletion failed.
            }
        }
    }

    public List<ReminderNotification> getTodaysReminders() {
        List<ReminderNotification> notifications = new ArrayList<>();
        String query = "SELECT CONCAT('Mr/Mrs. ', c.FirstName, ' ', c.LastName) AS CustomerName, " +
                "t.Amount, t.Type, r.ReminderDate " +
                "FROM customer c " +
                "JOIN transaction t ON c.CustomerID = t.CustomerID " +
                "JOIN reminder r ON t.TransactionID = r.TransactionID " +
                "WHERE r.ReminderDate = CURDATE()";

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReminderNotification notification = new ReminderNotification();
                notification.setCustomerName(rs.getString("CustomerName"));
                notification.setAmount(rs.getBigDecimal("Amount"));
                notification.setTransactionType(rs.getString("Type"));
                notification.setReminderDate(rs.getDate("ReminderDate"));
                notifications.add(notification);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public int updateCustomerDetails(Customer customer) {
        try{
            String query = "UPDATE customer SET FirstName = ?, LastName = ?, EmailAddress = ?, PhoneNumber = ?, Address = ? WHERE CustomerID = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getCustomerID());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
