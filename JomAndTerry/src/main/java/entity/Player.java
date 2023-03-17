package entity;

import main.GamePanel;
import utility.KeyHandler;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @Des This is a player class extends from Entity.
 */
public class Player extends Entity {
    KeyHandler keyHandler;
    public int hasCheese = 0; // Tracking the number of cheese.
    public int hasSteak = 0; // Tracking the number of steak.

    public int totalScore = 0; // Tracking the total score.
    boolean captureFlag = false; // Flag for being caught.

    int KeyHoldTimer = 0; // Timer for how long the player has hold the key.


    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        solidArea.x = x + solidArea.width;
        solidArea.y = y + solidArea.height;
        this.keyHandler = keyHandler;
        getPlayerImage();

    }

    public void setDefaultValues() {
        x = 0;
        y = 48;
        direction = "down";
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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
            image = utilityTool.scaleImage(image, gp.tileSize - 10, gp.tileSize - 10);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
        if (keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed) {
            KeyHoldTimer++;
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }
        
        } else {
            KeyHoldTimer = -1;
        }

        doMove = KeyHoldTimer % 20 == 0;
    }

    // Player bug: wall collision to the right stops it from going up and then collision to the left stops it from going down
    public void update() {
        if (totalScore >= 6) {
            gp.ui.showMessage("Door is open!", 5);
        }

        // CHECK OBJECT INTERACTION.
        // GET THE INDEX OF OBJECT THAT BEING TOUCH BY PLAYER.
        setAction();
        if(doMove){
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            gp.collisionChecker.checkObject(this);
            gp.collisionChecker.checkEntity(this);
        }
        super.update();

    }


    // METHOD OF PICKING UP OBJECT.
    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name; // Get the type of different objects.

            switch (objectName) {
                case "Cheese" -> {
                    gp.playSE(4);
                    hasCheese++;
                    totalScore++;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You got a cheese!", 1); // Show the msg when touch object.
                    if (hasCheese == 6 && gp.exitcondition) {
                        gp.playSE(1);
                        gp.exitcondition = false;
                    }
                }

                case "Steak" -> {
                    gp.playSE(5);
                    hasSteak += 5;
                    totalScore += 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You got a steak!", 2); // Show the msg when touch object.
                    if (hasCheese == 6 && gp.exitcondition) {
                        gp.playSE(1);
                        gp.exitcondition = false;
                    }
                }

                case "Trap" -> {
                    gp.playSE(7);
                    totalScore -= 5;
                    gp.obj[i] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("Ouch! You touched a trap!", 3); // Show the msg when touch object.
                    if (gp.player.totalScore < 0)
                        gp.ui.gameLose = true;
                }

                case "Hole" -> {
                    if (hasCheese >= 6) {
                        gp.ui.showMessage("You escape successfully!", i); // Show the msg when get the cheese.
                        gp.ui.gameEnd = true; // End the game
                    } else {
                        gp.ui.showMessage("You need collect all the cheese!", 1); // Show the msg when get the cheese.
                    }
                }
            }
        }
    }

    public void captured(int i) {
        if (i != 999 || captureFlag) {
            captureFlag = true;
            gp.ui.gameLose = true; // End the game.
            gp.playSE(6);
        }
    }

    public void retry() {
        setDefaultValues();
        hasCheese = 0; // resets the number of cheese.
        hasSteak = 0; // resets the number of steak.
        totalScore = 0; // resets the total score.
        captureFlag = false;
        gp.ui.resumeTimer();
        gp.ui.resetMsg();
        gp.ui.resetGameState();
        gp.assetSetter.setObject();
        gp.assetSetter.setEnemy();
        gp.gameState = gp.gamePlay;
        for (int i = 0; i < gp.enemy.length; i++) {
            gp.enemy[i].retry();
        }
        for (int i = 0; i < gp.enemy.length; i++) {
            gp.enemy[i].retry();
        }
    }

}
