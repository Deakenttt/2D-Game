package object;

import entity.Enemy;
import main.GamePanel;

/**
 * @Des Class for palace objects on the map
 */
public class AssetSetter {
    GamePanel gp;
    int[][] objectsMap;
   QuadRangeChooser chooser = new QuadRangeChooser();

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

    public void setEnemy(){
        gp.enemy[0] = new Enemy(gp, 1, 14);
        gp.enemy[1] = new Enemy(gp, 18, 1);
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
            steakCounter = getRandomNumber(3, 7);
        }
        steakCounter--;
    }

    public void object_setter(int i){
        int row, col;
        int[] quad = chooser.getNextRange();
        do {
            row = getRandomNumber(quad[0], quad[1]);
            col = getRandomNumber(quad[2], quad[3]);
        } while (!isTileAvailable(col, row));  // there isn't anything existing on the object position

        gp.obj[i].x = (col) * gp.tileSize;
        gp.obj[i].y = (row) * gp.tileSize;
        gp.obj[i].solidArea.x = gp.obj[i].x + gp.obj[i].solidAreaDefaultX;
        gp.obj[i].solidArea.y = gp.obj[i].y + gp.obj[i].solidAreaDefaultY;
        objectsMap[col][row] = 1;
    }

    private boolean isTileAvailable(int col, int row) {
        int tileNum = gp.tileManager.mapTileNum[col][row];
        return !gp.tileManager.tile[tileNum].collision && objectsMap[col][row] == 0;
    }

}
