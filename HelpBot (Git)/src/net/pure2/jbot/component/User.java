package net.pure2.jbot.component;

/**
 *
 * @author Pure_
 */
public class User {

    private final String nickname;
    private final String username;
    private final String realname;
    private String password;
    
    /**
     * Constructs a new User object.
     * 
     * @param nickname nickname
     * @param username username
     * @param realname realname
     */
    public User(String nickname, String username, String realname) {
        this.nickname = nickname;
        this.username = username;
        this.realname = realname;
    }
    
    /**
     * Constructs a new User object.
     * 
     * @param nickname nickname
     * @param username username
     * @param realname realname
     * @param password the nicknames password
     */
    public User(String nickname, String username, String realname, String password) {
        this.nickname = nickname;
        this.username = username;
        this.realname = realname;
        this.password = password;
    }
    
    /**
     * Gets this Users username.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets this Users nickname.
     * 
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * Gets this Users nicknames password.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Checks whether or not identification for this nickname is required.
     * 
     * @return identification required
     */
    public boolean isIdentificationRequired() {
        return password != null && password.length() > 0;
    }
    
    /**
     * Returns this user in a format suitable to identify with the IRC server
     * using the USER command.
     * 
     * @return user
     */
    public String toUser() {
        return username + " 8 * :" + realname;
    }
}
