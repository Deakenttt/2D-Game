package object;

import javax.swing.border.EmptyBorder;

import entity.Enemy;
import main.GamePanel;

/**
 * @Des This is a class for setting up objects.
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;


    public int[] destHolder;  // store x, y pos
    int steakCounter = 2;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    /**
     * Setting all the buffered images to each type of objects.
     */
    public void setObject() {
        destHolder = new int[10];
        objectsMap = new int[gp.maxScreenCol][gp.maxScreenRow];  // contain all 0's
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[1] = new OBJ_Cheese();
        gp.obj[2] = new OBJ_Cheese();
        gp.obj[3] = new OBJ_Cheese();
        gp.obj[4] = new OBJ_Cheese();
        gp.obj[5] = new OBJ_Cheese();
        gp.obj[7] = new OBJ_Trap();
        gp.obj[8] = new OBJ_Trap();

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                object_setter(i);
            }
        }

        // Door
        destHolder[2] = gp.obj[7].x;
        destHolder[3] = gp.obj[7].y;

        // Hole
        gp.obj[9] = new OBJ_Hole();
        gp.obj[9].x = 19 * gp.tileSize;
        gp.obj[9].y = 14 * gp.tileSize;


        // Steak
        gp.obj[6] = null;
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
     * This is a method for creating enemy objects, and stores these into the array.
     */
    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, 14, 12);
        gp.enemy[1] = new Enemy(gp, 8, 10);
    }

    /**
     * This is a method for triggering the door object's status to open.
     */
    public void exit_open() {
        gp.tileManager.exit_update();
        gp.obj[9].image = null;
    }

    /**
     * This is a method for updating the steak object position during the game.
     */
    public void steak_update() {
        if (steakCounter == 0) {
            if (gp.obj[6] == null) {
                gp.obj[6] = new OBJ_Steak();
                object_setter(6);
            } else {
                gp.obj[6] = null;
            }
            steakCounter = getRandomNumber(4, 8);
        }
        steakCounter--;
    }

    /**
     * This is a method for setting object's solidArea and position.
     *
     * @param i
     */
    public void object_setter(int i) {
        int tileNum;
        int row;
        int col;

        do {
            row = getRandomNumber(1, 15);
            col = getRandomNumber(1, 15);
            tileNum = gp.tileManager.mapTileNum[col][row];
        } while (gp.tileManager.tile[tileNum].exist || objectsMap[row][col] != 0);  // there isn't anything existing on the object position
        gp.obj[i].x = (col) * gp.tileSize;
        gp.obj[i].y = (row) * gp.tileSize;
        gp.obj[i].solidArea.x = gp.obj[i].x + gp.obj[i].solidAreaDefaultX;
        gp.obj[i].solidArea.y = gp.obj[i].y + gp.obj[i].solidAreaDefaultY;
        objectsMap[row][col] = 1;
    }

}
