/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quickchatapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Student
 */
public class Message {

    // Attributes to store data for each message object
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int status;

    private static int totalMessages = 0;
    private static List<Message> messages = new ArrayList();

    // Constructor: This "builds" the message object
    public Message(String id, String recipient, String text, String hash, int status) {
        this.messageID = id;
        this.recipient = recipient;
        this.messageText = text;
        this.messageHash = hash;
        this.status = status;

        if (status == 1) { // Only increment if 'Send' was chosen
            totalMessages++;
        }
    }

    // Default constructor for utility methods
    public Message() {
    }

    // --- LOGIC METHODS ---
    public String generateMessageID() {
        Random rn = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += "" + (rn.nextInt(10) + 1);
        }
        return id;
    }

    public String createMessageHash(String id, int index, String message) {
        String idPart = id.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        return (idPart + ":" + index + ":" + firstWord + lastWord).toUpperCase();
    }

    public String checkRecipientCell(String recipient) {
        return (recipient.startsWith("+") && recipient.length() >= 10)
                ? "Cell phone number successfully stored." : "Invalid format.";
    }

    public String validateMessageLength(String message) {
        return (message.length() <= 250) ? "Message ready to send." : "Too long.";
    }

    public static void addMessage(Message message) {
        messages.add(message);
        totalMessages = totalMessages + 1;
    }

    // --- REPORTING METHODS ---
    public String getFormattedDetails() {
        return String.format("Message ID: %s\nHash: %s\nRecipient: %s\nMessage: %s",
                messageID, messageHash, recipient, messageText);
    }

    public static void printMessages() {
        for (Message m : messages) {
            System.out.println(m.getFormattedDetails());
            System.out.println("--------------------");
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
}
