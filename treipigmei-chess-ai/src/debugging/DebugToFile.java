package debugging;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A class that will be used for debugging purposes.
 * It will write to a file on disk instead of System.out which is bound
 * to the XBoard input. 
 * 
 * @author grigoroiualex
 *
 */
public class DebugToFile {
    private static DebugToFile instance = null; 
    private PrintWriter writer;
    
    private DebugToFile() {
        try {
            writer = new PrintWriter("TreiPigMei-debug-" + (int)(Math.random() * 100) + ".txt", "UTF-8");
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    /**
     * If there in no instance already created it creates one.
     * Thus, it ensures that the file isn't reopened (and 
     * truncated to 0 length) before ending the program.
     * 
     * @return the current instance
     */
    public static DebugToFile getInstance() {
        if(instance == null) {
            instance = new DebugToFile();
        }
        
        return instance;
    }
    
    /**
     * Outputs the message to the file and moves on a new line
     * 
     * @param message to be written
     */
    public void output(String message) {
        writer.println(message);
        // nu prea bine. scoate-l cand ii gata
        writer.flush();
    }
    
    /**
     * Closes the opened file and destroys the current instance
     */
    public void close() {
        writer.println();
        writer.close();
        instance = null;
    }
}
