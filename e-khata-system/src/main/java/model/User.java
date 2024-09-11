package model;

// Model class representing a user entity
public class User {

    // Private fields representing user attributes
    private int userID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String businessName;
    private String address;
    private String password;

    // Default constructor
    public User() {}

    // Constructor for user login with email and password
    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    // Constructor to initialize all user attributes
    public User(String firstName, String lastName, String emailAddress, String phoneNumber, String businessName, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.businessName = businessName;
        this.address = address;
        this.password = password;
    }

    // Getter for userID
    public int getUserID() {
        return userID;
    }

    // Setter for userID
    public void setUserID(int userID) {
        this.userID = userID;
    }

    // Getter for firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for lastName
    public String getLastName() {
        return lastName;
    }

    // Setter for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter for emailAddress
    public String getEmailAddress() {
        return emailAddress;
    }

    // Setter for emailAddress
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter for businessName
    public String getBusinessName() {
        return businessName;
    }

    // Setter for businessName
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
