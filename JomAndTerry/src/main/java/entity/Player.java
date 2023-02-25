package entity;

import main.GamePanel;
import utility.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    // We need GamePanel and KeyHandler
    GamePanel gp;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        this.keyHandler = keyHandler;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            } else {
                direction = "right";
                x += speed;
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

    public void draw(Graphics2D g2) {
        System.out.println("Setting Player");

        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, 48, 48);
    }
}
