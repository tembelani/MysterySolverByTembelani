package Gui;

import Game.JavaDetectiveGame;
import javax.swing.*;
import Model.*;
import Data.DBManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GameWindow extends JFrame {
    private final JavaDetectiveGame game;
    private CaseData currentCase;
    private Player currentPlayer;

    public GameWindow() throws SQLException {
        game = new JavaDetectiveGame();
        setTitle("Scrolls of Truth: The Prophet's Inquiry");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        showPlayerSetup();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header with player info
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel playerLabel = new JLabel("No player selected");
        headerPanel.add(playerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Main button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton startBttn = new JButton("Unseal the Scroll");
        JButton viewClueBttn = new JButton("Reveal Sacred Clues");
        JButton questionBttn = new JButton("Interrogate the Witnesses");
        JButton accuseBttn = new JButton("Declare the Guilty");
        JButton profileBttn = new JButton("Detective Profile");
        JButton exitBttn = new JButton("Depart from the Temple");

        buttonPanel.add(startBttn);
        buttonPanel.add(viewClueBttn);
        buttonPanel.add(questionBttn);
        buttonPanel.add(accuseBttn);
        buttonPanel.add(profileBttn);
        buttonPanel.add(exitBttn);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Update player label
        Runnable updatePlayerLabel = () -> {
            if (currentPlayer != null) {
                playerLabel.setText("Detective: " + currentPlayer.getName() + " | Score: " + currentPlayer.getScore());
            }
        };

        startBttn.addActionListener(e -> onStartCase());
        viewClueBttn.addActionListener(e -> onViewClues());
        questionBttn.addActionListener(e -> onQuestionSuspects());
        accuseBttn.addActionListener(e -> onMakeAccusation());
        profileBttn.addActionListener(e -> onShowProfile());
        exitBttn.addActionListener(e -> onExitGame());
    }

    private void showPlayerSetup() {
        Object[] options = {"Create New Detective", "Load Existing Detective"};
        int choice = JOptionPane.showOptionDialog(this,
            "Welcome to the Scrolls of Truth!\nHow would you like to proceed?",
            "Detective Registration",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == 0) {
            createNewPlayer();
        } else if (choice == 1) {
            loadExistingPlayer();
        } else {
            System.exit(0);
        }
    }

    private void createNewPlayer() {
        String playerName = JOptionPane.showInputDialog(this,
            "Enter your detective's name:",
            "New Detective",
            JOptionPane.QUESTION_MESSAGE);

        if (playerName != null && !playerName.trim().isEmpty()) {
            // Difficulty selection
            Difficulty[] difficulties = Difficulty.values();
            Difficulty selectedDifficulty = (Difficulty) JOptionPane.showInputDialog(this,
                "Choose your investigative prowess:",
                "Difficulty Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,
                difficulties[1]);

            if (selectedDifficulty != null) {
                game.createPlayer(playerName.trim(), selectedDifficulty);
                this.currentPlayer = game.getCurrentPlayer();
                JOptionPane.showMessageDialog(this,
                    "Welcome, Detective " + playerName + "!\nYour journey begins...",
                    "Commissioned",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            showPlayerSetup(); // Retry if no name entered
        }
    }

    private void loadExistingPlayer() {
        try {
            List<Player> players = game.getDbManager().getAllPlayers();
            if (players.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No existing detectives found. Creating a new one.",
                    "No Profiles",
                    JOptionPane.INFORMATION_MESSAGE);
                createNewPlayer();
                return;
            }

            String[] playerNames = players.stream()
                .map(Player::getName)
                .toArray(String[]::new);

            String selectedName = (String) JOptionPane.showInputDialog(this,
                "Choose your detective:",
                "Load Detective",
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerNames,
                playerNames[0]);

            if (selectedName != null) {
                Player loadedPlayer = game.getDbManager().loadPlayerProfile(selectedName);
                if (loadedPlayer != null) {
                    this.currentPlayer = loadedPlayer;
                    JOptionPane.showMessageDialog(this,
                        "Welcome back, Detective " + loadedPlayer.getName() + "!\n" +
                        "Score: " + loadedPlayer.getScore() + " | Cases Solved: " + loadedPlayer.getCasesSolved(),
                        "Welcome Back",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading detective profiles: " + e.getMessage(),
                "Load Error",
                JOptionPane.ERROR_MESSAGE);
            createNewPlayer();
        }
    }

    private void onStartCase() {
        if (currentPlayer == null) {
            JOptionPane.showMessageDialog(this, "You must first create or load a detective.");
            showPlayerSetup();
            return;
        }

        try {
            game.loadCase("data/case1.txt", "data/clues.txt", "data/suspects.txt");
            currentCase = game.getCurrentCase();
            JOptionPane.showMessageDialog(this,
                "The parchment has been revealed:\n\n" + currentCase.getStory(),
                "Scroll Unsealed",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this,
                "The scribes failed to retrieve the scrolls:\n" + e1.getMessage(),
                "Scroll Retrieval Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onViewClues() {
        if (currentCase == null) {
            JOptionPane.showMessageDialog(this, "You must first unseal the scroll.");
            return;
        }

        StringBuilder cluesText = new StringBuilder("Sacred Clues:\n\n");
        List<Clue> clues = currentCase.getClues();
        for (Clue c : clues) {
            cluesText.append("• ").append(c.toString()).append("\n");
        }

        JTextArea area = new JTextArea(cluesText.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scroll, "Clues from the Scroll", JOptionPane.PLAIN_MESSAGE);
    }

    private void onQuestionSuspects() {
        if (currentCase == null) {
            JOptionPane.showMessageDialog(this, "You must first unseal the scroll.");
            return;
        }

        List<Suspect> suspects = currentCase.getSuspects();
        String[] names = suspects.stream().map(Suspect::getName).toArray(String[]::new);
        String chosen = (String) JOptionPane.showInputDialog(this,
            "Select a witness to interrogate:",
            "Interrogation Chamber",
            JOptionPane.PLAIN_MESSAGE, null, names, names[0]);

        if (chosen != null) {
            Suspect s = suspects.stream().filter(x -> x.getName().equals(chosen)).findFirst().orElse(null);
            if (s != null) {
                currentCase.getQuestionMap().put(s.getName(), true);

                StringBuilder statements = new StringBuilder("Testimonies of the Witness:\n\n");
                for (String statement : s.getStatements()) {
                    statements.append("❝ ").append(statement).append(" ❞\n\n");
                }

                JTextArea area = new JTextArea(statements.toString());
                area.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(area);
                scrollPane.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(this, scrollPane,
                    "Interrogation of: " + s.getName(),
                    JOptionPane.INFORMATION_MESSAGE);

                game.saveLog("data/saveLog.txt", "Interrogated " + s.getName());
                
                // Add to player's discovered information
                currentPlayer.addClue(new Clue(0, "Interviewed suspect: " + s.getName()));
            }
        }
    }

    private void onMakeAccusation() {
        if (currentCase == null) {
            JOptionPane.showMessageDialog(this, "You must first unseal the scroll.");
            return;
        }

        List<Suspect> suspects = currentCase.getSuspects();
        String[] names = suspects.stream().map(Suspect::getName).toArray(String[]::new);
        String accused = (String) JOptionPane.showInputDialog(this,
            "Whom dost thou accuse?",
            "Judgment Before the Elders",
            JOptionPane.PLAIN_MESSAGE, null, names, names[0]);

        if (accused != null && currentPlayer != null) {
            String culprit = "Judah"; // Hardcoded for now
            boolean correct = accused.equals(culprit);
            
            if (correct) {
                currentPlayer.completeCase();
                try {
                    game.getDbManager().savePlayerProfile(currentPlayer);
                } catch (SQLException e) {
                    System.err.println("Error saving player progress: " + e.getMessage());
                }
            }

            String outcome = correct
                ? "Verily, thou hast uncovered the truth. Justice is served!\n" +
                  "Your score has increased to: " + currentPlayer.getScore()
                : "Nay, the true transgressor was " + culprit + ".\n" +
                  "Learn from this failure, detective.";

            JOptionPane.showMessageDialog(this, outcome,
                "Judgment Rendered",
                correct ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

            game.saveLog("data/saveLog.txt", 
                "Detective " + currentPlayer.getName() + " accused " + accused + 
                ". Verdict: " + (correct ? "CORRECT" : "WRONG"));
        }
    }

    private void onShowProfile() {
        if (currentPlayer == null) {
            JOptionPane.showMessageDialog(this, "No detective profile loaded.");
            return;
        }

        String profileText = String.format("""
            DETECTIVE PROFILE
            =================
            
            Name: %s
            Score: %d
            Difficulty: %s
            Cases Solved: %d
            Games Played: %d
            Current Case: %d
            
            Investigative Record:
            - Clues Discovered: %d
            - Current Status: %s
            """,
            currentPlayer.getName(),
            currentPlayer.getScore(),
            currentPlayer.getDifficulty(),
            currentPlayer.getCasesSolved(),
            currentPlayer.getGamesPlayed(),
            currentPlayer.getCurrentCaseId(),
            currentPlayer.getDiscoveredClues().size(),
            currentPlayer.isCaseSolved() ? "Case Closed" : "Investigation Ongoing"
        );

        JTextArea area = new JTextArea(profileText);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Detective Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onExitGame() {
        if (currentPlayer != null) {
            try {
                game.getDbManager().savePlayerProfile(currentPlayer);
                JOptionPane.showMessageDialog(this,
                    "Your progress has been saved, Detective " + currentPlayer.getName() + ".\n" +
                    "Final Score: " + currentPlayer.getScore(),
                    "Case Files Archived",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.err.println("Error saving final progress: " + e.getMessage());
            }
        }
        System.exit(0);
    }
}