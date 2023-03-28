package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class GameTitleUI extends UI{


   
    public GameTitleUI(GamePanel gp) {
        super(gp);
    }
        
    public void draw(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        g2.setColor(Color.GRAY);
        textWithShadow(titleText, g2, 5);
        // Menu Options
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        longText(subText, g2);
    }
    
    public void longText(String text, Graphics2D g2){
        int maxWidth = 800; // maximum width of text
        String[] lines = getLines(text, g2.getFontMetrics(), maxWidth);
        x = getXforCenteredText(lines[0], g2); // use the x position of the first line for centering
        y += gp.tileSize;
        for (int i = 0; i < lines.length; i++) {
            y += gp.tileSize; // increase y position for next line
            g2.drawString(lines[i], x, y);
        }

    }
    public void drawMouse(Graphics2D g2){
        // Mouse picture
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize;
        g2.drawImage(gp.player.left1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }
}
