package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import main.GamePanel;

public class GameUI extends UI{
    protected boolean messageOn = false;
    protected String message = "";
    protected int msgCounter = 0;

    protected double playTime = 0.0; // start at 0 seconds
    protected long lastTime = System.currentTimeMillis(); // initialize lastTime to current time
    protected boolean paused = false; // initialize paused to false
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");


    public GameUI(GamePanel gp) {
        super(gp);
    }

    /**
     * This is a method for drawing the player score information and the timer information during the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawScoreAndTimer(Graphics2D g2) {
        g2.setFont(gameFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.scoreCount, 25, 40);
        g2.drawImage(cheeseImg, gp.tileSize / 2 + 100, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.cheeseCount, 150, 40);
        g2.drawImage(steakImg, gp.tileSize / 2 + 170, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.steakCount / 5, 220, 40);
        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);
    }

    /**
     * This is a method for drawing the message and displayed in the central position.
     *
     * @param text The message content that need to be displayed
     * @param g2   Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawCenteredMessage(String text, Graphics2D g2) {
        int x, y;
        g2.setFont(gameFont);
        g2.setColor(Color.WHITE);

        x = getXforCenteredText(text, g2);
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);
    }

        /**
     * This a method for pause the timer.
     */
    public void pauseTimer() {
        paused = true;
    }

    /**
     * This is a method for resume the timer.
     */
    public void resumeTimer() {
        playTime = 0.0;
        paused = false;
    }

    public void resetUI(){
        resumeTimer();
        resetMsg();
    }

    /**
     * resetMsg() is a method for resetting the msgCounter to 0 and messageOn to false when user pressed the retry button.
     */
    public void resetMsg() {
        msgCounter = 0;
        messageOn = false;
    }
}
