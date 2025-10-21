package Model;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private List<Clue> discoveredClues;
    private int score;
    private boolean caseSolved;
    private Difficulty difficulty;
    private int currentCaseId;
    private String profileInfo;
    private int gamesPlayed;
    private int casesSolved;

    public Player(String name) {
        this.name = name;
        this.discoveredClues = new ArrayList<>();
        this.score = 0;
        this.caseSolved = false;
        this.difficulty = Difficulty.MEDIUM;
        this.currentCaseId = 1;
        this.profileInfo = "New detective on the case";
        this.gamesPlayed = 0;
        this.casesSolved = 0;
    }

    // Enhanced constructor for loading from database
    public Player(int id, String name, String difficulty, int currentCaseId, int score, int gamesPlayed, int casesSolved) {
        this.id = id;
        this.name = name;
        this.difficulty = Difficulty.valueOf(difficulty);
        this.currentCaseId = currentCaseId;
        this.score = score;
        this.gamesPlayed = gamesPlayed;
        this.casesSolved = casesSolved;
        this.discoveredClues = new ArrayList<>();
        this.profileInfo = "Seasoned investigator";
    }

    public void addClue(Clue clue) {
        if (!discoveredClues.contains(clue)) {
            discoveredClues.add(clue);
            score += 10;
        }
    }

    public void completeCase() {
        this.caseSolved = true;
        this.score += 100;
        this.casesSolved++;
        this.gamesPlayed++;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Clue> getDiscoveredClues() { return discoveredClues; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public boolean isCaseSolved() { return caseSolved; }
    public void setCaseSolved(boolean caseSolved) { this.caseSolved = caseSolved; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public int getCurrentCaseId() { return currentCaseId; }
    public void setCurrentCaseId(int currentCaseId) { this.currentCaseId = currentCaseId; }
    public String getProfileInfo() { return profileInfo; }
    public void setProfileInfo(String profileInfo) { this.profileInfo = profileInfo; }
    public int getGamesPlayed() { return gamesPlayed; }
    public void setGamesPlayed(int gamesPlayed) { this.gamesPlayed = gamesPlayed; }
    public int getCasesSolved() { return casesSolved; }
    public void setCasesSolved(int casesSolved) { this.casesSolved = casesSolved; }

    @Override
    public String toString() {
        return String.format("Detective %s | Score: %d | Cases Solved: %d/%d", 
            name, score, casesSolved, gamesPlayed);
    }
}