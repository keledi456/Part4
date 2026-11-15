
package quickchatpart3;

public class Message {
   
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String messageStatus;

    public Message(String messageID, String recipient,
                   String message, String messageStatus) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageStatus = messageStatus;

        this.messageHash = createMessageHash();
    }

    public String createMessageHash() {

        String firstTwoDigits = messageID.length() >= 2
                ? messageID.substring(0, 2)
                : messageID;

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (firstTwoDigits + ":"
                + words.length + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public void setMessage(String message) {
        this.message = message;
        this.messageHash = createMessageHash();
    }
}
