/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Quickchatapp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testUsernameCorrect() {
        // Test data: kyl_1 (contains underscore and < 5 chars)
        boolean actual = login.checkUserName("kyl_1");
        assertTrue(actual, "Username should be valid (contains underscore and is <= 5 chars)");
    }

    @Test
    public void testUsernameIncorrect() {
        // Test data: kyle!!!!!!! (too long and no underscore)
        boolean actual = login.checkUserName("kyle!!!!!!!.");
        assertFalse(actual, "Username should be invalid");
    }

    @Test
    public void testPasswordComplexitySuccess() {
        // Test data: Ch&&sec@ke99! (Meets all requirements)
        boolean actual = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(actual, "Password should meet complexity requirements");
    }

    @Test
    public void testPasswordComplexityFailure() {
        // Test data: password (Too short, no caps, no special, no numbers)
        boolean actual = login.checkPasswordComplexity("password");
        assertFalse(actual, "Password does not meet requirements");
    }
    
    @Test
    public void testLoginSuccess() {
        // Registering a user first to test login
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Palesa", "Mokoena");
        
        // Testing if correct details log in successfully
        boolean actual = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(actual);
    }

    @Test
    public void testLoginFailure() {
        // Registering a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Palesa", "Mokoena");
        
        // Testing with wrong password
        boolean actual = login.loginUser("kyl_1", "wrongPass123!");
        assertFalse(actual);
    }
}
