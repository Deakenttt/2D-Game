package ui;

import main.GamePanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GamePlayUI extends GameUI {
    private long currentTime;
    private int height = gp.tileSize * 2;
    protected int msgLength;
    protected int msgWindowLength;
    protected boolean msgOn = false;
    public GamePlayUI(GamePanel gp) {
        super(gp);
    }

    /**
     * This is a method for drawing the UI content when such as message when the game is over or user win the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (msgOn){
            drawMessage(g2);
        }
    }

    public void showMessage(String text, int objectType) {
        message = text;
        msgCounter = 0;
        currentImage = images[objectType];
        msgLength = message.length()*10;
        msgWindowLength = msgLength + gp.tileSize*2;
        msgOn = true;
    }

    /**
     * This is s method for drawing the message context when player touch any type of objects.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawMessage(Graphics2D g2) {
        x = 0;
        y = gp.tileSize;
        drawMsgWindow(x, y, msgWindowLength, height, g2);
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
        g2.drawString(message, gp.tileSize / 2, height + 5);
        g2.drawImage(currentImage, msgLength+gp.tileSize, height-20 , gp.tileSize, gp.tileSize, null);

        // Only show the message in a short period of the time.
        msgCounter++;
        if (msgCounter > 120) {
            resetMsg();
        }
        // }
    }

        /**
     * This is a method for drawing a message window by using the x, y position and value of width, height.
     *
     * @param x      window's x position.
     * @param y      window's y position.
     * @param width  window's width value.
     * @param height window's height value.
     */
    public void drawMsgWindow(int x, int y, double width, int height, Graphics2D g2) {
        g2.setColor(dBrown);
        g2.fillRoundRect(x, y, (int) width, height, 5, 5);
        g2.setColor(lBrown);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, (int) width - 10, height - 10, 5, 5);
    }

    public void drawScoreAndTimer(Graphics2D g2) {
        currentTime = System.currentTimeMillis();
        playTime += (currentTime - (double) lastTime) / 1000.0; // convert elapsed time to seconds
        lastTime = currentTime; // update lastTime to current time
        super.drawScoreAndTimer(g2);
    }

    public void resetUI(){
        resetTimer();
        resetMsg();
    }

    /**
     * resetMsg() is a method for resetting the msgCounter to 0 and messageOn to false when user pressed the retry button.
     */
    public void resetMsg() {
        msgCounter = 0;
        message = "";
        currentImage = null;
        msgOn = false;
    }

}
