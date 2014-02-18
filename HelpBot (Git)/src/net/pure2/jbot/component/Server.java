package net.pure2.jbot.component;

/**
 * An object which represents an IRC Server.
 *
 * @author Pure_
 */
public class Server {

    /**
     * The server's hostname, e.g. irc.moparisthebest.xxx.
     */
    private String hostname;
    
    /**
     * The server's port, e.g. 6667.
     */
    private int port;
    
    /**
     * Constructs a new Server object.
     * 
     * @param hostname server hostname
     * @param port server port
     */
    public Server(String hostname, int port) {
        setHostname(hostname);
        setPort(port);
    }
    
    /**
     * Gets this servers hostname.
     * 
     * @return server hostname
     */
    public String getHostname() {
        return hostname;
    }
    
    /**
     * Gets this servers port.
     * 
     * @return server port
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Sets this servers hostname.
     * 
     * @param hostname server hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    /**
     * Sets this servers port.
     *
     * @param port server port
     */
    public void setPort(int port) {
        if (port > Short.MAX_VALUE) {
            throw new IllegalArgumentException("The port number cannot be above 65535!");
        }
        this.port = port;
    }
}
