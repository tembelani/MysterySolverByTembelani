package Data;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import Model.Clue;
import Model.Player;
import Model.Suspect;
import java.util.ArrayList;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String PROFILES_DIR = "profiles";

    // Ensure directories exist
    static {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Files.createDirectories(Paths.get(PROFILES_DIR));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public static String readStory(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(DATA_DIR, "case1.txt")));
    }
    
    public static List<Clue> readClues(String path) throws IOException {
        List<Clue> clues = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(DATA_DIR, "clues.txt"))) {
            String line;
            int id = 1;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("CLUES FROM") && !line.startsWith("-")) {
                    clues.add(new Clue(id++, line));
                } else if (line.startsWith("-")) {
                    clues.add(new Clue(id++, line.substring(2).trim()));
                }
            }
        }
        return clues;
    }

    public static List<Suspect> readSuspects(String filePath) throws IOException {
        List<Suspect> suspects = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(DATA_DIR, "suspects.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Suspect s = new Suspect(parts[0].trim(), parts[1].trim());
                    s.addStatement(parts[2].trim());
                    suspects.add(s);
                }
            }
        }
        return suspects;
    }

    public static void saveLog(String path, String content) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(DATA_DIR, "saveLog.txt"), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND)) {
            bw.write(content);
            bw.newLine();
        }
    }

    // Profile-related file operations
    public static void savePlayerProfileToFile(Player player) throws IOException {
        String filename = player.getName().replaceAll("[^a-zA-Z0-9]", "_") + "_profile.txt";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PROFILES_DIR, filename))) {
            writer.write("Player Profile: " + player.getName() + "\n");
            writer.write("Score: " + player.getScore() + "\n");
            writer.write("Difficulty: " + player.getDifficulty() + "\n");
            writer.write("Cases Solved: " + player.getCasesSolved() + "\n");
            writer.write("Games Played: " + player.getGamesPlayed() + "\n");
            writer.write("Current Case: " + player.getCurrentCaseId() + "\n");
        }
    }

    public static List<String> getPlayerProfileFiles() throws IOException {
        List<String> profiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(PROFILES_DIR), "*.txt")) {
            for (Path entry : stream) {
                profiles.add(entry.getFileName().toString());
            }
        }
        return profiles;
    }
}