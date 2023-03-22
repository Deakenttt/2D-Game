package object;

import java.awt.Rectangle;

import entity.Enemy;
import main.GamePanel;

/**
 * This is a class for setting up objects, such as setting buffered images and position for objects.
 *
 */
public class AssetSetter {
    GamePanel gp;
    QuadRangeChooser chooser = new QuadRangeChooser();
    int steakCounter = 2;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Setting all the buffered images to each type of objects.
     */
    public void setObject() {
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[1] = new OBJ_Cheese();
        gp.obj[2] = new OBJ_Cheese();
        gp.obj[3] = new OBJ_Cheese();
        gp.obj[4] = new OBJ_Cheese();
        gp.obj[5] = new OBJ_Cheese();
        gp.obj[6] = null; // Steak
        gp.obj[7] = new OBJ_Trap();
        gp.obj[8] = new OBJ_Trap();
        gp.obj[9] = new OBJ_Hole();

        for (int i = 0; i < 9; i++) {

            if (gp.obj[i] != null && gp.obj[i].randomPosition) {
                object_setter(i);
            }
        }
    }
    /**
    * This is a method for creating enemy objects, and stores these into the array.
    */
    public void setEnemy(){
        gp.enemy[0] = new Enemy(gp, 1, 14);
        gp.enemy[1] = new Enemy(gp, 18, 14);
        if(gp.levelState == 2)
            gp.enemy[2] = new Enemy(gp, 18, 1);
    }

    /**
    * This is a method for setting object's solidArea and position.
    *
    * @param i object to be set
    */
    public void object_setter(int i) {
        int row, col;
        int[] quad = chooser.getNextRange();

        do {
            row = getRandomNumber(quad[0], quad[1]);
            col = getRandomNumber(quad[2], quad[3]);
        } while (!isTileAvailable(col, row));  // there isn't anything existing on the object position
        gp.obj[i].solidArea.x = (col) * gp.tileSize;//gp.obj[i].x;
        gp.obj[i].solidArea.y = (row) * gp.tileSize;//gp.obj[i].y;
    }

    /**
     * This is a method for getting the random number to use for determine the object's position.
     *
     * @param min The minimum value for the range.
     * @param max The maximum value for the range.
     * @return The random index.
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * This is a method for triggering the door object's status to open.
     */
    public void exitOpen() {
        gp.tileManager.exit_update();
        gp.obj[9].image = null;
    }

    /**
     * This is a method for updating the steak object position during the game.
     */
    public void steakUpdate() {
        if (steakCounter == 0) {
            if (gp.obj[6] == null) {
                gp.obj[6] = new OBJ_Steak();
                object_setter(6);
            } else {
                gp.obj[6] = null;
            }
            steakCounter = getRandomNumber(3, 7);
        }
        steakCounter--;
    }                                                      

    private boolean isTileAvailable(int col, int row) {
        int tileNum = gp.tileManager.mapTileNum[col][row];
        Rectangle rect = new Rectangle(col*gp.tileSize, row*gp.tileSize, 48, 48);
        return !gp.tileManager.tile[tileNum].collision && gp.collisionChecker.checkObject(rect) == 999;
    }
}
