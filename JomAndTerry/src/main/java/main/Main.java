package main;

import javax.swing.*;

/**
 * @title Jom and Terry
 * @des This is a project for CMPT 276
 * @author  Wendi Xiao, Katherine Lee, Deep Bhimani, Beibei Tang
 * @since  02-23-2023
 */
public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jom and Terry");

        // Initiate gamePanel object.
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Set up objects placement.
        gamePanel.setUpGame();

        // Call startGameThread method to start the game.
        gamePanel.startGameThread();
    }
}
