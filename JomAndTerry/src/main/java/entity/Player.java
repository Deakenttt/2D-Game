package entity;

import main.GamePanel;
import utility.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @Des This is a player class extends from Entity.
 */
public class Player extends Entity {
    // We need GamePanel and KeyHandler
    // GamePanel gp;
    KeyHandler keyHandler;
    int hasCheese = 0; // Tracking the number of cheese.
    int hasSteak = 0; // Tracking the number of steak.
    int totalScore = 0; // Tracking the total score.
    boolean captureFlag = false;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        this.keyHandler = keyHandler;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            // loaded images.
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_up_2.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_down_2.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_left_2.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/mouse_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {

        if (keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

        }
        }

        public void update(){
            // CHECK OBJECT INTERACTION.
            // GET THE INDEX OF OBJECT THAT BEING TOUCH BY PLAYER.
            // int objIndex = gp.collisionChecker.checkObject(this, true);
            gp.collisionChecker.checkObject(this, true);
            gp.collisionChecker.checkEntity(this);

            // pickUpObject(objIndex); // Calls pickUpObject method.
            System.out.println("Cheese = " + hasCheese + " Steak = " + hasSteak + " total Score = " + totalScore);
            super.update();
        }


    // METHOD OF PICKING UP OBJECT.
    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name; // Get the type of different objects.

            switch (objectName) {
                case "Cheese" -> {
                    hasCheese++;
                    totalScore++;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                }

                case "Steak" -> {
                    hasSteak += 5;
                    totalScore += 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                }

                case "Trap" -> {
                    totalScore -= 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                }

                case "Hole" -> {
                    if (hasCheese >= 2) {
                        gp.obj[i] = null;

                    }
                }
            }
        }
    }

    public void captured(int i) {
        if (i != 999 || captureFlag) {
            // unsure of what it should do
            System.out.println("PLAYER HAS BEEN CAUGHT!!!!!!");
            captureFlag = true;
        }
    }


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

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
