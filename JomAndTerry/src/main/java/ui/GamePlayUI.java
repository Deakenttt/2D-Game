package ui;

import main.GamePanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GamePlayUI extends GameUI {

    public GamePlayUI(GamePanel gp) {
        super(gp);
    }

    /**
     * This is a method for drawing the UI content when such as message when the game is over or user win the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void draw(Graphics2D g2) {
        drawScoreAndTimer(g2);
        drawMessage(g2);
    }

    public void showMessage(String text, int objectType) {
        message = text;
        messageOn = true;
        currentImage = images[objectType];
    }

    /**
     * This is s method for drawing the message context when player touch any type of objects.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawMessage(Graphics2D g2) {
        g2.setFont(gameFont);
        int x = 0;
        int y = gp.tileSize;
        int height = gp.tileSize * 2;

        if (messageOn) {
            int length = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();

            drawMsgWindow(x, y, length+50, height, g2);

            g2.setColor(Color.WHITE);
            g2.drawString(message, gp.tileSize / 2, height + 5);
            g2.drawImage(currentImage, x+length, height + 12, gp.tileSize / 2, gp.tileSize / 2, null);

            // Only show the message in a short period of the time.
            msgCounter++;
            if (msgCounter > 120) {
                msgCounter = 0;
                messageOn = false;
            }
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
    public void drawMsgWindow(int x, int y, int width, int height, Graphics2D g2) {
        g2.setColor(dBrown);
        g2.fillRoundRect(x, y, width, height, 5, 5);

        g2.setColor(lBrown);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 5, 5);
    }

    public void drawScoreAndTimer(Graphics2D g2) {
        if (!paused) { // only update playTime if not paused
            long currentTime = System.currentTimeMillis();
            double elapsedSeconds = (currentTime - (double) lastTime) / 1000.0; // convert elapsed time to seconds
            playTime += elapsedSeconds; // update playTime by adding elapsed time
            lastTime = currentTime; // update lastTime to current time
        }
        super.drawScoreAndTimer(g2);
    }


}
