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
    public int points = 0;
    public boolean randomPosition = true;

    public Rectangle solidArea = new Rectangle(x, y, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    // public GamePanel gp;
    public SuperObject(GamePanel gp){
        // this.gp = gp;
        setDefaultValues();
        image = gp.imageLoader.getImage(name);
        
    }

    public void setDefaultValues(){
        solidArea =  new Rectangle(x, y, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
    /**
     * This is a draw method for drawing the object.
     *
     * @param g2 Graphics2D for drawing the 2d pixels.
     * @param gp GamePanel, provides space in which an application can attach any other component, including other panels.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, solidArea.x, solidArea.y, gp.tileSize, gp.tileSize, null);
    }

}

