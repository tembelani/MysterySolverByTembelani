package Model;

/**
 * Enum representing different difficulty levels for cases
 */
public enum Difficulty {
    EASY("Easy - More clues, obvious hints"),
    MEDIUM("Medium - Balanced challenge"),
    HARD("Hard - Fewer clues, subtle hints");
    
    private final String description;
    
    Difficulty(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}