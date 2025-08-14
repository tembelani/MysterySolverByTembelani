package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    // Pastel color palette with ancient Arabic/Biblical theme
    private final Color PASTEL_SAND = new Color(237, 221, 196);
    private final Color PASTEL_TURQUOISE = new Color(167, 219, 216);
    private final Color PASTEL_GOLD = new Color(234, 215, 153);
    private final Color PASTEL_RED = new Color(210, 145, 145);
    private final Color PASTEL_BLUE = new Color(176, 196, 222);
    private final Color DARK_BROWN = new Color(101, 67, 33);
    private final Font ARABIC_FONT = new Font("Dialog", Font.BOLD, 14);

    public GameWindow() {
        setTitle("Desert Mysteries: The Case of Joseph");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); // Now resizable
        
        // Main container with background color
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PASTEL_SAND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title label with styling
        JLabel titleLabel = new JLabel("The Mystery of Joseph's Betrayal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arabic Typesetting", Font.BOLD, 24));
        titleLabel.setForeground(DARK_BROWN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Button panel with pastel colors
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(PASTEL_SAND);
        buttonPanel.setOpaque(false);
        
        // Create styled buttons
        JButton startCaseBtn = createStyledButton("Begin the Investigation", PASTEL_TURQUOISE);
        JButton viewCluesBtn = createStyledButton("Examine the Clues", PASTEL_GOLD);
        JButton questionSuspectsBtn = createStyledButton("Question the Brothers", PASTEL_BLUE);
        JButton makeAccusationBtn = createStyledButton("Make Your Accusation", PASTEL_RED);
        JButton exitBtn = createStyledButton("Leave the Desert", new Color(200, 200, 200));
        
        // Add buttons to panel
        buttonPanel.add(startCaseBtn);
        buttonPanel.add(viewCluesBtn);  
        buttonPanel.add(questionSuspectsBtn);
        buttonPanel.add(makeAccusationBtn);
        buttonPanel.add(exitBtn);
        
        // Add decorative panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(PASTEL_SAND);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Add background image panel
        JLabel background = new JLabel(new ImageIcon("path_to_arabic_desert_image.png")); // Add your image path
        background.setLayout(new BorderLayout());
        background.add(contentPanel);
        
        mainPanel.add(background, BorderLayout.CENTER);
        add(mainPanel);

        // Action listeners with themed messages
        startCaseBtn.addActionListener(e -> showThemedMessage(
            "<html><div style='text-align: center;'><b>WHO SOLD JOSEPH?</b><br><br>" +
            "In the land of Canaan, a favored son named Joseph wore a coat of many colors,<br>" +
            "a gift from his father that bred jealousy among his eleven brothers.<br><br>" +
            "They plotted against him, first to kill, then one suggested a different fate -<br>" +
            "to sell him to Ishmaelite traders passing by Dothan's gate.<br><br>" +
            "<i>Your quest is to discover which brother made this suggestion,<br>" +
            "and uncover the truth behind this ancient transgression.</i></div></html>",
            "The Desert Mystery Begins"
        ));

        viewCluesBtn.addActionListener(e -> showThemedMessage(
            "<html><div style='text-align: center;'><b>CLUES FROM THE DESERT:</b><br><br>" +
            "• <b>Levi</b> despises numbers and all calculations<br>" +
            "• <b>Judah</b> has an economist's mind, always seeking profitable transactions<br>" +
            "• Both <b>Levi</b> and <b>Reuben</b> suffer from resin allergies<br>" +
            "• <b>Judah</b> has a particular fondness for silver pieces<br>" +
            "• <b>Reuben</b> is wed to a woman of Ishmaelite descent<br>" +
            "• Neither <b>Levi</b> nor <b>Judah</b> can ride the desert's camels</div></html>",
            "Ancient Clues Revealed"
        ));

        questionSuspectsBtn.addActionListener(e -> {
            Object[] options = {"Reuben - The Firstborn", "Judah - The Trader", "Levi - The Pious"};
            int choice = JOptionPane.showOptionDialog(
                this,
                "<html><div style='text-align: center;'>Which brother will you question<br>under the desert sun?</div></html>",
                "Question the Brothers",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            if (choice == 0) {
                showThemedMessage(
                    "<html><div style='text-align: center;'>Reuben speaks:<br><br>" +
                    "<i>\"I wanted to save Joseph! I told them to throw him in the pit<br>" +
                    "so I could return later and rescue him in secret.\"</i><br><br>" +
                    "His Ishmaelite wife nods in agreement.</div></html>",
                    "Reuben's Testimony"
                );
            } else if (choice == 1) {
                showThemedMessage(
                    "<html><div style='text-align: center;'>Judah proclaims:<br><br>" +
                    "<i>\"What profit is there in killing our own brother?<br>" +
                    "Let us sell him to these traders and be rid of him!<br>" +
                    "After all, he is our flesh and blood.\"</i><br><br>" +
                    "He fingers some silver coins absentmindedly.</div></html>",
                    "Judah's Counsel"
                );
            } else if (choice == 2) {
                showThemedMessage(
                    "<html><div style='text-align: center;'>Levi declares:<br><br>" +
                    "<i>\"Numbers and trade mean nothing to me!<br>" +
                    "I would have left Joseph in the pit to die,<br>" +
                    "but I would never deal with Ishmaelites!\"</i><br><br>" +
                    "He sneezes as camel hair floats by.</div></html>",
                    "Levi's Words"
                );
            }
        });
        
        makeAccusationBtn.addActionListener(e -> {
            Object[] options = {
                "<html><b>Reuben</b> - The Firstborn</html>", 
                "<html><b>Judah</b> - The Trader</html>", 
                "<html><b>Levi</b> - The Pious</html>"
            };
            
            int choice = JOptionPane.showOptionDialog(
                this, 
                "<html><div style='text-align: center;'>Under the desert sky, you must decide:<br>" +
                "Which brother sold Joseph to the Ishmaelite traders?</div></html>", 
                "Final Accusation", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]
            );
            
            if (choice == 1) {
                showThemedMessage(
                    "<html><div style='text-align: center;'><b>You have uncovered the truth!</b><br><br>" +
                    "As written in Genesis 37:26-27:<br>" +
                    "<i>\"Judah said to his brothers, 'What will we gain if we kill our brother?<br>" +
                    "Let's sell him to the Ishmaelites and not harm him ourselves.'\"</i><br><br>" +
                    "The mystery is solved, and justice will be served.</div></html>",
                    "Wisdom Prevails"
                );
            } else if (choice >= 0) {
                String[] names = {"Reuben", "Judah", "Levi"};
                showThemedMessage(
                    "<html><div style='text-align: center;'><b>Your accusation misses the mark!</b><br><br>" +
                    names[choice] + " was not the one who suggested selling Joseph.<br>" +
                    "Look to the clues again and consider Judah's economic mind.</div></html>",
                    "Incorrect Judgment"
                );
            }
        });
        
        exitBtn.addActionListener(e -> System.exit(0));
        
        setVisible(true);
    }
    
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(ARABIC_FONT);
        button.setForeground(DARK_BROWN);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
        
        return button;
    }
    
    private void showThemedMessage(String message, String title) {
        JOptionPane.showMessageDialog(
            this,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static void main(String[] args) {
        try {
            // Set Arabic look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.messageFont", new Font("Arabic Typesetting", Font.PLAIN, 14));
            UIManager.put("OptionPane.background", new Color(237, 221, 196));
            UIManager.put("Panel.background", new Color(237, 221, 196));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new GameWindow();
        });
    }
}