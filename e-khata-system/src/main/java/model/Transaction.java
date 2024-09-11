package model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime transactionDate;
    private double amount;
    private double netAmount;
    private String transactionType;
    private String remarks;
    private String image;
    private int customerID;
    private String paymentMethod;

    public Transaction() {}

    public Transaction(LocalDateTime transactionDate, double amount, double netAmount, String transactionType, String remarks, String image, int customerID) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.netAmount = netAmount;
        this.transactionType = transactionType;
        this.remarks = remarks;
        this.image = image;
        this.customerID = customerID;
    }

    public Transaction(LocalDateTime transactionDate, double amount, double netAmount, String transactionType, String remarks, String image, int customerID, String paymentMethod) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.netAmount = netAmount;
        this.transactionType = transactionType;
        this.remarks = remarks;
        this.image = image;
        this.customerID = customerID;
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
