package net.pure2.jbot.bot;

/**
 * A class to handle message creation.
 *
 * @author Pure_
 */
public class MessageBuilder {

    /**
     * Stores the writer queue.
     */
    private final WriterQueue writerQueue;
    
    /**
     * Constructs the message builder - throws a null pointer exception if the
     * writer queue instance is null.
     * 
     * @param writerQueue writer queue to bind to
     */
    public MessageBuilder(WriterQueue writerQueue) {
        if (writerQueue == null) {
            throw new NullPointerException();
        }
        this.writerQueue = writerQueue;
    }
    
    /**
     * Sends the specified message.
     * 
     * @param command command to use, e.g. NOTICE, PRIVMSG etc.
     * @param destination destination, e.g. Name, #channel etc.
     * @param message the message to send
     */
    public void appendMessage(String command, String destination, String message) {
        writerQueue.add(command + " " + destination + " :" + message);
    }
    
    /**
     * Sends the specified message, using NOTICE.
     * 
     * @param destination destination, e.g. Name, #channel etc.
     * @param message the message to send
     */
    public void appendNotice(String destination, String message) {
        writerQueue.add("NOTICE " + destination + " :" + message);
    }
    
    /**
     * Sends the specified message, using PRIVMSG.
     * 
     * @param destination destination, e.g. Name, #channel etc.
     * @param message the message to send
     */
    public void appendPrivateMessage(String destination, String message) {
        writerQueue.add("PRIVMSG " + destination + " :" + message);
    }
}