package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

// Blueprint for character classes
import java.awt.*;

/**
 * @Des This is an Entity class for Player, Cat
 */
public class Entity {

    // ENTITY'S COORDINATE AND SPEED
    public int x, y;
    public int speed;
    GamePanel gp;

    // It describes an Image with an accessible buffer of image data.
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public boolean doMove = true; // only move the entity when doMove is true.

    // FOR ANIMATION
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean onPath = false;


    public Entity(GamePanel gp) {
        this.gp = gp;
        // SOLID AREA FOR COLLISION
        solidArea = new Rectangle(); // (x, y, width, height)
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 22;
        solidArea.height = 22;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        onPath = true;  // Using the A* setAction on SmCat
    }

    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 8;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat
    }

    public void setAction() {
    }

    public void update() {

        collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
        //System.out.println("collisionOn = " + collisionOn);
        if (gp.player.doMove) {
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            gp.assetSetter.steak_update();
            }
            
        }
    }

    // FOR COLLISION
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // SAVING THE DEFAULT VALUE OF X AND Y
    public int solidAreaDefaultX, solidAreaDefaultY;
}
