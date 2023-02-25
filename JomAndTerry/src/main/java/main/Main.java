package main;

import javax.swing.*;

/**
 * @Title Jom and Terry
 * @Des This is a project for CMPT 276
 * @Author Wendi Xiao, Katherine Lee, Deep Bhimani, Beibei Tang
 * @Date 02-23-2023
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("test");

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
