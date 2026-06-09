/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quickchatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Student
 */
public class Message {
private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    
    // Global tracking maps to easily search across every entry type
    private static ArrayList<String> allRecipients = new ArrayList<>();
    private static ArrayList<String> allMessages = new ArrayList<>();
    private static ArrayList<String> allFlags = new ArrayList<>();

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
     public static void populateArrays(String recipient, String message, String hash, String id, String flag) {
        messageHashes.add(hash);
        messageIDs.add(id);
        allRecipients.add(recipient);
        allMessages.add(message);
        allFlags.add(flag);
        
        // Categorize into specific state blocks
        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(message);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(message);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessages.add(message);
        }
    }

    // a. Display sender and recipient of all stored messages
    public static void displayAllStored() {
        System.out.println("\n--- ALL STORED MESSAGES ---");
        boolean found = false;
        for (int i = 0; i < allMessages.size(); i++) {
            if (allFlags.get(i).equalsIgnoreCase("Stored")) {
                System.out.println("Recipient/ID: " + allRecipients.get(i) + " | Message: " + allMessages.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages matching 'Stored' status are currently held.");
        }
    }

    // b. Display the longest stored message
    public static String getLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages available.";
        }
        String longest = storedMessages.get(0);
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    // c. Search for a message ID and display corresponding recipient and message
    public static String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchID)) {
                return "Recipient: " + allRecipients.get(i) + " | Message: " + allMessages.get(i);
            }
        }
        return "Message ID not found.";
    }

    // d. Search for all messages stored for a particular recipient
    public static ArrayList<String> searchAllStoredForRecipient(String recipientNum) {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < allMessages.size(); i++) {
            if (allRecipients.get(i).equals(recipientNum) && allFlags.get(i).equalsIgnoreCase("Stored")) {
                results.add(allMessages.get(i));
            }
        }
        return results;
    }

    // e. Delete a message using the message hash
    public static boolean deleteMessageByHash(String searchHash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(searchHash)) {
                String targetMsg = allMessages.get(i);
                String flagType = allFlags.get(i);
                
                // Clear from active specific subsets
                if (flagType.equalsIgnoreCase("Sent")) sentMessages.remove(targetMsg);
                else if (flagType.equalsIgnoreCase("Disregard")) disregardedMessages.remove(targetMsg);
                else if (flagType.equalsIgnoreCase("Stored")) storedMessages.remove(targetMsg);
                
                // Drop from primary index tables
                allRecipients.remove(i);
                allMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                allFlags.remove(i);
                return true;
            }
        }
        return false;
    }
    public static void printStoredReport() {
        System.out.println("\n=================================================================");
        System.out.println("                 STORED MESSAGES - FULL DETAILS REPORT           ");
        System.out.println("=================================================================");
        boolean checking = false;
        for (int i = 0; i < allMessages.size(); i++) {
            if (allFlags.get(i).equalsIgnoreCase("Stored")) {
                System.out.println("Message ID : " + messageIDs.get(i));
                System.out.println("Hash Code  : " + messageHashes.get(i));
                System.out.println("Recipient  : " + allRecipients.get(i));
                System.out.println("Message Content: \"" + allMessages.get(i) + "\"");
                System.out.println("-----------------------------------------------------------------");
                checking = true;
            }
        }
        if (!checking) {
            System.out.println("No records found with status 'Stored'.");
        }
    }
    
    public static void saveJsonFile(Message message){
        
    try {
        Path path = Path.of("messages.json");
        
        JSONArray array;
        
        if(Files.exists(path) && Files.size(path)>0){
            String json = Files.readString(path);
            array = new JSONArray(json);
        } else{
            array = new JSONArray();
        }
        JSONObject obj =new JSONObject();
        obj.put("recipient", message.recipient);
        obj.put("id", message.messageID);
        obj.put("text", message.messageText);
        obj.put("status",message.status);
        obj.put("hash", message.messageHash);
        
        array.put(obj);
        Files.writeString(path, array.toString(2));
        System.out.println("File is successfully saved");
    } catch (IOException ex) {
        System.getLogger(Message.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
    }
    
    // Direct Access hooks for your automated testing file assertions
    public static ArrayList<String> getSentMessages() { return sentMessages; }
    public static ArrayList<String> getStoredMessages() { return storedMessages; }
}
   