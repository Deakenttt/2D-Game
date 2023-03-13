package entity;

import main.GamePanel;
import utility.KeyHandler;
import utility.UtilityTool;

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
    public int hasCheese = 0; // Tracking the number of cheese.
    public int hasSteak = 0; // Tracking the number of steak.
    public int totalScore = 0; // Tracking the total score.
    boolean captureFlag = false; // Flag for being caught

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        this.keyHandler = keyHandler;
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 96;
        y = 96;
        speed = 15;
        direction = "down";
        solidArea.width = 22;
        solidArea.height = 22;
    }

    public void getPlayerImage() {

        up1 = setup("mouse_up_1");
        up2 = setup("mouse_up_2");

        down1 = setup("mouse_down_1");
        down2 = setup("mouse_down_2");

        left1 = setup("mouse_left_1");
        left2 = setup("mouse_left_2");

        right1 = setup("mouse_right_1");
        right2 = setup("mouse_right_2");
    }

    // METHOD OF SETTING SCALED IMAGE FOR PLAYER
    public BufferedImage setup(String imageName) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/mouse/" + imageName + ".png")));
            image = utilityTool.scaleImage(image, gp.tileSize-10, gp.tileSize-10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
        doMove = (keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed);
        if (doMove) {
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
        System.out.println("the Pos is: " + x + ", " + y);
    }

    // Player bug: wall collision to the right stops it from going up and then collision to the left stops it from going down
    public void update() {
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
                    gp.ui.showMessage("You got a cheese!"); // Show the msg when touch object.
                }

                case "Steak" -> {
                    hasSteak += 5;
                    totalScore += 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You got a steak!"); // Show the msg when touch object.
                }

                case "Trap" -> {
                    totalScore -= 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You touched a trap!"); // Show the msg when touch object.
                }

                case "Hole" -> {
                    if (hasCheese == 6) {
                        gp.obj[i] = null;
                        gp.ui.showMessage("You escape successfully!"); // Show the msg when get the cheese.
                        gp.ui.gameEnd = true; // End the game
                    } else {
                        gp.ui.showMessage("You need collect all the cheese!"); // Show the msg when get the cheese.
                    }
                } 
            }
        }
    }

    public void captured(int i) {
        if (i != 999 || captureFlag) {
            captureFlag = true;
            gp.ui.gameLose = true; // End the game.
        }
    } 

    public void retry(){
        setDefaultValues();
        hasCheese = 0; // resets the number of cheese.
        hasSteak = 0; // resets the number of steak.
        totalScore = 0; // resets the total score.
        captureFlag = false;
        gp.ui.gameLose = false;
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

        g2.drawImage(image, x, y, null);
    }
}
