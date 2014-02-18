package net.pure2.jbot;

/**
 *
 * @author Pure_
 */
public class HelpItem {
    
    private final String name;
    private final String description;
    private final String content;
    
    public HelpItem(String name, String description, String content) {
        this.name = name;
        this.description = description;
        this.content = content;
    }
    
    public String getName() {
        return name;
    }
    
    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name + "\n" + description + "\n" + content;
    }
    
}
