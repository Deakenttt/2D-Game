package main;

import javax.swing.*;

/**
 * @author Katherine Lee, Deep Bhimani, Wendi Xiao, Beibei Tang
 * @title Jom and Terry
 * @des 2D game project for CMPT 276
 * @since 02-23-2023
 */
public class Main {
    public static void main(String[] args) {

        // Setting up a JFrame class act as main window.
        // Setting the window title.
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jom and Terry");

        // Initiate gamePanel object.
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // Defined and sizes the frame so that all its contents are at or above their preferred sizes.

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame(); // Set up objects placement.

        gamePanel.startGameThread();// Call startGameThread method to start the game.
    }
}
