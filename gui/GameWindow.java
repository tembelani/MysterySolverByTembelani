package gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(){

        setTitle("Java Detective");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centre the window
        setResizable(false); //make the window not resizable

        //main panel with GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,20));
        
        //create buttons for game actions
        JButton startCaseBtn = new JButton("Start Case");
        JButton viewCluesBtn = new JButton("View Clues");
        JButton questionSuspectsBtn = new JButton("Question Suspects");
        JButton makeAccusationBtn = new JButton("Make Accusation");
        JButton exitBtn = new JButton("Exit Game");

        //Add buttons to panel
        buttonPanel.add(startCaseBtn);
        buttonPanel.add(viewCluesBtn);  
        buttonPanel.add(questionSuspectsBtn);
        buttonPanel.add(makeAccusationBtn);
        buttonPanel.add(exitBtn);

        //add panel to frame
        add(buttonPanel);
        setVisible(true);

        // Action listeners for buttons
        startCaseBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "WHO SOLD JOSEPH?\r\n" + //
                        "\r\n" + //
                        "There was a man named Joseph who had eleven brothers who \r\n" + //
                        "were jealous of him because his father favoured him. \r\n" + //
                        "Their father even bought him a technicolor dream coat, \r\n" + //
                        "which was given to Joseph, but this didn't sit well with his brothers. \r\n" + //
                        "So, they plotted to kill him. One of the brothers suggested that \r\n" + //
                        "he be sold to the Ishmaelites in Dothan. \r\n" + //
                        "\r\n" + //
                        "Your guest is to find which of his brothers made this decision? ", "Start case", JOptionPane.INFORMATION_MESSAGE)); // Placeholder action
        viewCluesBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Here are the clues: \n"
            + "- Levi hates maths. In fact, anything that has to do with numbers.\n"
            + "- Judah is an economist by nature, always finds opportunities to exchange and trade.\n"
            + "- Levi and Reuben are allergic to resins.\n"
            + "- Judah loves Silver.\n"
            + "- Reuben is married to an Ishmaelite.\n"
            + "- Levi and Judah can't ride camels.",
            "View Clues", JOptionPane.INFORMATION_MESSAGE)); // Placeholder action
        questionSuspectsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "You can choose a suspect to question: \n- Reuben. \n- Judah. \n- Levi", 
            "Question Suspects", JOptionPane.INFORMATION_MESSAGE)); // Placeholder action
makeAccusationBtn.addActionListener(e -> {
    // Create the options for the accusation
    Object[] options = {"Reuben", "Judah", "Levi"};
    
    // Show the option dialog
    int choice = JOptionPane.showOptionDialog(
        this, 
        "Who do you think sold Joseph to the Ishmaelites?", 
        "Make Accusation", 
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        options, 
        options[0]
    );
    
    // Check the player's choice
    if (choice == 1) { // Judah is the correct answer (index 1 in the array)
        JOptionPane.showMessageDialog(
            this, 
            "Correct! Judah suggested selling Joseph to the Ishmaelites (Genesis 37:26-27).", 
            "Result", 
            JOptionPane.INFORMATION_MESSAGE
        );
    } else if (choice >= 0) { // Any other selection (0 or 2 in this case)
        String selectedName = (String)options[choice];
        JOptionPane.showMessageDialog(
            this, 
            "Incorrect! " + selectedName + " was not the one who suggested selling Joseph.", 
            "Result", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    // If choice is -1, the user closed the dialog without selecting
});
        
        exitBtn.addActionListener(e -> System.exit(0)); // Exit button functionality


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new); // Run the GUI in the Event Dispatch Thread
    }
}
