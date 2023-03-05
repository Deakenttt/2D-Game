package main;

import object.OBJ_Cheese;
import object.OBJ_Steak;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    Graphics2D g2;
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage cheeseImg;
    BufferedImage steakImg;

    // For showing message.
    public boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;

    // For game win or game lose.
    public boolean gameEnd = false;
    public boolean timeUp = false;
    public boolean gameLose = false;

    // For timer and time format.
    public double playTime = 20.0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    // For menu cursor
    public int menuCursor = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Cheese cheese = new OBJ_Cheese();
        cheeseImg = cheese.image;
        OBJ_Steak steak = new OBJ_Steak();
        steakImg = steak.image;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        // Game state is titleState.
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }

        // Game state is gamePause.
        if (gp.gameState == gp.gamePause) {

            drawGamePauseScreen(g2);
        }

        // Game state is gamePlay.
        if (gp.gameState == gp.gamePlay) {

            drawGamePlayScreen(g2);
        }
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void drawMessage(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if (messageOn) {

            g2.drawString(message, gp.tileSize / 2, gp.tileSize / 2 * 5);

            msgCounter++;
            if (msgCounter > 120) {
                msgCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawScoreAndTimer(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.totalScore, 25, 40);
        g2.drawImage(cheeseImg, gp.tileSize / 2 + 100, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasCheese, 150, 40);
        g2.drawImage(steakImg, gp.tileSize / 2 + 170, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasSteak / 5, 220, 40);

        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);
    }

    public int getCenteredTextOfX(String text) {

        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - textLength / 2;
        return x;
    }

    public void drawCenteredMessage(String text, Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        int x, y;

        x = getCenteredTextOfX(text);
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);
    }

    public void gameEnd(String text, Graphics2D g2) {

        drawCenteredMessage(text, g2);
        gp.gameThread = null; // Stop the game.
    }

    public void drawMenu(Graphics2D g2) {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        // 1. PLAY
        String text = "PLAY";
        int x = getCenteredTextOfX(text);
        int y = gp.tileSize * 10;
        g2.drawString(text, x, y);
        if (menuCursor == 0)
            g2.drawString(">", x - gp.tileSize, y);

        // 2. INSTRUCTION
        text = "INSTRUCTION";
        x = getCenteredTextOfX(text);
        y = gp.tileSize * 12;
        g2.drawString(text, x, y);
        if (menuCursor == 1)
            g2.drawString(">", x - gp.tileSize, y);

        // 3. QUIT
        text = "QUIT";
        x = getCenteredTextOfX(text);
        y = gp.tileSize * 14;
        g2.drawString(text, x, y);
        if (menuCursor == 2)
            g2.drawString(">", x - gp.tileSize, y);

    }

    public void drawTitleScreen(Graphics2D g2) {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "JOM AND TERRY";

        int x = getCenteredTextOfX(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // MENU
        drawMenu(g2);

    }

    public void drawGamePlayScreen(Graphics2D g2) {

        // TIME
        playTime -= (double) 1 / 60;

        if (playTime <= 0) {
            timeUp = true;
        }

        if (gameEnd) {

            gameEnd("Congratulation, You win the game!!", g2);
        }
        if (timeUp) {

            playTime = 0.0;
            gameEnd("Time is up!!", g2);
        }
        if (gameLose) {

            gameEnd("You have been caught by cat!!", g2);
        }

        // MESSAGE
        drawMessage(g2);

        // SCORE AND TIMER
        drawScoreAndTimer(g2);
    }

    public void drawGamePauseScreen(Graphics2D g2) {

        drawCenteredMessage("Pause", g2);

        // SCORE AND TIMER
        drawScoreAndTimer(g2);
    }
}
