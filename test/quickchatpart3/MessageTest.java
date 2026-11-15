
package quickchatpart3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Asertions.*;

public class MessageTest {
    
    @BeforeEach
    public void setUp() {
    }
        // Each test uses a fresh Java process or clean state.
    

    @Test
    public void testMessageID() {
        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello QuickChat",
                "Sent"
        );

        assertEquals("1234567890", message.getMessageID());
    }

    @Test
    public void testRecipient() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello QuickChat",
                "Sent"
        );

        assertEquals("+27821234567", message.getRecipient());
    }

    @Test
    public void testMessageContent() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello QuickChat",
                "Sent"
        );

        assertEquals("Hello QuickChat", message.getMessage());
    }

    @Test
    public void testMessageStatus() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello QuickChat",
                "Sent"
        );

        assertEquals("Sent", message.getMessageStatus());
    }

    @Test
    public void testMessageHash() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello QuickChat",
                "Sent"
        );

        assertEquals(
                "12:2:HELLOQUICKCHAT",
                message.getMessageHash()
        );
    }

    @Test
    public void testChangeMessageStatus() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Hello Dk",
                "Sent"
        );

        message.setMessageStatus("Stored");

        assertEquals("Stored", message.getMessageStatus());
    }

    @Test
    public void testAddSentMessage() {

        Message message = new Message(
                "1234567890",
                "+27821234567",
                "Testing sent message",
                "Sent"
        );

        assertTrue(ChatApp.addSentMessage(message));
        assertEquals("Sent", message.getMessageStatus());
    }

    @Test
    public void testDisregardMessage() {

        Message message = new Message(
                "2234567890",
                "+27821234567",
                "Testing disregarded message",
                "New"
        );

        assertTrue(ChatApp.disregardMessage(message));
        assertEquals("Disregarded", message.getMessageStatus());
    }

    @Test
    public void testStoreMessage() {

        Message message = new Message(
                "3234567890",
                "+27821234567",
                "Testing stored message",
                "New"
        );

        assertTrue(ChatApp.storeMessage(message));
        assertEquals("Stored", message.getMessageStatus());
    }

    @Test
    public void testSearchMessageById() {

        Message message = new Message(
                "4234567890",
                "+27821234567",
                "Search this message",
                "Sent"
        );

        ChatApp.addSentMessage(message);

        assertNotNull(
                ChatApp.searchMessageById("4234567890")
        );
    }
}
