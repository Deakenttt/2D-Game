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


    public Entity(GamePanel gp) {
        this.gp = gp;
        // SOLID AREA FOR COLLISION
        solidArea = new Rectangle(8, 16, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();

    }

    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 4;
        direction = "down";
    }

    public void setAction() {

    }

    public void update() {

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method 
        setAction();
        if (doMove) {
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
            if (spriteCounter > 10) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    // FOR COLLISION
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // SAVING THE DEFAULT VALUE OF X AND Y
    public int solidAreaDefaultX, solidAreaDefaultY;
}
