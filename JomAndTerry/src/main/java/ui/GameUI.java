package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    protected BufferedImage[] images = new BufferedImage[4];


    public GameUI(GamePanel gp) {
        super(gp);
        images[0] = gp.imageLoader.getImage("cheese");
        images[1] = gp.imageLoader.getImage("steak");
    }

    public void draw(Graphics2D g2) {
        drawScoreAndTimer(g2);
    }
    /**
     * This is a method for drawing the player score information and the timer information during the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawScoreAndTimer(Graphics2D g2) {
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.scoreCount, 25, 40);
        g2.drawImage(images[0], gp.tileSize / 2 + 100, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.cheeseCount, 150, 40);
        g2.drawImage(images[1], gp.tileSize / 2 + 170, 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString(" X " + gp.player.steakCount / 5, 220, 40);
        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);
    }

    public void setTimer(double time){
        playTime = time;
    }

    public double getTimer(){
        return (double) playTime;
    }
}
