package ui;

import main.GamePanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class GamePlayUI extends GameUI {
    private long currentTime;
    private int height;
    private int msgLength;
    private int msgX;
    private int msgY;
    private Map<String, Integer> msgWindow = new HashMap<String, Integer>();
    private Map<String, Integer> imgSettings = new HashMap<String, Integer>(); // x, y, length, height

    protected boolean msgOn = false;
    public GamePlayUI(GamePanel gp) {
        super(gp);
        x = 0;
        y = gp.tileSize;
        height = gp.tileSize * 2;
        msgX = y/2;
        msgY = height+5;

        imgSettings.put("length", gp.tileSize);
        imgSettings.put("width", gp.tileSize);
        imgSettings.put("x", 0);
        imgSettings.put("y", 0);

        msgWindow.put("outerWidth", 0);
        msgWindow.put("outerLength", height);
        msgWindow.put("innerWidth", 0);
        msgWindow.put("innerLength", height-10);
        msgWindow.put("innerX", x+5);
        msgWindow.put("innerY", y+5);

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

    public void setMessage(String text, int objectType) {
        message = text;
        msgCounter = 0;
        currentImage = images[objectType];
        msgLength = message.length()*12;
        msgWindow.replace("outerWidth", msgLength + gp.tileSize*2);
        msgWindow.replace("innerWidth", msgWindow.get("outerWidth")-10);

        imgSettings.replace("x", msgLength+gp.tileSize/2);
        imgSettings.replace("y", height-20); 
        
        msgOn = true;
    }

    /**
     * This is s method for drawing the message context when player touch any type of objects.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    protected void drawMessage(Graphics2D g2) {
        x = 0;
        y = gp.tileSize;
        drawMsgWindow(msgWindow, g2);
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);
        g2.drawString(message, msgX, msgY);
        g2.drawImage(currentImage, imgSettings.get("x"), imgSettings.get("y"), imgSettings.get("length"), imgSettings.get("width"), null);

        // Only show the message in a short period of the time.
        msgCounter++;
        if (msgCounter > 120) {
            resetMsg();
        }
    }

        /**
     * This is a method for drawing a message window by using the x, y position and value of width, height.
     *
     * @param x      window's x position.
     * @param y      window's y position.
     * @param width  window's width value.
     * @param height window's height value.
     */
    private void drawMsgWindow(Map<String, Integer> msgWindow, Graphics2D g2) {
        g2.setColor(dBrown);
        g2.fillRoundRect(x, y, msgWindow.get("outerWidth"), msgWindow.get("outerLength"), 5, 5);
        g2.setColor(lBrown);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(msgWindow.get("innerX"), msgWindow.get("innerY"), msgWindow.get("innerWidth"), msgWindow.get("innerLength"), 5, 5);
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

    /**
     * This is a method for resume the timer.
     */
    public void resetTimer() {
        playTime = 0.0;
        paused = false;
}
}
