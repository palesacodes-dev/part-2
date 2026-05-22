/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Quickchatapp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    
    //  MESSAGE LENGTH TESTS
    
    @Test
    public void testValidateMessageLength_Success() {
        // Test Data from your rubric template
        String validMessage = "Hi Mike, can you join us for dinner tonight?";
        String result = MessageUtil.validateMessageLength(validMessage);
        
        // Should match the exact success phrase in your sheet
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testValidateMessageLength_Failure() {
        // Create a test message that is exactly 255 characters (5 over the limit)
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            longMessage.append("a");
        }
        
        String result = MessageUtil.validateMessageLength(longMessage.toString());
        
        // Expecting the exact failure text structure from your rubric
        String expectedMessage = "Message exceeds 250 characters by 5; please reduce the size.";
        assertEquals(expectedMessage, result);
    }

    
    //  RECIPIENT NUMBER TESTS
    
    @Test
    public void testCheckRecipientCell_Success() {
        // Valid international format from your sheet (+27...)
        String validNum = "+27718693002";
        String result = MessageUtil.checkRecipientCell(validNum);
        
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        // Invalid format from your sheet (Missing the '+' symbol)
        String invalidNum = "08575975889";
        String result = MessageUtil.checkRecipientCell(invalidNum);
        
        // Exact error text string required by your prompt
        String expectedFailureMessage = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expectedFailureMessage, result);
    }

    
    //  MESSAGE HASH TEST
    
    @Test
    public void testCreateMessageHash() {
        // Setup the data exactly like the example in your sheet
        String dummyId = "0012345678"; 
        int loopIndex = 0;
        String sampleMessage = "Hi Mike, can you join us for dinner tonight?";
        
        String resultHash = MessageUtil.createMessageHash(dummyId, loopIndex, sampleMessage);
        
        // Should cleanly output "00:0:HITONIGHT"
        assertEquals("00:0:HITONIGHT", resultHash);
    }

    
    //  AUTOMATED MESSAGE ID TEST
    
    @Test
    public void testGenerateMessageID() {
        String generatedId = MessageUtil.generateMessageID();
        
        assertNotNull(generatedId);
        assertEquals(10, generatedId.length(), "The generated system ID must be exactly 10 digits long.");
    }

    
    // ACTION CODE STATUS TESTS
    
    
    @Test
    public void testSentMessage_Option1() {
        String result = MessageUtil.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testSentMessage_Option2() {
        String result = MessageUtil.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    public void testSentMessage_Option3() {
        String result = MessageUtil.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }
}
