package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class MainTitleUI extends TitleUI{
    protected BufferedImage mouseImg;

    public MainTitleUI(GamePanel gp) {
        super(gp);
        mouseImg = gp.imageLoader.getImage("mouse_left_1");
        titleText = "Jom and Terry";
        subText = "";
        titleScreenState = 0;
    }
    
    public void draw(Graphics2D g2) {
        y = gp.tileSize * 4;
        super.draw(g2);
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y = gp.tileSize * 6 ;
        g2.drawImage(mouseImg, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        y += gp.tileSize*2;
        titleButtons("Start", 0, g2);
        titleButtons("Instructions", 1, g2);
        titleButtons("Quit", 2, g2);
    }

}
