package model;

public class Clue {
    private int id;
    private String description;

    public Clue(int a, String b) {
        a = id;
        b = description;
    }

    //Getters
    public String getDescription() { 
        return description;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Clue ID: " + id + "\nDescription: " + description + "\n";
    }
}
