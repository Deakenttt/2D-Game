package object;

import main.GamePanel;
import entity.Enemy;

import java.awt.*;
import java.util.ArrayList;

/**
 * @Des Class for palace objects on the map
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.
    public void setObject() {
        int row = 1;
        int col = 1;
        objectsMap = new int[gp.maxScreenCol][gp.maxScreenRow];  // contain all 0's
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[1] = new OBJ_Cheese();
        gp.obj[2] = new OBJ_Cheese();
        gp.obj[3] = new OBJ_Cheese();
        gp.obj[4] = new OBJ_Cheese();
        gp.obj[5] = new OBJ_Cheese();
        gp.obj[6] = new OBJ_Steak();
        gp.obj[7] = new OBJ_Steak();
        gp.obj[8] = new OBJ_Trap();
        gp.obj[9] = new OBJ_Trap();
        gp.obj[10] = new OBJ_Hole();

        for (int i=0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                int tileNum;
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
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
