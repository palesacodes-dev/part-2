
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
class MessageUtil {

    static boolean sentMessage(int action) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static boolean checkRecipientCell(String recipient) {
        boolean prefix = recipient.contains("+27");
        if(prefix)
            return recipient.length() == 12;
        else {
            return recipient.length() == 10;
        }
     }

    static boolean validateMessageLength(String text) {
    return text.length() < 250;
    }

    static String generateMessageID() {
   Random rn = new Random();
   String id = "";
   for (int i = 0; i<10;i++){
       id +=""+(rn.nextInt(10)+1);
   }
   return id;
    }

    static String createMessageHash(String id, int i, String text) {
        String idPart = id.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        return (idPart + ":" + i + ":" + firstWord + lastWord).toUpperCase();
    }
    
}
