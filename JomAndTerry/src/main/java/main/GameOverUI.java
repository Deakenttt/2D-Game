package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOverUI extends UI{
    public GameOverUI(GamePanel gp) {
        super(gp);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(grey);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        textWithShadow(text, g2, 5);
        gameStoppedMenuButtons(g2); 
        pauseTimer();
        drawScoreAndTimer(g2);
    }

    public void textWithShadow(String text, Graphics2D g2, int shadowShift){
        // Shadow colour
        g2.setColor(Color.black);
        super.textWithShadow(text, g2, shadowShift);
    }
}
