package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseData {
    private String story;
    private List<Clue> clues;
    private List<Suspect> suspects;
    private Difficulty difficulty;
    private Map<String, Boolean> questionMap; // tracks which suspects have been questioned

    // ✅ Constructor now matches the one called in JavaDetectiveGame
    public CaseData(String story, List<Clue> clues, List<Suspect> suspects, Difficulty difficulty, Map<String, Boolean> questionMap) {
        this.story = story;
        this.clues = clues;
        this.suspects = suspects;
        this.difficulty = difficulty;
        // Prevent null pointer issues
        this.questionMap = (questionMap != null) ? questionMap : new HashMap<>();
    }

    public String getStory() {
        return story;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public List<Suspect> getSuspects() {
        return suspects;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Map<String, Boolean> getQuestionMap() {
        return questionMap;
    }

    public void setQuestionMap(Map<String, Boolean> questionMap) {
        this.questionMap = questionMap;
    }
}
