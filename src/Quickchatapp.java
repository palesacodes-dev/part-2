
import Quickchatapp.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quickchatapp {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login auth = new Login();
            boolean isRunning = true;

            while (isRunning) {

                boolean loginStatus = false;

                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");

                String choice = input.nextLine();

                switch (choice) {
                    case "1":
                        // --- REGISTRATION ---
                        System.out.print("Enter First Name: ");
                        String fName = input.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lName = input.nextLine();
                        System.out.print("Enter Username: ");
                        String user = input.nextLine();
                        System.out.print("Enter Password: ");
                        String pass = input.nextLine();

                        String regStatus = auth.registerUser(user, pass, fName, lName);
                        System.out.println("\n" + regStatus);

                        if (regStatus.equals("Username and Password successfully captured.")) {
                            // The specific personalized welcome you requested
                            System.out.println("Welcome " + fName + " it's nice to meet you!");
                            loginStatus = true;
                        }
                        break;

                    case "2":
                        // --- LOGIN ---
                        System.out.print("Enter Username: ");
                        String logUser = input.nextLine();
                        System.out.print("Enter Password: ");
                        String logPass = input.nextLine();

                        boolean success = auth.loginUser(logUser, logPass);
                        System.out.println("\n" + auth.returnLoginStatus(success));
                        loginStatus = true;
                        break;

                    case "3":
                        // --- EXIT ---
                        System.out.println("Exiting program... Goodbye!");
                        isRunning = false;
                        loginStatus = false;
                        break;

                    default:
                        System.out.println("Invalid selection. Please try again.");
                }
                while (loginStatus) {

                    // --- 3. THE MESSAGE MENU ---
                    if (loginStatus) {
                        boolean appRunning = true;

                        // This loop keeps the menu on screen until you hit '3'
                        while (appRunning) {
                            System.out.println("\n--- QUICKCHAT MAIN MENU ---");
                            System.out.println("1) Send Messages");
                            System.out.println("2) Show recently sent (Coming Soon)");
                            System.out.println("3) Quit & Show Report");
                            System.out.print("Selection: ");

                            int menuChoice = input.nextInt();
                            input.nextLine(); // CRITICAL: Clears the buffer so nextLine() works later
                            switch (menuChoice) {
                                case 1 -> {
                                    System.out.print("How many messages? ");
                                    int num = input.nextInt();
                                    input.nextLine();
                                    List<Message> MessageList = new ArrayList();
                                    for (int i = 0; i < num; i++) {
                                        System.out.println("\n--- Message Entry " + (i + 1) + " ---");
                                        boolean checkRecipientCell = false;
                                        String recipient = "";
                                        while (!checkRecipientCell) {
                                            System.out.print("Recipient: ");
                                            recipient = input.nextLine();
                                            checkRecipientCell = MessageUtil.checkRecipientCell(recipient);
                                            if (checkRecipientCell == false) {
                                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.Please correct the number and try again");
                                            }
                                        }
                                        boolean validText = false;
                                        String text = "";
                                        while (!validText) {
                                            System.out.print("Text: ");
                                            text = input.nextLine();
                                            validText = MessageUtil.validateMessageLength(text);
                                            if (validText == false) {
                                                int x = text.length() - 250;
                                                System.out.println("Message exceeds 250 characters by " + x + ";please reduce the size");
                                            }
                                        }
                                        String id = MessageUtil.generateMessageID();
                                        String hash = MessageUtil.createMessageHash(id, i, text);

                                        System.out.println("ID: " + id + " | Hash: " + hash);
                                        System.out.print("1) Send 2) Disregard 3) Store: ");
                                        int action = input.nextInt();
                                        input.nextLine();
                                        Message message = new Message(id, recipient, text, hash, action);
                                        MessageList.add(message);
                                        switch (action) {
                                            case 1 -> {
                                                System.out.println("Message successfully sent");
                                                System.out.println(message.getFormattedDetails());
                                                Message.addMessage(message);
                                                break;
                                            }
                                            case 2 -> {
                                                System.out.print("press 0 to delete the message");
                                                int discard = input.nextInt();
                                                if (discard == 0) {
                                                    MessageList.removeLast();
                                                }

                                            }
                                            case 3 -> {
                                                System.out.println("Message successfully stored");
                                                System.out.println(message.getFormattedDetails());
                                                Message.addMessage(message);
                                            }
                                        }

                                        // Save the data
                                    }
                                }
                                case 3 -> {
                                    // Final Report Logic
                                    System.out.println("\n--- FINAL REPORT ---");
                                    Message.printMessages();
                                    System.out.println("Total Sent: " + Message.returnTotalMessages());
                                    appRunning = false; // Stops the loop and exits
                                }

                                default ->
                                    System.out.println("Feature coming soon or invalid option.");
                            }
                        }
                    } else {
                        System.out.println("Access Denied. Please restart to try again.");
                    }
                }
                input.close();
            }
        }
    }
}
