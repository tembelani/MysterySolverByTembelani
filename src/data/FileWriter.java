package data;
import java.io.BufferedWriter;
import java.io.IOException; 


public class FileWriter {
    public static void writeLog(String filePath, String logText){
        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath, true))){
            writer.write(logText);
            writer.newLine();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
