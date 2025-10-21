package Data;

import java.io.*;
import java.nio.file.*;

public class InvestigationLog {
    private static final String DATA_DIR = "data";
    
    public static void saveLog(String notes) {
        try {
            // Ensure data directory exists
            Files.createDirectories(Paths.get(DATA_DIR));
            
            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(DATA_DIR, "saveLog.txt"), 
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.APPEND)) {
                writer.write(notes + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving log: " + e.getMessage());
        }
    }
}