package Model;

public class Clue {
    private final String text;
    private final int id;

    public Clue(int id, String text){
        this.id = id;
        this.text = text;
    }

    public String getText(){return text;}
    public int getId(){return id;}

    @Override
    public String toString(){
        return id + ")" + text;
    }   
}

