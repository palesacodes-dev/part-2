/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class Register {

    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String userPassword;
    private final String phoneNumber;

    // Updated Constructor with First & Last Name
    public Register(String firstName, String lastName, String userName, 
                    String userPassword, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
    }

    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (userPassword.length() < 8) return false;

        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : userPassword.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkPhoneNumber() {
        return phoneNumber.startsWith("+27") && phoneNumber.length() == 12;
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // Success message with First and Last Name (as required)
        return "Welcome " + firstName + " " + lastName + " it is great to see you.";
    }

    // Getters
    public String getUserName() { return userName; }
    public String getUserPassword() { return userPassword; }
}