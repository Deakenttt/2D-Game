package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    // It describes an Image with an accessible buffer of image data.
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // FOR ANIMATION
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // FOR COLLISION
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
