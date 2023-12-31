package entity;

import main.GamePanel;
import utility.KeyHandler;

import java.util.Objects;

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
        setAction();
        handleGameState();
        if (doMove) {
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            gp.collisionChecker.checkEntity(this);
            gp.assetSetter.steakUpdate();
        }
        if(this.x == 0 && this.y == 48 && Objects.equals(this.direction, "left")) {
            doMove = false;
            gp.ui.setMessage("sorry you can't go out of the map", "outOfMap");
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
                case "cheese" -> pickupObjectEffect(objectName, "You got a cheese!", object,  4);
                case "steak" -> {
                    System.out.println("picked up a steak");
                    pickupObjectEffect(objectName, "You got a steak!", object,  5);
                }
                case "trap" -> pickupObjectEffect(objectName, "Ouch! You touched a trap!", object,  7);
                case "hole" -> handleExit();
                default -> {
                }
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

    /**
     * This method is used to handle the effect (such as sound effect, updated score, remove the picked up object) when player collect rewards or trap.
     *
     * @param pickupObjectName  The object name in string that collected by the player, use to determine the different cases for updating the score.
     * @param pickUpObjectMsg   The message in string that used to display to the game when player collect the objects.
     * @param pickupObjectIndex The index of the object that picked up by player, used to indicate which object need to be removed from the arraylist.
     * @param pickupObjectSoundIndex The number representing the index of the sound effect. it used to play the sound effect.
     */
    public void pickupObjectEffect(String pickupObjectName, String pickUpObjectMsg, int pickupObjectIndex, int pickupObjectSoundIndex) {
        handleScore(pickupObjectName);                               // Update the score.
        gp.playSE(pickupObjectSoundIndex);                                // Play the sounds effect.
        gp.obj[pickupObjectIndex] = null;                            // Remove that object.
        gp.ui.setMessage(pickUpObjectMsg, pickupObjectName);         // Show the msg when touch object.
        System.out.println("score: " + scoreCount);
    }

    /**
     * This is a method for handling the score update.
     *
     * @param pickupObjectName A string type parameter to determine different cases of scores. such as pick up a cheese or trap, and how to deal with the score.
     */
    public void handleScore(String pickupObjectName) {
        if (Objects.equals(pickupObjectName, "cheese")) {
            cheeseCount++;
            scoreCount++;
        } else if (Objects.equals(pickupObjectName, "steak")) {
            steakCount += 5;
            scoreCount += 5;
        } else if (Objects.equals(pickupObjectName, "trap")) {
            scoreCount -= 5;
        }
    }

    /**
     * This is a method for handling the game state when the player meet some conditions.
     */
    public void handleGameState() {
        if (cheeseCount >= 6) {
            gp.ui.setMessage("Door is open!", "hole");
        }
        if (cheeseCount == 6 && gp.exitcondition) {
            gp.playSE(1);
            gp.exitcondition = false;
        }
        if (gp.player.scoreCount < 0)
            gp.gameOver();
    }

    /**
     * This is a method for handling the exit to disappear or not, determining if player can get in or not.
     */
    public void handleExit() {
        if (cheeseCount >= 6) {
            gp.gameWin();
            gp.ui.setMessage("You escaped successfully!", "hole"); // Show the msg when get the cheese.
        } else
            gp.ui.setMessage("You need to collect all of the cheese!", "cheese"); // Show the msg when get the cheese.
    }

    public void setKeyHoldTimer(int i){
        KeyHoldTimer = i;
    }
}
