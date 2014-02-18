package net.pure2.jbot.component;

/**
 * An object which represents a an IRC Channel.
 *
 * @author Pure_
 */
public class Channel {

    /**
     * The channel name, e.g. #chan.
     */
    private final String channel;
    
    /**
     * The channel password, e.g. secret.
     */
    private String password;
    
    /**
     * Constructs a new Channel object.
     * 
     * @param channel channel name
     */
    public Channel(String channel) {
        this.channel = channel;
    }
    
    /**
     * Constructs a new Channel object.
     * 
     * @param channel channel name
     * @param password channel password
     */
    public Channel(String channel, String password) {
        this.channel = channel;
        this.password = password;
    }
    
    /**
     * Gets the channel.
     * 
     * @return channel name
     */
    public String getChannel() {
        return channel;
    }
    
    /**
     * Gets the channel password.
     * 
     * @return channel password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Returns whether or not identification is required in this channel.
     * 
     * @return whether or not identification is required
     */
    public boolean isAuthenticationRequired() {
        return password != null && password.length() > 0;
    }
    
    /**
     * Gets the command required to join this channel.
     * 
     * @return channel join command
     */
    public String getJoinCommand() {
        String command = "JOIN " + channel;
        
        if (isAuthenticationRequired()) {
            command += " :" + password;
        }
        return command;
    }
}
