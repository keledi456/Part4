
package quickchatpart3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ChatApp {

    private static final int MAX_MESSAGES = 100;

    // Message arrays
    private static Message[] sentMessages =
            new Message[MAX_MESSAGES];

    private static Message[] disregardedMessages =
            new Message[MAX_MESSAGES];

    private static Message[] storedMessages =
            new Message[MAX_MESSAGES];

    // Message ID and Hash arrays
    private static String[] messageHashes =
            new String[MAX_MESSAGES];

    private static String[] messageIDs =
            new String[MAX_MESSAGES];

    // Counters
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    private static int metadataCount = 0;

    // JSON file
    private static final Path FILE_PATH =
            Path.of("stored_messages.json");

    // Add a sent message
    public static boolean addSentMessage(Message message) {

        if (sentCount >= MAX_MESSAGES) {
            return false;
        }

        message.setMessageStatus("Sent");

        sentMessages[sentCount] = message;
        sentCount++;

        addMetadata(message);

        return true;
    }

    // Add a disregarded message
    public static boolean disregardMessage(Message message) {

        if (disregardedCount >= MAX_MESSAGES) {
            return false;
        }

        message.setMessageStatus("Disregarded");

        disregardedMessages[disregardedCount] = message;
        disregardedCount++;

        return true;
    }

    // Add a stored message
    public static boolean storeMessage(Message message) {

        if (storedCount >= MAX_MESSAGES) {
            return false;
        }

        message.setMessageStatus("Stored");

        storedMessages[storedCount] = message;
        storedCount++;

        addMetadata(message);

        saveStoredMessagesToFile();

        return true;
    }

    // Add message ID and hash
    private static void addMetadata(Message message) {

        if (metadataCount < MAX_MESSAGES) {

            messageIDs[metadataCount] =
                    message.getMessageID();

            messageHashes[metadataCount] =
                    message.getMessageHash();

            metadataCount++;
        }
    }

    // Save messages in JSON format
    public static void saveStoredMessagesToFile() {

        List<String> jsonLines = new ArrayList<>();

        for (int i = 0; i < storedCount; i++) {

            Message message = storedMessages[i];

            String data =
                    encode(message.getMessageID()) + "|" +
                    encode(message.getRecipient()) + "|" +
                    encode(message.getMessage()) + "|" +
                    encode(message.getMessageStatus());

            jsonLines.add("{\"data\":\"" + data + "\"}");
        }

        try {

            Files.write(FILE_PATH, jsonLines);

            System.out.println("Messages saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving messages: "
                    + e.getMessage());
        }
    }

    // Encode text for JSON storage
    private static String encode(String text) {

        return Base64.getEncoder().encodeToString(
                text.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    // Decode text from JSON storage
    private static String decode(String text) {

        return new String(
                Base64.getDecoder().decode(text),
                java.nio.charset.StandardCharsets.UTF_8);
    }

    // Read stored messages from JSON file
    public static void loadStoredMessages() {

        if (!Files.exists(FILE_PATH)) {
            System.out.println("No stored messages file found.");
            return;
        }

        try {

            List<String> lines = Files.readAllLines(FILE_PATH);

            storedCount = 0;

            for (String line : lines) {

                if (storedCount >= MAX_MESSAGES) {
                    break;
                }

                int start = line.indexOf("\"data\":\"");

                if (start == -1) {
                    continue;
                }

                start += 8;

                int end = line.indexOf("\"", start);

                if (end == -1) {
                    continue;
                }

                String data = line.substring(start, end);

                String[] fields = data.split("\\|", -1);

                if (fields.length != 4) {
                    continue;
                }

                String id = decode(fields[0]);
                String recipient = decode(fields[1]);
                String text = decode(fields[2]);
                String status = decode(fields[3]);

                Message message = new Message(
                        id, recipient, text, status);

                storedMessages[storedCount] = message;
                storedCount++;

                addMetadata(message);
            }

            System.out.println("Stored messages loaded: "
                    + storedCount);

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Error loading messages: "
                    + e.getMessage());
        }
    }

    // Search for a message using its ID
    public static Message searchMessageById(String id) {

        for (int i = 0; i < sentCount; i++) {

            if (sentMessages[i].getMessageID().equals(id)) {
                return sentMessages[i];
            }
        }

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i].getMessageID().equals(id)) {
                return storedMessages[i];
            }
        }

        return null;
    }

    // Delete a sent message using its hash
    public static boolean deleteMessageByHash(String hash) {

        for (int i = 0; i < sentCount; i++) {

            if (sentMessages[i].getMessageHash().equals(hash)) {

                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }

                sentMessages[sentCount - 1] = null;
                sentCount--;

                return true;
            }
        }

        return false;
    }

    // Display all sent messages
    public static void displaySentMessages() {

        System.out.println("\n--- SENT MESSAGES ---");

        for (int i = 0; i < sentCount; i++) {

            System.out.println("Message ID: "
                    + sentMessages[i].getMessageID());

            System.out.println("Recipient: "
                    + sentMessages[i].getRecipient());

            System.out.println("Message: "
                    + sentMessages[i].getMessage());

            System.out.println("Hash: "
                    + sentMessages[i].getMessageHash());

            System.out.println("----------------------");
        }
    }

    // Display the message report
    public static void displayMessageReport() {

        System.out.println("\n========== MESSAGE REPORT ==========");

        for (int i = 0; i < sentCount; i++) {

            System.out.println("Message ID: "
                    + sentMessages[i].getMessageID());

            System.out.println("Message Hash: "
                    + sentMessages[i].getMessageHash());

            System.out.println("Recipient: "
                    + sentMessages[i].getRecipient());

            System.out.println("Message: "
                    + sentMessages[i].getMessage());

            System.out.println("Status: "
                    + sentMessages[i].getMessageStatus());

            System.out.println("------------------------------------");
        }

        System.out.println("Total sent messages: " + sentCount);
        System.out.println("Total disregarded messages: "
                + disregardedCount);
        System.out.println("Total stored messages: " + storedCount);

        System.out.println("====================================");
    }

    // Get message counts
    public static int getSentCount() {
        return sentCount;
    }

    public static int getDisregardedCount() {
        return disregardedCount;
    }

    public static int getStoredCount() {
        return storedCount;
    }

    // Get message arrays
    public static Message[] getSentMessages() {
        return sentMessages;
    }

    public static Message[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public static Message[] getStoredMessages() {
        return storedMessages;
    }

    public static String[] getMessageHashes() {
        return messageHashes;
    }

    public static String[] getMessageIDs() {
        return messageIDs;
    }
}
