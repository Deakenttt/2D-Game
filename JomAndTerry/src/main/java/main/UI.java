package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public boolean gamefinished = false;
    public int commandNum = 0;

    // For showing message.
    public boolean messageOn = false;
    public String message = "";
    public int msgCounter = 0;


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

        if (titleScreenState == 0) {
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
        }else if (titleScreenState == 1) {
            // Instructions page

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Instructions";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "You are Terry the mouse, you goal is to collect all 6 Cheeses (1 point each) across the map to unlock the exit door. Jom (smart Cat) and his friends (Dumb cats) are after you. If you are caught by the cats, the game is over. The Steak is a special reward which is worth 5 points and a mouse trap which takes away 5 points. If you get caught by the mouse trap before you have 5 points, you lose. Your goal is to exit the map with as many points as possible as soon as possible (no time limit).";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Play The Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Go Back";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

        }
        


    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}