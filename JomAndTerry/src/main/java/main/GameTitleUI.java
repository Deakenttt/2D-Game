package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameTitleUI extends UI{

    public GameTitleUI(GamePanel gp) {
        super(gp);
        //TODO Auto-generated constructor stub
    }
     /**
  * drawTitleScreen() is a method for handling all the visual elements on the page showing when
  * the user start the game, it has buttons for user to select, such as 'start', 'Instruction', 'quit'
  * when pressed the 'start' button, it will show another page for user to select the different difficulties.
  * when pressed the 'instruction' button, it will show another page that showing all instuction about the game.
  */
 public void draw(Graphics2D g2) {
    if (titleScreenState == 0) {
        // Title screen page
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Jom and Terry";
        textWithShadow(text, g2, 5);

        // Mouse picture
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.left1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        // Menu options
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        y += gp.tileSize * 2;
        
        titleButtons("Start", 0, g2);
        titleButtons("Instructions", 1, g2);
        titleButtons("Quit", 2, g2);

    } else if (titleScreenState == 1) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String titleText = "Instructions";
        textWithShadow(titleText, g2, 5);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        String instructionText = "You are the mouse (Terry). Collect 6 cheeses to unlock the exit door. " +
                "Avoid the smart cats (Joms; yes they're all named Jom). " +
                "Cheese = 1 point, Steak (bonus) = 5 points, mouse trap (punishment) = -5 points. " +
                "Negative points = Automatic loss. " +
                "Exit with max points and the fastest time, there is no time limit. Enjoy!";
        longText(instructionText, g2);
        g2.setFont(g2.getFont().deriveFont(40F));
        titleButtons("Play The Game", 0, g2);
        titleButtons("Go Back", 1, g2);

    }else if (titleScreenState == 2) {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(60F));
        String titleText = "Select the Level";
        x = getXforCenteredText(titleText, g2);
        y = gp.tileSize * 3;
        g2.drawString(titleText, x, y);
        g2.setFont(g2.getFont().deriveFont(35F));
        String instructionText = "Level 1 is easy and for beginners." +
                "Level 2 is more difficult and for intermediates." +
                "Choose Wisely! Good Luck! ";
        longText(instructionText, g2);

        g2.setFont(g2.getFont().deriveFont(40F));
        titleButtons("Level 1", 0, g2);
        y = y - gp.tileSize;
        titleButtons("Level 2", 1, g2);
        titleButtons("Go Back", 2, g2);
    }
    }
    public void textWithShadow(String text, Graphics2D g2, int shadowShift){
        // Shadow colour
        g2.setColor(Color.gray);
        y = gp.tileSize * 3;
        super.textWithShadow(text, g2, shadowShift);
    }
    
    public void longText(String text, Graphics2D g2){
        int maxWidth = 800; // maximum width of text
        String[] lines = getLines(text, g2.getFontMetrics(), maxWidth);
        x = getXforCenteredText(lines[0], g2); // use the x position of the first line for centering
        y += gp.tileSize * 3;
        for (int i = 0; i < lines.length; i++) {
            g2.drawString(lines[i], x, y);
            y += gp.tileSize; // increase y position for next line
        }

    }
}
