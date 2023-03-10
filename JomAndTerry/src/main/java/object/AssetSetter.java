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

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.
    public void setObject() {

        int row;
        int col;
        objectsMap = new int[gp.maxScreenCol][gp.maxScreenRow];

        // CHEESE
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[1] = new OBJ_Cheese();
        // STEAK
        gp.obj[2] = new OBJ_Steak();
        // TRAP
        gp.obj[3] = new OBJ_Trap();
        // HOLE
        gp.obj[4] = new OBJ_Hole();

        for (int i=0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                int tileNum;
                do {
                    row = getRandomNumber(0, 16);
                    col = getRandomNumber(0, 16);
                    tileNum = gp.tileManager.mapTileNum[row][col];
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

    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE, 5 * gp.tileSize, 12 * gp.tileSize);
        gp.enemy[1] = new Enemy(gp, Color.RED, 16 * gp.tileSize, 5 * gp.tileSize);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE, 0, 0);
    }

}
