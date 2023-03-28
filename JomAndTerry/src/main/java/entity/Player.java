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

        if (cheeseCount >= 6) 
            gp.currentUI.showMessage("Door is open!", 5);

        setAction();
        if(doMove){
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
     * @param i The index of the object being picked up.
     */
    public void pickUpObject(int object) {
        if (object != 999) {
            String objectName = gp.obj[object].name; // Get the type of different objects.
            switch (objectName) {
                case "cheese":
                    gp.playSE(4);
                    cheeseCount++;
                    scoreCount++;
                    gp.obj[object] = null;
                    System.out.println("score: " + scoreCount);
                    gp.currentUI.showMessage("You got a cheese!", 1); // Show the msg when touch object.
                    if (cheeseCount == 6 && gp.exitcondition) {
                        gp.playSE(1);
                        gp.exitcondition = false;
                    }
                    break;

                case "steak":
                    gp.playSE(5);
                    steakCount += 5;
                    scoreCount += 5;
                    gp.obj[object] = null;
                    System.out.println("score: " + scoreCount);
                    gp.currentUI.showMessage("You got a steak!", 2); // Show the msg when touch object.
                    break;

                case "trap": 
                    gp.playSE(7);
                    scoreCount -= 5;
                    gp.obj[object] = null;
                    System.out.println("score: " + scoreCount);
                    gp.currentUI.showMessage("Ouch! You touched a trap!", 3); // Show the msg when touch object.
                    if (gp.player.scoreCount < 0)
                        gp.gameOver();
                    break;
                    

                case "hole":
                    if (cheeseCount >= 6) {
                        gp.gameWin();
                        gp.currentUI.showMessage("You escaped successfully!", object); // Show the msg when get the cheese.
                    } else
                        gp.currentUI.showMessage("You need to collect all of the cheese!", 1); // Show the msg when get the cheese.
                    
                    break;
                default:
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
            gp.gameOver();
           gp.playSE(6);
        }
    }
}
