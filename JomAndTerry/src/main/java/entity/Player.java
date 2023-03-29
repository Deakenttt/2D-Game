package entity;

import main.GamePanel;
import utility.KeyHandler;

/**
 * The Player class represents a player entity that extends from the Entity class.
 * It keeps track of the player's score, the number of cheese and steak, and whether the player is captured.
 */
public class Player extends Entity {
    KeyHandler keyHandler;
    public int cheeseCount = 0; // Tracking the number of cheese.
    public int steakCount = 0; // Tracking the number of steak.
    public int scoreCount = 0; // Tracking the total score.
    boolean captureFlag = false; // Flag for being caught.
    int KeyHoldTimer = 0; // Timer for how long the player has hold the key.

    /**
     * Constructs a Player object.
     *
     * @param gp         The GamePanel object.
     * @param keyHandler The KeyHandler object.
     */
    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        this.keyHandler = keyHandler;
        solidAreaDefaultX = x + solidArea.width;
        solidAreaDefaultY = y + solidArea.height;

    }

    /**
     * Sets the default values for the player.
     */
    public void setDefaultValues() {
        x = 0;
        y = 48;
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;
        direction = "down";
        cheeseCount = 0; // resets the number of cheese.
        steakCount = 0; // resets the number of steak.
        scoreCount = 0; // resets the total score.
        captureFlag = false;
        name = "mouse";
    }

    /**
     * Sets the player's action based on the key handler.
     */
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
        } else
            KeyHoldTimer = -1;

        doMove = KeyHoldTimer % 20 == 0;
    }

    /**
     * Updates the player's position and checks for object interactions.
     */
    public void update() {
        handleGameState();
        setAction();
        if (doMove) {
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            gp.collisionChecker.checkEntity(this);
            gp.assetSetter.steakUpdate();
        }
        super.update();
        pickUpObject(gp.collisionChecker.checkObject(solidArea));
    }


    /**
     * Picks up an object and updates the player's score and inventory.
     *
     * @param object The index of the object being picked up.
     */
    public void pickUpObject(int object) {
        if (object != 999) {
            String objectName = gp.obj[object].name; // Get the type of different objects.
            switch (objectName) {
                case "cheese":
                    pickupObjectEffect(objectName, "You got a cheese!", object, 0, 4);
                    break;

                case "steak":
                    pickupObjectEffect(objectName, "You got a steak!", object, 1, 5);
                    break;

                case "trap":
                    pickupObjectEffect(objectName, "Ouch! You touched a trap!", object, 2, 7);
                    break;

                case "hole":
                    handleExit();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * This method is used to handle the player capture event.
     *
     * @param cat The index of the entity that has captured the player.
     */
    public void captured(int cat) {
        if (cat != 999 || captureFlag) {
            captureFlag = true;
            gp.gameOver();
            gp.playSE(6);
        }
    }

    public void pickupObjectEffect(String pickupObjectName, String pickUpObjectMsg, int pickupObjectIndex, int pickupObjectType, int pickupObjectSound) {
        handleScore(pickupObjectName);
        gp.playSE(pickupObjectSound);
        gp.obj[pickupObjectIndex] = null;
        gp.currentUI.showMessage(pickUpObjectMsg, pickupObjectType); // Show the msg when touch object.
        System.out.println("score: " + scoreCount);
    }

    public void handleScore(String pickupObjectName) {
        if (pickupObjectName == "cheese") {
            cheeseCount++;
            scoreCount++;
        } else if (pickupObjectName == "steak") {
            steakCount += 5;
            scoreCount += 5;
        } else if (pickupObjectName == "trap") {
            scoreCount -= 5;
        }
    }

    public void handleGameState() {
        if (cheeseCount >= 6) {
            gp.currentUI.showMessage("Door is open!", 3);
        }
        if (cheeseCount == 6 && gp.exitcondition) {
            gp.playSE(1);
            gp.exitcondition = false;
        }
        if (gp.player.scoreCount < 0)
            gp.gameOver();
    }

    public void handleExit() {
        if (cheeseCount >= 6) {
            gp.gameWin();
            gp.currentUI.showMessage("You escaped successfully!", 3); // Show the msg when get the cheese.
        } else
            gp.currentUI.showMessage("You need to collect all of the cheese!", 0); // Show the msg when get the cheese.
    }
}
