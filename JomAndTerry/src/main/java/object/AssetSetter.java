package object;

import main.GamePanel;
import entity.Enemy;

import java.awt.*;

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

        // CHEESE
        gp.obj[0] = new OBJ_Cheese();
        gp.obj[0].x = 5 * gp.tileSize;
        gp.obj[0].y = 12 * gp.tileSize;

        gp.obj[1] = new OBJ_Cheese();
        gp.obj[1].x = 7 * gp.tileSize;
        gp.obj[1].y = 12 * gp.tileSize;

        // STEAK
        gp.obj[2] = new OBJ_Steak();
        gp.obj[2].x = 16 * gp.tileSize;
        gp.obj[2].y = 5 * gp.tileSize;

        // TRAP
        gp.obj[3] = new OBJ_Trap();
        gp.obj[3].x = 16 * gp.tileSize;
        gp.obj[3].y = 8 * gp.tileSize;

        // HOLE
        gp.obj[4] = new OBJ_Hole();
        gp.obj[4].x = 19 * gp.tileSize;
        gp.obj[4].y = 14 * gp.tileSize;
    }

    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE, 5 * gp.tileSize, 12 * gp.tileSize);
        gp.enemy[1] = new Enemy(gp, Color.RED, 16 * gp.tileSize, 5 * gp.tileSize);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE, 0, 0);
    }
}
