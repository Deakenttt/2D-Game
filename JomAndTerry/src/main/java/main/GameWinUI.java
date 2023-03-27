package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameWinUI extends UI {

    public GameWinUI(GamePanel gp) {
        super(gp);
        //TODO Auto-generated constructor stub
    }
    public void draw(Graphics2D g2) {
        g2.setColor(grey);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "You Win!";
        // Shadow layer
        g2.setColor(Color.black);
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
        // Retry button
        g2.setFont(g2.getFont().deriveFont(50f));
        titleButtons("Retry", 0, g2);
        //Quit button
        titleButtons("Quit", 1, g2);
        //Home page
        titleButtons("Home Page", 2, g2);
        //change the level
        titleButtons("Change Level", 3, g2);
        pauseTimer();
        drawScoreAndTimer(g2);
    }
    
}
