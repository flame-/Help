package net.pure2.jbot.bot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A WriterQueue model from an older project. This will be improved upon later.
 * 
 * @author Pure_
 */
public class WriterQueue extends Thread {
    
    public BufferedWriter writer;
    public Queue<String> queue = new LinkedList<>();

    /**
     * Constructs this WriterQueue.
     * 
     * @param socket socket to write to
     * @throws IOException 
     */
    public WriterQueue(Socket socket) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /**
     * Handles the WriterQueue thread execution.
     */
    @Override
    public void run() {
        while (true) {
            try {
                processQueue();
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }
        }
    }
    
    /**
     * This method processes the writer queue.
     */
    public void processQueue() {
        try {
            // Checking if there is an item available
            if (queue.peek() != null) {
                // Getting the item and removing it from the queue
                String item = queue.poll();
                
                if (!item.isEmpty()) {
                    writer.write(item);

                    // Printing the message, we trim it so that the console isn't flooded with new lines
                    System.out.println("[WriterQueue] " + queue.size() + ": " + item.trim());

                    // Flushing the socket writer every time an item ends with a new line ('\r\n')
                    if (item.endsWith("\r\n")) {
                        writer.flush();
                    }

                    Thread.sleep(100);
                }
            }
        } catch (IOException | InterruptedException e) {
            // Exception
        }
    }
    
    /**
     * Instantly writes to the socket, without being processed by the queue
     * system. Useful for sending urgent data.
     * 
     * @param data the data to send
     * @param endLine whether to end the line or not (appends \r\n).
     * 
     * @throws IOException 
     */
    public void write(String data, boolean endLine) throws IOException {
        if (endLine) {
            writer.write(data + "\r\n");
            writer.flush();
        } else {
            writer.write(data);
        }
    }
    
    /**
     * Adds the given data to the writer queue.
     * 
     * @param data data to add to the queue
     * @param endLine whether to end the line or not (appends \r\n).
     */
    public void add(String data, boolean endLine) {
        queue.add(data + (endLine ? "\r\n" : ""));
    }
    
    /**
     * Adds the given data to the writer queue followed by a new-line.
     * 
     * @param data data to add to the queue
     */
    public void add(String data) {
        queue.add(data + "\r\n");
    }
    
}
