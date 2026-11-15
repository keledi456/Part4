
package quickchatpart3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Load previously stored messages
        ChatApp.loadStoredMessages();

        int choice;

        do {

            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1. Send Message");
            System.out.println("2. View Sent Messages");
            System.out.println("3. Display Message Report");
            System.out.println("4. Stored Messages");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");

            while (!input.hasNextInt()) {
                System.out.println("Please enter a number.");
                input.next();
            }

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Message ID: ");
                    String id = input.nextLine();

                    System.out.print("Enter recipient: ");
                    String recipient = input.nextLine();

                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    Message message = new Message(
                            id, recipient, messageText, "Sent");

                    System.out.println("\nChoose message action:");
                    System.out.println("1. Send");
                    System.out.println("2. Store");
                    System.out.println("3. Disregard");

                    int action = input.nextInt();
                    input.nextLine();

                    if (action == 1) {

                        if (ChatApp.addSentMessage(message)) {
                            System.out.println("Message sent successfully.");
                        } else {
                            System.out.println("Sent message list is full.");
                        }

                    } else if (action == 2) {

                        if (ChatApp.storeMessage(message)) {
                            System.out.println("Message stored successfully.");
                        } else {
                            System.out.println("Stored message list is full.");
                        }

                    } else if (action == 3) {

                        if (ChatApp.disregardMessage(message)) {
                            System.out.println("Message disregarded.");
                        } else {
                            System.out.println("Disregarded list is full.");
                        }

                    } else {
                        System.out.println("Invalid action.");
                    }

                    break;

                case 2:

                    ChatApp.displaySentMessages();
                    break;

                case 3:

                    ChatApp.displayMessageReport();
                    break;

                case 4:

                    System.out.println("\n===== STORED MESSAGES =====");

                    Message[] stored = ChatApp.getStoredMessages();

                    for (int i = 0;
                         i < ChatApp.getStoredCount(); i++) {

                        System.out.println(
                                stored[i].getMessageID()
                                + " - "
                                + stored[i].getRecipient()
                                + " - "
                                + stored[i].getMessage());
                    }

                    break;

                case 5:

                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:

                    System.out.println("Invalid menu option.");
            }

        } while (choice != 5);

        input.close();
    }
}
