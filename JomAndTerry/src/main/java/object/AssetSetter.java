package object;

import entity.SmCat;
import main.GamePanel;
import entity.Enemy;
import entity.Pos;
import entity.Enemy;
import java.awt.*;
import java.util.ArrayList;

/**
 * @Des Class for palace objects on the map
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;

    public int[] destHolder;  // store x, y pos
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.

    public void setObject() {

        destHolder = new int[10];

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

        destHolder[0] =  gp.obj[6].x;
        destHolder[1] =  gp.obj[6].y;
        destHolder[2] =  gp.obj[7].x;
        destHolder[3] =  gp.obj[7].y;

        // HOLE
        gp.obj[8] = new OBJ_Hole();
        gp.obj[8].x = 19 * gp.tileSize;
        gp.obj[8].y = 14 * gp.tileSize;

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE, 6 * gp.tileSize, 13 * gp.tileSize);
        gp.enemy[1] = new Enemy(gp, Color.RED, 14 * gp.tileSize, 6 * gp.tileSize);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE, 18*gp.tileSize, 12* gp.tileSize);

        Pos p1 = new Pos(destHolder[0], destHolder[1]);
        Pos p2 = new Pos(destHolder[2], destHolder[3]);
        gp.enemy[0].dest[0] = p1;
        gp.enemy[0].dest[1] = p2;

        gp.enemy[1].dest[0] = p1;
        gp.enemy[1].dest[1] = p2;

        gp.enemy[2].dest[0] = p1;
        gp.enemy[2].dest[1] = p2;
    }
    public void setsmCat(){
        gp.smartCats[0] = new SmCat(gp, 14, 12);
        gp.smartCats[1] = new SmCat(gp, 8, 10);
    }

    public void exit_open(){
        gp.obj[8].collision = false;
    }

}
