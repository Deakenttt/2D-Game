package main;

import object.OBJ_Cheese;
import object.OBJ_Steak;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage cheeseImg;
    BufferedImage steakImg;


    public boolean gamefinished = false;
    public int commandNum = 0;

    // For showing message.
    public boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;

    // For game win or game lose.
    public boolean gameEnd = false;
    public boolean timeUp = false;
    public boolean gameLose = false;


    // 0 = title screen, 1 = instructions screen
    public int titleScreenState = 0;

    public UI (GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Cheese cheese = new OBJ_Cheese();
        cheeseImg = cheese.image;
        OBJ_Steak steak = new OBJ_Steak();
        steakImg = steak.image;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setColor(Color.white);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Game state is gamePause.
        if (gp.gameState == gp.gamePause) {

            drawGamePauseScreen(g2);
        }

        // Game state is gamePlay.
        if (gp.gameState == gp.gamePlay) {

            // SCORE AND TIMER
            drawScoreAndTimer(g2);
            drawGamePlayScreen(g2);
        }

        // Game state is gameOverState.
        if (gp.gameState == gp.gameOverState) {

            gameOverScreen();
        }


        // Game state is gameOverState.
        if (gp.gameState == gp.gameWinState) {

            gameWinScreen();
        }

    }


    public void gameOverScreen(){
        g2.setColor (new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont (g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Lose!";

       // shadow layer
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);
         
       // retry button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        //Quit button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
        pauseTimer();
        drawScoreAndTimer(g2);

    }

    public void gameWinScreen(){
        g2.setColor (new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;  
        int y;
        String text;
        g2.setFont (g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Win!";

        //shadow layer
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //retry button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        //Quit button
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
        pauseTimer();
        drawScoreAndTimer(g2);
    }

    public void drawTitleScreen(){
        if (titleScreenState == 0) {
            g2.setColor (new Color (0,0,0));
            g2.fillRect (0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont (g2.getFont () .deriveFont (Font .BOLD, 96F));
            String text = "Jom and Terry";
            int x = getXforCenteredText (text);
            int y = gp.tileSize*3;

            // SHADOW
            g2.setColor (Color.gray);
            g2.drawString (text, x+5, y+5);

            // MAIN COLOR
            g2.setColor (Color.white);
            g2.drawString (text, x, y);

            // Mouse picture
            x = gp.screenWidth/2- (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2. drawImage (gp.player.left1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // menu options
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "Start";
            x = getXforCenteredText (text);
            y += gp.tileSize*4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Instructions";
            x = getXforCenteredText (text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Quit";
            x = getXforCenteredText (text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }else if (titleScreenState == 1) {
            // Instructions page

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Instructions";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "You are Terry the mouse, you goal is to collect all 6 Cheeses (1 point each) across the map to unlock the exit door. Jom (smart Cat) and his friends (Dumb cats) are after you. If you are caught by the cats, the game is over. The Steak is a special reward which is worth 5 points and a mouse trap which takes away 5 points. If you get caught by the mouse trap before you have 5 points, you lose. Your goal is to exit the map with as many points as possible as soon as possible (no time limit).";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Play The Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Go Back";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
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
            double elapsedSeconds = (currentTime - lastTime) / 1000.0; // convert elapsed time to seconds
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
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        int x, y;

        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);
    }

    public void drawGamePlayScreen(Graphics2D g2) {

        // TIME
        playTime -= (double) 1 / 60;

        if (playTime <= 0) {
            timeUp = true;
        }
        if (gameEnd) {

            // gp.gameThread = null;
            gp.gameState = gp.gameWinState;
        }
        if (timeUp) {
            playTime = 0.0;

        }
        if (gameLose) {

            // gp.gameThread = null;
            gp.gameState = gp.gameOverState;
        }

        // MESSAGE
        drawMessage(g2);

    }


    public void drawGamePauseScreen(Graphics2D g2) {

        drawCenteredMessage("Pause", g2);

        // SCORE AND TIMER
        drawScoreAndTimer(g2);
    }


}