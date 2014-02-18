package net.pure2.jbot;

import java.io.IOException;
import java.util.HashMap;
import net.pure2.jbot.bot.Bot;
import net.pure2.jbot.bot.MessageParser;
import net.pure2.jbot.component.Channel;
import net.pure2.jbot.component.Server;
import net.pure2.jbot.component.User;

/**
 *
 * @author Pure_
 */
public class HelpBot extends Bot {
    
    /**
     * The command prefix.
     */
    private static final String COMMAND_PREFIX = ".";
    
    /**
     * Our hashmap of help items.
     */
    public static final HashMap<String, HelpItem> helpItems = new HashMap<>();
    
    /**
     * Whether or not the bot will respond to commands.
     */
    private boolean captureCommands = true;

    /**
     * @param args the command line arguments
     * 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // Server details
        Server server = new Server("irc.moparisthebest.xxx", 6667);
        
        // Bot identity
        User user = new User("HelpBot69", "HelpBot", "MoparScape's official Help Bot.");
        
        // Auto-join channels
        Channel[] channels = new Channel[] {
            new Channel("#help")
        };
        
        // Getting help contents
        HelpItem[] helpItems = Utility.getHelpContents();
        
        for (HelpItem helpItem : helpItems) {
            HelpBot.helpItems.put(helpItem.getName(), helpItem);
        }
        
        // Starting our bot
        HelpBot helpBot = new HelpBot(server, user, channels);
        helpBot.initialise();
        helpBot.start();
    }
    
    /**
     * Constructs the help-bot.
     * 
     * @param server server to connect to
     * @param user user to identify as
     * @param channels channels to auto-join
     */
    public HelpBot(Server server, User user, Channel[] channels) {
        super(server, user, channels);
    }

    @Override
    public void onDataReceived(String data) {
        // Printing the raw input
        System.out.println(data.trim());
    }

    @Override
    public void onMessageReceived(MessageParser.MessageData messageData) {
        // Capturing commands
        if (messageData.getMessage().startsWith(COMMAND_PREFIX)) {
            String command = messageData.getMessage().substring(1);
            String message = "";
            
            // Getting the command details
            if (messageData.getMessage().contains(" ")) {
                message = messageData.getMessage()
                        .substring(messageData.getMessage().indexOf(" ") + 1);
                command = messageData.getMessage()
                    .substring(1, messageData.getMessage().indexOf(" "));
            }
            
            // Calling the event
            commandReceived(command, message, messageData.getDest(), messageData.getNickname());
        }
    }
    
    /**
     * Called when a command is received.
     * 
     * @param command command name
     * @param message message
     * @param destination destination sent to
     * @param sender the person who used the command (nick)
     */
    private void commandReceived(String command, String message, String destination,
            String sender) {
        // Returning if command listening is disabled
        if (!captureCommands) {
            return;
        }
        
        // Processing user command
        switch(command) {
            // Sends the help 'menu' to the given user
            case "help":
                sendHelp(destination);
                break;
                
            // Help items
            default:
                if (helpItems.containsKey(command)) {
                    String[] lines = helpItems.get(command).getContent().split("\n");
                    
                    for (String line : lines) {
                        this.getMessageBuilder().appendPrivateMessage(destination, line);
                    }
                }
        }
    }
    
    /**
     * Sends the help list to a person.
     * 
     * @param sender nick to send to
     */
    private void sendHelp(String sender) {
        getMessageBuilder().appendPrivateMessage(sender, "List of help commands:");
        
        for (HelpItem helpItem : helpItems.values()) {
            getMessageBuilder().appendPrivateMessage(sender, "" + COMMAND_PREFIX
                    + helpItem.getName() + "" + " - " + helpItem.getDescription());
        }
    }
    
}
