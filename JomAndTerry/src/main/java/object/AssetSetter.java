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

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // METHOD FOR SETTING OBJECT.
    public void setObject() {

        for (int i = 0; i < gp.obj.length; i++) {

            // CHEESE
            if (i < 6) {
                gp.obj[i] = new OBJ_Cheese();
                ArrayList<Integer> pos = getRandomPosition();
                gp.obj[i].x = pos.get(0) * gp.tileSize;
                gp.obj[i].y = pos.get(1) * gp.tileSize;
            }
        }

        // STEAK
        gp.obj[6] = new OBJ_Steak();
        ArrayList<Integer> pos = getRandomPosition();
        gp.obj[6].x = pos.get(0) * gp.tileSize;
        gp.obj[6].y = pos.get(1) * gp.tileSize;


        // TRAP
        gp.obj[7] = new OBJ_Trap();
        pos = getRandomPosition();
        gp.obj[7].x = pos.get(0) * gp.tileSize;
        gp.obj[7].y = pos.get(1) * gp.tileSize;

        // HOLE
        gp.obj[8] = new OBJ_Hole();
        gp.obj[8].x = 19 * gp.tileSize;
        gp.obj[8].y = 14 * gp.tileSize;

    }

    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE, 5 * gp.tileSize, 12 * gp.tileSize);
        gp.enemy[1] = new Enemy(gp, Color.RED, 16 * gp.tileSize, 6 * gp.tileSize);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE, 0, 1);

    }

    public ArrayList<Integer> getRandomPosition() {
        int tmpX = (int) Math.floor(Math.random() * (20));
        int tmpY = (int) Math.floor(Math.random() * (16));
        while (gp.tileManager.mapTileNum[tmpX][tmpY] != 0) {
            tmpX = (int) Math.floor(Math.random() * (20));
            tmpY = (int) Math.floor(Math.random() * (16));
        }
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(tmpX);
        ret.add(tmpY);
        return ret;
    }

}
