package ui;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class TitleUI extends UI{
   
    public TitleUI(GamePanel gp) {
        super(gp);
    }
        
    public void draw(Graphics2D g2) {
        g2.setFont(titleFont);
        g2.setColor(Color.GRAY);
        textWithShadow(titleText, g2, 5);
        longText(subText, g2);
    }
    
    public void longText(String text, Graphics2D g2){
        g2.setFont(textFont);
        String[] lines = getLines(text, g2.getFontMetrics(), 800);
        x = getXforCenteredText(lines[0], g2); // use the x position of the first line for centering
        y += gp.tileSize;
        for (int i = 0; i < lines.length; i++) {
            y += gp.tileSize; // increase y position for next line
            g2.drawString(lines[i], x, y);
        }
    }
}
