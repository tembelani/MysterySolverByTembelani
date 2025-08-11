package model;

public class Suspect {
    private String name;
    private String description;


    public Suspect(String name, String description) {
        this.name = name;
        this.description = description;
    }
    //Getters
    public String getName() {return name;}   
    public String getDescription() {return description;}
    
    @Override
    public String toString() {
        return name + "\n " + description + "\n";
    }
}
