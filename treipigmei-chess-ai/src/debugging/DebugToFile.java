package debugging;

import java.io.IOException;
import java.io.PrintWriter;

public class DebugToFile {
    private static DebugToFile instance = null; 
    private PrintWriter writer;
    
    private DebugToFile() {
        try {
            writer = new PrintWriter("TreiPigMei-debug.txt", "UTF-8");
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public DebugToFile getInstance() {
        if(instance == null) {
            instance = new DebugToFile();
        }
        
        return instance;
    }
    
    public void output(String message) {
        writer.println(message);
    }
}
