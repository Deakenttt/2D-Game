package entity;

import main.GamePanel;
import utility.KeyHandler;

/**
* The Player class represents a player entity that extends from the Entity class.
* It keeps track of the player's score, the number of cheese and steak, and whether the player is captured.
*/
public class Player extends Entity {
    KeyHandler keyHandler;
    public int hasCheese = 0; // Tracking the number of cheese.
    public int hasSteak = 0; // Tracking the number of steak.
    public int totalScore = 0; // Tracking the total score.
    boolean captureFlag = false; // Flag for being caught.
    int KeyHoldTimer = 0; // Timer for how long the player has hold the key.

    /**
     * Constructs a Player object.
     *
     * @param gp The GamePanel object.
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
        hasCheese = 0; // resets the number of cheese.
        hasSteak = 0; // resets the number of steak.
        totalScore = 0; // resets the total score.
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
        if (hasCheese >= 6) 
            gp.ui.showMessage("Door is open!", 5);

        setAction();
        if(doMove){
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            gp.collisionChecker.checkObject(this);
            gp.collisionChecker.checkEntity(this);
            gp.assetSetter.steak_update();      
        }
        super.update();
    }


    /**
     * Picks up an object and updates the player's score and inventory.
     *
     * @param i The index of the object being picked up.
     */
    public void pickUpObject(int object) {

        if (object != 999) {
            String objectName = gp.obj[object].name; // Get the type of different objects.

            switch (objectName) {
                case "Cheese":
                    gp.playSE(4);
                    hasCheese++;
                    totalScore++;
                    gp.obj[object] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You got a cheese!", 1); // Show the msg when touch object.
                    if (hasCheese == 6 && gp.exitcondition) {
                        gp.playSE(1);
                        gp.exitcondition = false;
                    }
                    break;

                case "Steak":
                    gp.playSE(5);
                    hasSteak += 5;
                    totalScore += 5;
                    gp.obj[object] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("You got a steak!", 2); // Show the msg when touch object.
                    if (hasCheese == 6 && gp.exitcondition) {
                        gp.playSE(1);
                        gp.exitcondition = false;
                    }
                    break;

                case "Trap": 
                    gp.playSE(7);
                    totalScore -= 5;
                    gp.obj[object] = null;
                    System.out.println("score: " + totalScore);
                    gp.ui.showMessage("Ouch! You touched a trap!", 3); // Show the msg when touch object.
                    if (gp.player.totalScore < 0)
                        gp.ui.gameLose = true;
                    break;
                    

                case "Hole":
                    if (hasCheese >= 6) {
                        gp.ui.showMessage("You escape successfully!", object); // Show the msg when get the cheese.
                        gp.ui.gameEnd = true; // End the game
                    } else
                        gp.ui.showMessage("You need to collect all of the cheese!", 1); // Show the msg when get the cheese.
                    
                    break;
            }
        }
    }

    /**
    * This method is used to handle the player capture event.
    * @param i The index of the entity that has captured the player.
    */
    public void captured(int cat) {
        if (cat != 999 || captureFlag) {
            captureFlag = true;
            gp.ui.gameLose = true; // End the game.
            gp.playSE(6);
        }
    }
}
