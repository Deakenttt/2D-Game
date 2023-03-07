package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public boolean gamefinished = false;
    public int commandNum = 0;

    // 0 = title screen, 1 = instructions screen
    public int titleScreenState = 0;

    public UI (GamePanel gp) {
        this.gp = gp;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setColor(Color.white);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
    }

    public void drawTitleScreen(){
        g2.setColor (new Color (0,0,0));
        g2.fillRect (0, 0, gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont (g2.getFont () .deriveFont (Font .BOLD, 96F));
        String text = "Jom and Terry";
        int x = getXforCenteredText (text);
        int y = gp.tileSize*3;

        // SHADOW
        g2.setColor (Color.gray);
        g2.drawString (text, x+5, y+5);

        // MAIN COLOR
        g2.setColor (Color.white);
        g2.drawString (text, x, y);

        // Mouse picture
        x = gp.screenWidth/2- (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2. drawImage (gp.player.left1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // menu options
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "Start";
        x = getXforCenteredText (text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Instructions";
        x = getXforCenteredText (text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Quit";
        x = getXforCenteredText (text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }


    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}