package utils;

public class SignUpValidation {

    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
    }

    public static boolean isValidFirstName(String firstname) {
        return firstname.matches("[a-zA-Z\\s]+");
    }

    public static boolean isValidLastName(String lastname) {
        return lastname.matches("[a-zA-Z\\s]+");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$");
    }

    public static boolean isValidNumber(String phonenumber) {
        return phonenumber.matches("\\d{10}"); // Match digits only
    }

}
