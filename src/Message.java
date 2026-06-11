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
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
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
    private String sender;

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
    public Message(String id, String recipient, String text, String hash, int status, String sender) {
        this.messageID = id;
        this.recipient = recipient;
        this.messageText = text;
        this.messageHash = hash;
        this.status = status;
        this.sender =sender;
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

    public String getRecipientAndSenderFormat() {
        return String.format("Recipient: %s\nSender: %s\n", recipient, sender);
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
    
    public static void populateList(){
        try{
        storedMessages = new Message().loadMessages(); 
       
        for (Message msg : storedMessages){
           messageHashes.add(msg.messageHash);
           messageIDs.add(msg.messageID);
        }
        if(!storedMessages.isEmpty()){
           System.out.println("Messages successfully loaded");
       }
        }catch (Exception e){
        }
    }
    // a. Display sender and recipient of all stored messages
    public static void displayAllStored() {
        System.out.println("\n--- ALL STORED MESSAGES ---");
        boolean found = false;
        for (int i = 0; i < storedMessages.size(); i++) {
            Message message = storedMessages.get(i);
            System.out.println(message.getRecipientAndSenderFormat());
            found = true;
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
        Message longest = storedMessages.get(0);
        for (Message msg : storedMessages) {
            if (msg.messageText.length() > longest.messageText.length()) {
                longest = msg;
            }
        }
        return longest.messageText;
    }

    // c. Search for a message ID and display corresponding recipient and message
    public static String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchID)) {
                return "Recipient: " + storedMessages.get(i).recipient + " \nMessage: " + storedMessages.get(i).messageText;
            }
        }
        return "Message ID not found.";
    }

    // d. Search for all messages stored for a particular recipient
    public static ArrayList<Message> searchAllStoredForRecipient(String recipientNum) {
        ArrayList<Message> results = new ArrayList<>();
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).recipient.equals(recipientNum) ) {
                results.add(storedMessages.get(i));
            }
        }
        return results;
    }

    // e. Delete a message using the message hash
    public static boolean deleteMessageByHash(String searchHash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(searchHash)) {
                storedMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
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
        for (int i = 0; i < storedMessages.size(); i++) {
            Message message  = storedMessages.get(i);
            System.out.println(message.getFormattedDetails()+"\n");
            checking = true;
        }
        if (!checking) {
            System.out.println("No records found with status 'Stored'.");
        }
    }
    
    public static void saveJsonFile(Message message){
        
    try {
        Path path = Path.of("messages.json");
        storedMessages.add(message);
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
        obj.put("sender",message.sender);
        
        array.put(obj);
        Files.writeString(path, array.toString(2));
        System.out.println("File is successfully saved");
    } catch (IOException ex) {
        System.getLogger(Message.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
    }
    public  ArrayList<Message> loadMessages()throws Exception{
        String json= Files.readString(Path.of("messages.json"));
        JSONArray array= new JSONArray(json);
        ArrayList<Message> messages= new ArrayList<>();
        for (int i=0; i < array.length(); i++) {
        JSONObject obj = array.getJSONObject(i);
        Message message= new Message(
                obj.getString("id"),
                obj.getString("recipient"),
                obj.getString("text"),
                obj.getString("hash"),
                obj.getInt("status"),
                obj.getString("sender")
        );
        messages.add(message);
        
    }  
    return messages;
    }
    // Direct Access hooks for your automated testing file assertions
    public static ArrayList<Message> getSentMessages() { return sentMessages; }
    public static ArrayList<Message> getStoredMessages() { return storedMessages; }
}
   