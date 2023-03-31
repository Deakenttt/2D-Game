package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class GameOverUI extends GameUI{

    public GameOverUI(GamePanel gp) {
        super(gp);
    }

    public void draw(Graphics2D g2) {
        y = gp.tileSize * 4;
        g2.setColor(grey);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        g2.setColor(Color.black);
        textWithShadow(titleText, g2);

        // Retry button
        titleButtons("Retry", 0, g2);
        //Quit button
        titleButtons("Quit", 1, g2);
        //Home page
        titleButtons("Home Page", 2, g2);
        //change the level
        titleButtons("Change Level", 3, g2);        
        paused = true;
        super.draw(g2);
    }
}
