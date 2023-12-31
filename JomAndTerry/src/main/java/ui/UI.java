package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GamePanel;


/**
 * UI class is handling all the UI elements for the game screen, included the score display,
 * message display, title page, game win and lost page...etc.
 */
public class UI {
    public GamePanel gp;
    protected BufferedImage currentImage;

    // For the command cursor.
    public int commandNum = 0;

    // For showing messages
    protected String titleText;
    protected String subText;

    public String message = "";
    public boolean messageOn = false;
    public int objectCollectType = 0;


    // Text positions
    protected int y;
    protected int x;

    // Font settings
    protected Font titleFont = new Font("Arial", Font.BOLD, 96);
    protected Font textFont = new Font("Arial", Font.PLAIN, 24);
    protected Font XLFont = new Font("Arial", Font.BOLD, 110);
    protected Font buttonFont = new Font("Arial", Font.BOLD, 50);
    protected Color dBrown = new Color(54, 46, 57, 255);
    protected Color lBrown = new Color(205, 159, 100);
    protected Color grey = new Color(0, 0, 0, 150);

    // 0 = title screen, 1 = instructions screen, 2 = Choose map screen
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public void setTitleText(String text) {
        titleText = text;
    }


    public void resetUI() {
    }

    /**
     * This is a method for triggering the text should be show on or not.
     *
     * @param text       String type context to be process.
     * @param pickupObjectName Determine which object icon should be displayed.
     */
    public void setMessage(String text, String pickupObjectName) {
    }

    /* Methods for drawing */

    public void draw(Graphics2D g2) {
    }

    /* Methods related to formatting */

    /**
     * This is a method for getting a center x position of the text.
     *
     * @param text String type context to be process.
     * @return The x position of the text that start from the middle.
     */
    protected int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth - 1) / 2 - length / 2;
        return x;
    }

    /**
     * This is a method for getting the lines and store to the List when it processes a punch of string content.
     * so that when display these string could use these lines to set the different line when displaying
     * a paragraph nicely.
     *
     * @param text     String type context to be process.
     * @param metrics  The standard height of a line of text in this font.
     * @param maxWidth The value for setting one block of text's maximum width.
     * @return String type arraylist that stores all the lines.
     */
    protected String[] getLines(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        try {
            for (String word : words) {
                int width = metrics.stringWidth(line + " " + word);
                if (width <= maxWidth) {
                    line.append(" ").append(word);
                } else {
                    lines.add(line.toString().trim());
                    line = new StringBuilder(word);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: getLines method in UI recieved a null pointer from FontMetrics metrics, manually print 8 words per line\n" + e);
            int counter = 0;
            for (String word : words) {
                if (counter < 8) {
                    line.append(" ").append(word);
                    counter++;
                } else {
                    lines.add(line.toString().trim());
                    line = new StringBuilder(word);
                    counter = 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        lines.add(line.toString().trim());
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Draws a button with the specified text and number.
     * The button is centered horizontally and positioned below the previously drawn button.
     * If the button number matches the current command number, a ">" symbol is drawn to the left of the text.
     *
     * @param button_text the text to be displayed on the button
     * @param button_num  the number of the button
     */
    protected void titleButtons(String button_text, int button_num, Graphics2D g2) {
        g2.setFont(buttonFont);
        x = getXforCenteredText(button_text, g2);
        y += gp.tileSize * 2;
        g2.drawString(button_text, x, y);
        if (commandNum == button_num) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    protected void textWithShadow(String text, Graphics2D g2) {
        x = getXforCenteredText(text, g2);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    public void setTimer(double time) {
    }

    public double getTimer() {
        return 0.0;
    }

}