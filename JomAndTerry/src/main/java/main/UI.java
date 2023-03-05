package main;

import object.OBJ_Cheese;
import object.OBJ_Steak;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage cheeseImg;
    BufferedImage steakImg;

    public boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;

    public boolean gameEnd = false;
    public boolean timeUp = false;
    public boolean gameLose = false;

    public double playTime = 20.0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Cheese cheese = new OBJ_Cheese();
        cheeseImg = cheese.image;
        OBJ_Steak steak = new OBJ_Steak();
        steakImg = steak.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void gameEnd(String text, Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        int textLength, x, y;

        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);

        gp.gameThread = null; // Stop the game.
    }

    public void draw(Graphics2D g2) {

        if (gameEnd) {

            gameEnd("Congratulation, You win the game!!", g2);
        }
        if (timeUp) {

            gameEnd("Time is up!!", g2);
            playTime = 0.0;
        }
        if(gameLose) {

            gameEnd("You have been caught by cat!!", g2);
        }

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.totalScore, 25, 40);
        g2.drawImage(cheeseImg, gp.tileSize / 2 + 100, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasCheese, 150, 40);
        g2.drawImage(steakImg, gp.tileSize / 2 + 170, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasSteak / 5, 220, 40);


        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);

        // TIME
        playTime -= (double) 1 / 60;
        if (playTime <= 0) {
            timeUp = true;
        }

        // MESSAGE
        if (messageOn) {

            g2.drawString(message, gp.tileSize / 2, gp.tileSize / 2 * 5);

            msgCounter++;
            if (msgCounter > 120) {
                msgCounter = 0;
                messageOn = false;
            }
        }
    }
}
