package Game;

import javax.swing.*;

import Gui.GameWindow;


public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{
                GameWindow window = new GameWindow();
                window.setVisible(true);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
