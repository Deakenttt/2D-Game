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

    public void setAction() {

    }

    public void update(){
        setAction();
        switch(direction){
            case "up": y -= speed;
            break;

            case "down": y += speed;
            break;

            case "left": x -= speed;
            break;

            case "right": x += speed;
            break;
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
