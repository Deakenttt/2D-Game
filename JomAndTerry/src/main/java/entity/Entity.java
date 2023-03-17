package entity;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.util.Objects;
import java.io.IOException;

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
    String name;

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

    public void setDefaultValues() {
        x = 48;
        y = 48;
        onPath = true;  // Using the A* setAction on SmCat

    }
    public void getPlayerImage() {
        up1 = setup("%s_up_1".formatted(name));
        up2 = setup("%s_up_2".formatted(name));
        
        down1 = setup("%s_down_1".formatted(name));
        down2 = setup("%s_down_2".formatted(name));
        
        left1 = setup("%s_left_1".formatted(name));
        left2 = setup("%s_left_2".formatted(name));

        right1 = setup("%s_right_1".formatted(name));
        right2 = setup("%s_right_2".formatted(name));

    }

    public BufferedImage setup(String imageName) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/%s/%s.png".formatted(name, imageName))));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
    }
    

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

    // FOR COLLISION
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // SAVING THE DEFAULT VALUE OF X AND Y
    public int solidAreaDefaultX, solidAreaDefaultY;

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

