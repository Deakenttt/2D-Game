package main;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    public boolean gamefinished = false;
    public int commandNum = 0;

    public UI (GamePanel gp) {
        this.gp = gp;

         try {
             InputStream is = getClass().getResourceAsStream ("/font/x12y16pxMaruMonica.ttf");
             maruMonica = Font.createFont (Font. TRUETYPE_FONT, is);
             is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
             purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
         } catch (FontFormatException e) {
             e.printStackTrace ();
         }
           catch (IOException e) {
             e.printStackTrace();
           }
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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