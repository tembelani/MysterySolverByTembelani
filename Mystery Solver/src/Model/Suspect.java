package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds suspect information and their statements.
 */
public class Suspect {
    private final String name;
    private final String description;
    private final List<String> statements = new ArrayList<>();

    public Suspect(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addStatement(String s) { statements.add(s); }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<String> getStatements() { return statements; }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
