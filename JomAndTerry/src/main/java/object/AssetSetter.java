package object;

import entity.SmCat;
import main.GamePanel;
import entity.Enemy;
import entity.Pos;
import entity.Enemy;
import java.awt.*;

/**
 * @Des Class for palace objects on the map
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;

    public int[] destHolder;  // store x, y pos
    int steakCounter = 2;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.

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

        for (int i=0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                object_setter(i);
            }
        }

        destHolder[2] =  gp.obj[7].x;
        destHolder[3] =  gp.obj[7].y;

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


        Pos p1 = new Pos(destHolder[0], destHolder[1]);
        Pos p2 = new Pos(destHolder[2], destHolder[3]);
        gp.enemy[0].dest[0] = p1;
        gp.enemy[0].dest[1] = p2;

        gp.enemy[1].dest[0] = p1;
        gp.enemy[1].dest[1] = p2;

    }
    public void setsmCat(){
        gp.smartCats[0] = new SmCat(gp, 14, 12);
        gp.smartCats[1] = new SmCat(gp, 8, 10);
    }

    public void exit_open(){
        gp.tileManager.exit_update();
        gp.obj[9].image = null;
    }

    // sets steak randomly at random intervals
    public void steak_update(){
        if(steakCounter == 0){
            if(gp.obj[6] == null){
                gp.obj[6] = new OBJ_Steak();
                object_setter(6);
            }

            else if (gp.obj[6] != null){
                gp.obj[6] = null;
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
