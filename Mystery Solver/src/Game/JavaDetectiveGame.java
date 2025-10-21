package Game;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import Data.DBManager;
import Data.FileManager;
import Model.*;

public class JavaDetectiveGame {
    private CaseData currentCase;
    private final DBManager dbManager;
    private Player currentPlayer;
    
    public JavaDetectiveGame() throws SQLException {
        this.dbManager = new DBManager();
    }
    
    public CaseData getCurrentCase() {
        return currentCase;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    // Add this getter for DBManager access
    public DBManager getDbManager() {
        return dbManager;
    }

    public void createPlayer(String playerName, Difficulty difficulty) {
        currentPlayer = new Player(playerName);
        currentPlayer.setDifficulty(difficulty);
        try {
            dbManager.savePlayerProfile(currentPlayer);
            // Also save to text file for backup
            FileManager.savePlayerProfileToFile(currentPlayer);
        } catch (SQLException | IOException e) {
            System.out.println("Error saving player profile: " + e.getMessage());
        }
    }

    public void loadCase(String storyPath, String cluesPath, String suspectsPath) throws IOException {
        String story = FileManager.readStory(storyPath);
        List<Clue> clues = FileManager.readClues(cluesPath);
        List<Suspect> suspects = FileManager.readSuspects(suspectsPath);

        Map<String, Boolean> questioned = new HashMap<>();
        for (Suspect s : suspects) {
            questioned.put(s.getName(), false);
        }

        this.currentCase = new CaseData(story, clues, suspects, Difficulty.MEDIUM, questioned);
        System.out.println("Case loaded successfully.");
    }

    public void saveLog(String path, String entry) {
        try {
            FileManager.saveLog(path, entry);
        } catch (IOException e) {
            System.out.println("Error saving log: " + e.getMessage());
        }
    }

    public void markSuspectQuestioned(int suspectId) {
        try {
            dbManager.markSuspectQuestioned(suspectId);
        } catch (SQLException e) {
            System.out.println("Error marking suspect as questioned: " + e.getMessage());
        }
    }
}