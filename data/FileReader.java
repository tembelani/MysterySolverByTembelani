package data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileReader {

    public static String readFile(String filePath){
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                content.append(line).append("\n");
            }
        } catch(IOException e){
            e.printStackTrace();   
            content.append("Error reading file: ").append(e.getMessage());
        }
        return content.toString();
    }

    public static void saveLog(String path, String notes){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(notes);
            writer.newLine();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
