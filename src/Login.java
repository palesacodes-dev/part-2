
/**
 *
 * @author Student
 */
public class Login {
    // Fields to store user data
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
   

    // 1. Method to check Username: contains "_" and is <= 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // 2. Method to check Password complexity
    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasNum = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        
        return password.length() >= 8 && hasUpper && hasNum && hasSpecial;
    }

    // 3. Register Method: Returns the specific messages from the task table
    public String registerUser(String user, String pass, String fName, String lName, String phoneNumber) {
        if (!checkUserName(user)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } 
        if (!checkPasswordComplexity(pass)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // If checks pass, store the data
        this.registeredUsername = user;
        this.registeredPassword = pass;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phoneNumber;
        
        return "Username and Password successfully captured.";
    }

    // 4. Login Method: Checks if entered details match registered ones
    public boolean loginUser(String user, String pass) {
        return user.equals(this.registeredUsername) && pass.equals(this.registeredPassword);
    }

    // 5. Final Status Method: Returns the specific welcome or error message
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}