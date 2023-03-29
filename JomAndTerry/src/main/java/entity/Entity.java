package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

// Blueprint for character classes
import java.awt.*;

/**
* Entity class representing the Player and Cat entities in the game.
* It contains information about entity's coordinates, speed, direction, and animations.
* It also has methods for updating the entity's position and drawing the entity on the game screen.
*/
public class Entity {
    public int x, y;
    public int speed;
    GamePanel gp;
    String name;

    // It describes an Image with an accessible buffer of image data.
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public boolean doMove = true; // only move the entity when doMove is true.

    // For animation
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean onPath = false;

    // For collision
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // For saving the default x and y values
    public int solidAreaDefaultX, solidAreaDefaultY;

    /**
    * Constructs an Entity object with default values.
    *
    * @param gp The game panel of the game.
    */
    public Entity(GamePanel gp) {
        this.gp = gp;
        // SOLID AREA FOR COLLISION
        solidArea = new Rectangle(); // (x, y, width, height)
        solidArea.width = 22;
        solidArea.height = 22;
        speed = 48;
        setDefaultValues();
        getPlayerImage();

    }
    /**
     * Sets the default values of an entity.
     */
    public void setDefaultValues() {
        x = 48;
        y = 48;
        onPath = true;  // Using the A* setAction on SmCat
    }
    /**
    * Sets the image of an entity.
    */
    public void getPlayerImage() {
        up1 =  gp.imageLoader.getImage("%s_up_1".formatted(name));
        up2 =  gp.imageLoader.getImage("%s_up_2".formatted(name));
        down1 = gp.imageLoader.getImage("%s_down_1".formatted(name));
        down2 =  gp.imageLoader.getImage("%s_down_2".formatted(name));
        left1 =  gp.imageLoader.getImage("%s_left_1".formatted(name));
        left2 =  gp.imageLoader.getImage("%s_left_2".formatted(name));
        right1 =  gp.imageLoader.getImage("%s_right_1".formatted(name));
        right2 =  gp.imageLoader.getImage("%s_right_2".formatted(name));
    }

    /**
    * Sets the action of the entity.
    */
    public void setAction() {
    }
    
    /**
    * Updates the position and sprite of the entity.
    */
    public void update() {
        if (gp.player.doMove && !collisionOn) {
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
            solidArea.x = x + solidArea.width;
            solidArea.y = y + solidArea.height;
        }
        spriteCounter++;
        if (spriteCounter > 5) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }
    }            
    /**
    * Draws the sprite on the graphics context.
    * @param g2 the graphics context to draw on
    */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, x, y, null);
    }
}

