package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import main.GamePanel;


/**
 * UI class is handling all the UI elements for the game screen, included the score display,
 * message display, title page, game win and lost page...etc.
 */
public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_80B;
    Font gameFont = new Font("Arial", Font.PLAIN, 20);
    Font titleFont = new Font("Arial", Font.BOLD, 96);
    Font textFont = new Font("Arial", Font.PLAIN, 24);
    Font XLFont = new Font("Arial", Font.BOLD, 110);
    Font buttonFont = new Font ("Arial", Font.BOLD, 50);

    protected BufferedImage cheeseImg;
    protected BufferedImage steakImg;
    protected BufferedImage trapImg;
    protected BufferedImage doorImg;
    protected BufferedImage[] images = new BufferedImage[4];

    public BufferedImage currentImage;


    // For the command cursor.
    public int commandNum = 0;

    // For showing message.
    protected boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;
    public int objectCollectType = 0;
    public int y;
    public int x;
    public String text;
    public FontMetrics metrics;
    protected String titleText;
    protected String subText;
    
    Color dBrown = new Color(54, 46, 57, 255);
    Color lBrown = new Color(205, 159, 100);
    Color grey = new Color(0, 0, 0, 150);


    // 0 = title screen, 1 = instructions screen, 2 = Choose map screen
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_80B = new Font("Arial", Font.BOLD, 80);
        cheeseImg = gp.imageLoader.getImage("cheese");
        steak = gp.imageLoader.getImage("steak");
        trap = gp.imageLoader.getImage("trap");
        doorImg = gp.imageLoader.getImage("door");
        images[0] = cheeseImg;
        images[1] = steak;
        images[2] = trap;
        images[3] = doorImg;
    }

    public void draw(Graphics2D g2) {
        drawScoreAndTimer(g2);
        drawMessage(g2);
    }

    /**
     * This is a method for getting the lines and store to the List when it processes a punch of string content.
     * so that when display these string could use these lines to set the different line when displaying
     * a paragraph nicely.
     *
     * @param text        String type context to be process.
     * @param metrics The standard height of a line of text in this font.
     * @param maxWidth    The value for setting one block of text's maximum width.
     * @return String type arraylist that stores all the lines.
     */

    protected String[] getLines(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            int width = metrics.stringWidth(line + " " + word);
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

        

    /**
     * This is a method for getting a center x position of the text.
     *
     * @param text String type context to be process.
     * @return The x position of the text that start from the middle.
     */
    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth-1) / 2 - length / 2;
        return x;
    }

    /**
     * This is a method for triggering the text should be show on or not.
     *
     * @param text       String type context to be process.
     * @param objectType Determine which object icon should be displayed.
     */
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

    /**
     * resetMsg() is a method for resetting the msgCounter to 0 and messageOn to false when user pressed the retry button.
     */
    public void resetMsg() {
        msgCounter = 0;
        messageOn = false;
    }

    public double playTime = 0.0; // start at 0 seconds
    private long lastTime = System.currentTimeMillis(); // initialize lastTime to current time
    private boolean paused = false; // initialize paused to false
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

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

        if (!paused) { // only update playTime if not paused
            long currentTime = System.currentTimeMillis();
            double elapsedSeconds = (currentTime - (double) lastTime) / 1000.0; // convert elapsed time to seconds
            playTime += elapsedSeconds; // update playTime by adding elapsed time
            lastTime = currentTime; // update lastTime to current time
        }

        g2.drawString("Time:  " + decimalFormat.format(playTime), 800, 40);
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
     * This is a method for drawing the UI content when such as message when the game is over or user win the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawGamePlayScreen(Graphics2D g2) {
        drawScoreAndTimer(g2);
        drawMessage(g2);
    }

    /**
     * This is a method for drawing the pause screen when player pause the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void drawGamePauseScreen(Graphics2D g2) {
        drawCenteredMessage("Pause", g2);
        drawScoreAndTimer(g2);
    }

    /**
    * Draws a button with the specified text and number.
    * The button is centered horizontally and positioned below the previously drawn button. 
    * If the button number matches the current command number, a ">" symbol is drawn to the left of the text.
    * @param button_text the text to be displayed on the button
    * @param button_num the number of the button
    * @return void
    */
    
    public void titleButtons(String button_text, int button_num, Graphics2D g2){
        g2.setFont(buttonFont);
        x = getXforCenteredText(button_text, g2);
        y += gp.tileSize * 2;
        g2.drawString(button_text, x, y);
        if (commandNum == button_num) {
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    public void resetUI(){
        resumeTimer();
        resetMsg();
    }

    public void textWithShadow(String text, Graphics2D g2, int shadowShift){
        x = getXforCenteredText(text, g2);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x , y);
    }

    public void gameStoppedMenuButtons(Graphics2D g2){
        // Retry button
        titleButtons("Retry", 0, g2);
        //Quit button
        titleButtons("Quit", 1, g2);
        //Home page
        titleButtons("Home Page", 2, g2);
        //change the level
        titleButtons("Change Level", 3, g2);
    }
}