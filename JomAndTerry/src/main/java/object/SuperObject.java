package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int x, y;

    public Rectangle solidArea = new Rectangle(x, y, 48, 48);
    public int solidAreaDefaultX = solidArea.x, solidAreaDefaultY = solidArea.y;

    // DRAW METHOD
    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawRect(x, y, 48, 48);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
