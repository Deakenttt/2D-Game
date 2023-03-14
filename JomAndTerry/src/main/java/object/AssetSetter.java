package object;

import main.GamePanel;
import entity.Enemy;

import java.awt.*;

/**
 * @Des Class for palace objects on the map
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;
    int steakCounter = 2;
    boolean steakFlag = false;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.
    public void setObject() {
        objectsMap = new int[gp.maxScreenCol][gp.maxScreenRow];  // contain all 0's
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[1] = new OBJ_Cheese();
        gp.obj[2] = new OBJ_Cheese();
        gp.obj[3] = new OBJ_Cheese();
        gp.obj[4] = new OBJ_Cheese();
        gp.obj[5] = new OBJ_Cheese();
        gp.obj[6] = new OBJ_Steak();
        gp.obj[7] = new OBJ_Trap();
        gp.obj[8] = new OBJ_Trap();

        for (int i=0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                object_setter(i);
            }
        }

        // HOLE
        gp.obj[9] = new OBJ_Hole();
        gp.obj[9].x = 19 * gp.tileSize;
        gp.obj[9].y = 14 * gp.tileSize;

        // STEAK
        gp.obj[6] = null;

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE, 6 * gp.tileSize, 13 * gp.tileSize);
        gp.enemy[1] = new Enemy(gp, Color.RED, 14 * gp.tileSize, 6 * gp.tileSize);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE, 18*gp.tileSize, 12* gp.tileSize);

    }

    public void exit_open(){
        gp.obj[8].collision = false;
    }

    // sets steak randomly at random intervals
    public void steak_update(){
        if(steakCounter == 0){
            if(gp.obj[6] == null){
                gp.obj[6] = new OBJ_Steak();
                object_setter(6);
                steakFlag = true;
            }

            else if (gp.obj[6] != null){
                gp.obj[6] = null;
                steakFlag = false;
            }
            steakCounter = getRandomNumber(4, 8);
        }
        steakCounter--;
    }

    public void object_setter(int i){
        int tileNum;
        int row;
        int col;

        do {
            row = getRandomNumber(1, 15);
            col = getRandomNumber(1, 15);
            tileNum = gp.tileManager.mapTileNum[col][row];
        } while (gp.tileManager.tile[tileNum].exist || objectsMap[row][col] != 0);  // there isn't anything existing on the object position
        gp.obj[i].x = (col ) * gp.tileSize;
        gp.obj[i].y = (row ) * gp.tileSize;
        objectsMap[row][col] = 1;
    }

}
