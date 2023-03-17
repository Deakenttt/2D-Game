package main;

import object.OBJ_Cheese;
import object.OBJ_Hole;
import object.OBJ_Steak;
import object.OBJ_Trap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage cheeseImg;
    BufferedImage steakImg;
    BufferedImage trapImg;
    BufferedImage doorImg;


    public int commandNum = 0;

    // For showing message.
    public boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;
    public int objectCollectType = 0;

    // For game win or game lose.
    public boolean gameEnd = false;
    public boolean gameLose = false;


    // 0 = title screen, 1 = instructions screen, 2 = Choose map screen
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Cheese cheese = new OBJ_Cheese();
        cheeseImg = cheese.image;
        OBJ_Steak steak = new OBJ_Steak();
        steakImg = steak.image;
        OBJ_Trap trap = new OBJ_Trap();
        trapImg = trap.image;
        OBJ_Hole door = new OBJ_Hole();
        doorImg = door.image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setColor(Color.white);
        if (gp.gameState == GamePanel.titleState) {
            drawTitleScreen();
        }

        // Game state is gamePause.
        if (gp.gameState == GamePanel.gamePause) {

            drawGamePauseScreen(g2);
        }

        // Game state is gamePlay.
        if (gp.gameState == GamePanel.gamePlay) {

            // SCORE AND TIMER
            drawScoreAndTimer(g2);
            drawGamePlayScreen(g2);
        }

        // Game state is gameOverState.
        if (gp.gameState == GamePanel.gameOverState) {

            gameOverScreen();
        }


        // Game state is gameOverState.
        if (gp.gameState == GamePanel.gameWinState) {

            gameWinScreen();
        }

    }

    public void gameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Lose!";

        // shadow layer
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // retry button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //Quit button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
        //Home page
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Home Page";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 40, y);
        }

        //change the level
        text = "Change Level";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - 40, y);
        }
        pauseTimer();
        drawScoreAndTimer(g2);

    }

    public void gameWinScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Win!";

        //shadow layer
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        //retry button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //Quit button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }

        //Home page
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Home Page";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 40, y);
        }
        g2.setFont(g2.getFont().deriveFont(50f));

        //change the level
        text = "Change Level";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - 40, y);
        }
        pauseTimer();
        drawScoreAndTimer(g2);
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            // title screen page
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Jom and Terry";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Mouse picture
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.left1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // menu options
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "Start";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Instructions";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        } else if (titleScreenState == 1) {
            // Instructions page

            g2.setColor(Color.white);

            g2.setFont(g2.getFont().deriveFont(60F));

            String titleText = "Instructions";
            int x = getXforCenteredText(titleText);
            int y = gp.tileSize * 3;
            g2.drawString(titleText, x, y);

            g2.setFont(g2.getFont().deriveFont(24F));
            String instructionText =    "You are the mouse (Terry). Collect 6 cheeses to unlock the exit door. "+
                                        "Avoid the smart cats (Joms; yes they're all named Jom). " +
                                        "Cheese = 1 point, Steak (bonus) = 5 points, mouse trap (punishment) = -5 points. "+
                                        "Negative points = Automatic loss. " +
                                        "Exit with max points and the fastest time, there is no time limit. Enjoy!";
            int maxWidth = 800; // maximum width of text
            String[] lines = getLines(instructionText, g2.getFontMetrics(), maxWidth);

            x = getXforCenteredText(lines[0]); // use the x position of the first line for centering
            y += gp.tileSize * 3;
            for (int i = 0; i < lines.length; i++) {
                g2.drawString(lines[i], x, y);
                y += gp.tileSize; // increase y position for next line
            }

            g2.setFont(g2.getFont().deriveFont(40F));
            String playText = "Play The Game";
            x = getXforCenteredText(playText);
            y += gp.tileSize * 2;
            g2.drawString(playText, x, y);

            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            String backText = "Go Back";
            x = getXforCenteredText(backText);
            y += gp.tileSize;
            g2.drawString(backText, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }else if (titleScreenState == 2) {
            g2.setColor(Color.white);

            g2.setFont(g2.getFont().deriveFont(60F));

            String titleText = "Select the Level";
            int x = getXforCenteredText(titleText);
            int y = gp.tileSize * 3;
            g2.drawString(titleText, x, y);

            g2.setFont(g2.getFont().deriveFont(35F));
            String instructionText =    "Level 1 is easy and for beginners." +
                                        "Level 2 is more difficult and for intermediates." + 
                                        "Choose Wisely! Good Luck! ";
            int maxWidth = 800; // maximum width of text
            String[] lines = getLines(instructionText, g2.getFontMetrics(), maxWidth);

            x = getXforCenteredText(lines[0]); // use the x position of the first line for centering
            y += gp.tileSize * 3;
            for (int i = 0; i < lines.length; i++) {
                g2.drawString(lines[i], x, y);
                y += gp.tileSize; // increase y position for next line
            }

            g2.setFont(g2.getFont().deriveFont(40F));
            String playText = "Level 1";
            x = getXforCenteredText(playText);
            y += gp.tileSize * 2;
            g2.drawString(playText, x, y);

            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(40F));
            playText = "Level 2";
            x = getXforCenteredText(playText);
            y += gp.tileSize;
            g2.drawString(playText, x, y);

            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(40F));
            playText = "Go Back";
            x = getXforCenteredText(playText);
            y += gp.tileSize * 2;
            g2.drawString(playText, x, y);

            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

    }

    private String[] getLines(String text, FontMetrics fontMetrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            int width = fontMetrics.stringWidth(line + " " + word);
            if (width <= maxWidth) {
                line.append(" ").append(word);
            } else {
                lines.add(line.toString().trim());
                line = new StringBuilder(word);
            }
        }
        lines.add(line.toString().trim());
        return lines.toArray(new String[lines.size()]);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void showMessage(String text, int objectType) {
        message = text;
        messageOn = true;
        objectCollectType = objectType;
    }

    public void drawMessage(Graphics2D g2) {
        g2.setFont(arial_40);
        int x = 0;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize * 14);
        int height = gp.tileSize * 2;

        if (messageOn) {

            drawMsgWindow(x, y, width, height);

            g2.setColor(Color.WHITE);
            g2.drawString(message, gp.tileSize / 2, height + 5);

            if (objectCollectType == 1) {
                g2.drawImage(cheeseImg, width - 40, height + 12, gp.tileSize / 2, gp.tileSize / 2, null);
            } else if (objectCollectType == 2) {
                g2.drawImage(steakImg, width - 40, height + 12, gp.tileSize / 2, gp.tileSize / 2, null);
            } else if (objectCollectType == 3) {
                g2.drawImage(trapImg, width - 40, height + 12, gp.tileSize / 2, gp.tileSize / 2, null);
            } else if (objectCollectType == 4) {
                g2.drawImage(doorImg, width - 40, height + 12, gp.tileSize / 2, gp.tileSize / 2, null);
            }


            msgCounter++;
            if (msgCounter > 120) {
                msgCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawMsgWindow(int x, int y, int width, int height) {

        Color c = new Color(54, 46, 57, 255);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 5, 5);

        c = new Color(205, 159, 100);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 5, 5);
    }

    public void resetMsg() {
        msgCounter = 0;
        messageOn = false;
    }

    public void resetGameState() {
        gameLose = false;
        gameEnd = false;
    }

    public double playTime = 0.0; // start at 0 seconds
    private long lastTime = System.currentTimeMillis(); // initialize lastTime to current time
    private boolean paused = false; // initialize paused to false
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public void drawScoreAndTimer(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.totalScore, 25, 40);
        g2.drawImage(cheeseImg, gp.tileSize / 2 + 100, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasCheese, 150, 40);
        g2.drawImage(steakImg, gp.tileSize / 2 + 170, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.hasSteak / 5, 220, 40);

        if (!paused) { // only update playTime if not paused
            long currentTime = System.currentTimeMillis();
            double elapsedSeconds = (currentTime - (double) lastTime) / 1000.0; // convert elapsed time to seconds
            playTime += elapsedSeconds; // update playTime by adding elapsed time
            lastTime = currentTime; // update lastTime to current time
        }

        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);
    }

    public void pauseTimer() {
        paused = true;
    }

    public void resumeTimer() {
        playTime = 0.0;
        paused = false;
    }

    public void drawCenteredMessage(String text, Graphics2D g2) {
        int x, y;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);
    }

    public void drawGamePlayScreen(Graphics2D g2) {

        if (gameEnd) {
            gp.gameState = GamePanel.gameWinState;
            gp.playSE(3);
        }

        if (gameLose) {
            gp.gameState = GamePanel.gameOverState;
            gp.playSE(2);
        }

        drawMessage(g2);
    }


    public void drawGamePauseScreen(Graphics2D g2) {
        drawCenteredMessage("Pause", g2);
        drawScoreAndTimer(g2);
    }


}