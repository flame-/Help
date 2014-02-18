package net.pure2.jbot.bot;

/**
 *
 * @author Pure_
 */
public class MessageParser {

    /**
     * Gets data from the given line.
     * 
     * @param line line to parse
     * @return message data
     */
    public static MessageData getData(String line) {
        try {
            // Checking if the line is valid
            if (!line.contains("!")) {
                return null;
            }

            // Trimming the starting colon
            if (line.startsWith(":")) {
                line = line.substring(1);
            }
            
            // Splitting data
            String[] data = line.split(" ");
            
            // Getting the sender nick!user@host
            String sender = data[0];

            // Getting the nickname
            String nickname = line.substring(0, line.indexOf("!"));

            // Getting the command
            String command = data[1];

            // Getting the channel
            String channel = data[2];
            
            // Getting the message
            String message = line.substring(line.indexOf(":") + 1);

            return new MessageData(sender, nickname, command, channel, message);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * A class to contain the current parsed message's relevant data.
     * 
     * @author Pure_
     */
    public static class MessageData {
        
        private final String sender;
        private final String nickname;
        private final String command;
        private final String destination;
        private final String message;
        
        /**
         * Constructs a new messagedata object.
         * 
         * @param sender sender
         * @param nickname senders nick
         * @param command command used
         * @param destination destination
         * @param message message
         */
        public MessageData(String sender, String nickname, String command,
                String destination, String message) {
            this.sender = sender;
            this.nickname = nickname;
            this.command = command;
            this.destination = destination;
            this.message = message;
        }
        
        /**
         * Gets the sender of the message, nick!user@host.
         * 
         * @return sender
         */
        public String getSender() {
            return sender;
        }
        
        /**
         * Gets the nickname of the sender.
         * 
         * @return nickname
         */
        public String getNickname() {
            return nickname;
        }
        
        /**
         * Gets the command of the message, e.g. PRIVMSG, NOTICE.
         * 
         * @return command
         */
        public String getCommand() {
            return command;
        }
        
        /**
         * Gets the destination sent in, e.g. #chan, nick.
         * 
         * @return destination
         */
        public String getDest() {
            return destination;
        }
        
        /**
         * Gets the actual message sent.
         * 
         * @return message
         */
        public String getMessage() {
            return message;
        }
    }
}
