
package gui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    public GameFrame(){
        setTitle("Java Detective");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //center the window

        //main panel with GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //create buttons for game actions
        panel.add(new JButton("Start Case"));
        panel.add(new JButton("View Clues"));
        panel.add(new JButton("Question Suspects"));
        panel.add(new JButton("Make Accusation"));
        panel.add(new JButton("Exit Game"));

        //add panel to frame
        add(panel);
        setVisible(true);
    }
}