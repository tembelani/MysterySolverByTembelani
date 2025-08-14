package model;

public class choices {
    private String choiceText;
    private int choiceId;

    public choices(String a, int b) {
        a = choiceText;
        b = choiceId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public int getChoiceId() {
        return choiceId;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "choiceText='" + choiceText + '\'' +
                ", choiceId=" + choiceId +
                '}';
    }
}
