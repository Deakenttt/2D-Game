package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Des This is s object class for all the subclasses.
 */
public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int x, y;

    public Rectangle solidArea = new Rectangle(x, y, 48, 48);
    public int solidAreaDefaultX = solidArea.x, solidAreaDefaultY = solidArea.y;

    /**
     * This is a draw method for drawing the object.
     *
     * @param g2 Graphics2D for drawing the 2d pixels.
     * @param gp GamePanel, provides space in which an application can attach any other component, including other panels.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
