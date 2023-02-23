package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;

// Blueprint for character classes
public class Entity {
    public int x, y;
    public int speed;
    GamePanel gp;

    // It describes an Image with an accessible buffer of image data.
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // for animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
}
