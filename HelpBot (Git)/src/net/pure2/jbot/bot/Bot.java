package net.pure2.jbot.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import net.pure2.jbot.component.Channel;
import net.pure2.jbot.component.Server;
import net.pure2.jbot.component.User;

/**
 * Handles bot logic.
 *
 * @author Pure_
 */
public abstract class Bot {
    
    private final Server server;
    private final User user;
    private final Channel[] channels;
    
    private Socket socket;
    private WriterQueue writerQueue;
    private BufferedReader reader;
    private MessageBuilder messageBuilder;
    
    /**
     * Constructs this Bot.
     * 
     * @param server server to connect to
     * @param user user to identify as
     * @param channels channels to auto-join
     */
    public Bot(Server server, User user, Channel[] channels) {
        this.server = server;
        this.user = user;
        this.channels = channels;
    }
    
    /**
     * Initialises the bot.
     * 
     * @return whether or not the initialisation was successful
     */
    public boolean initialise() {
        try {
            // Initialising the socket
            socket = new Socket(server.getHostname(), server.getPort());

            // Initialising the writer queue and message builder
            writerQueue = new WriterQueue(socket);
            messageBuilder = new MessageBuilder(writerQueue);
            
            // We add the initial data queue in advance, so that issues do not occur with the initial queue and thus identification
            prepareOnConnect();
            
            // Initialising the reader
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    
    /**
     * Adds initial queue data to the writer queue.
     */
    private void prepareOnConnect() {
        // Adding the identification data to the queue
        writerQueue.add("NICK " + user.getNickname());
        writerQueue.add("USER " + user.toUser());
        
        // If a password is set, adding identification with NickServ
        if (user.isIdentificationRequired()) {
            writerQueue.add("PRIVMSG NickServ IDENTIFY " + user.getPassword(), true);
        }
    }
    
    /**
     * Starts the bot.
     */
    public void start() {
        // Starting the stream writing loop
        writerQueue.start();
        
        // Starting the reading loop
        beginRead();
    }
    
    /**
     * The bots reading is handled here.
     */
    private void beginRead() {
        try {
            String line = "";
            
            // Joining channels when allowed to
            while (!(line = reader.readLine()).contains("001")) {
                
            }
            
            // Adding the initial channels to join to the queue
            for (Channel channel : channels) {
                writerQueue.add(channel.getJoinCommand());
            }

            // Getting the current line to parse
            while ((line = reader.readLine()) != null) {
                // Ping-pong
                if (line.startsWith("PING")) {
                    writerQueue.write(line.replace("PING", "PONG"), true);
                    continue; // Capturing it, so no other event is called
                }
                
                // Calling the data received event
                onDataReceived(line.trim());
                
                // Parsing the data
                MessageParser.MessageData messageData = MessageParser.getData(line);
                
                // Skipping parsing if the message data is null
                if (messageData == null) {
                    continue;
                }
                
                // Calling certain events depending on the command parsed
                switch(messageData.getCommand()) {
                    case "PRIVMSG":
                        onMessageReceived(messageData);
                        break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Gets the message builder instance.
     * 
     * @return message builder
     */
    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }
    
    /**
     * An event called when any data is received from the socket.
     * 
     * @param data data received
     */
    public abstract void onDataReceived(String data);
    
    /**
     * An event called when a 'private' message is received from the socket.
     * 
     * @param messageData message data
     */
    public abstract void onMessageReceived(MessageParser.MessageData messageData);
}
