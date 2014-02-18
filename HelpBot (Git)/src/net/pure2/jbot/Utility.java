package net.pure2.jbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Pure_
 */
public class Utility {
    
    /**
     * Gets the help content from their respective files.
     * 
     * @return HelpItem[] or null if an exception occurs
     */
    public static HelpItem[] getHelpContents() {
        try {
            // Creating an array to store help contents
            HelpItem[] helpItems;

            // Populating the array
            File dir = new File("./data/help/");
            File[] files = dir.listFiles();
            helpItems = new HelpItem[files.length];
            
            // Printing the amount of items being loaded
            System.out.println("Loading " + files.length + " help items.");

            // Reading each file
            for (int i = 0; i < files.length; i++) {
                BufferedReader br = new BufferedReader(new FileReader(files[i]));

                String helpName = files[i].getName().substring(0, files[i].getName().indexOf("."));
                String helpMenu = br.readLine();
                String helpContent = "";

                br.readLine(); // Reading the blank line.

                String line = "";

                while ((line = br.readLine()) != null) {
                    helpContent += line + "\n";
                }

                helpItems[i] = new HelpItem(helpName, helpMenu, helpContent);
            }

            // Returning the array
            return helpItems;
        } catch (IOException e) {
            return null;
        }
    }
}
