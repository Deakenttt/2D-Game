package utility;

import entity.Entity;
import entity.Player;
import main.GamePanel;

/**
 * @Des This is a class of checking collision.
 */
public class CollisionChecker {

    GamePanel gp;
    int row = 0;
    int col = 0;


    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
   
    // METHOD OF CHECKING ENTITY AND ENTITY
    public boolean checkTile(Entity entity) {
        int tileNum;
        simulateNode(entity);

        tileNum = gp.tileManager.mapTileNum[entity.solidArea.x/entity.speed][entity.solidArea.y/entity.speed];
        // tileNum = gp.tileManager.mapTileNum[(entity.solidArea.y - entity.solidArea.width)/entity.speed][(entity.solidArea.x - entity.solidArea.height)/entity.speed];
            if (gp.tileManager.tile[tileNum].collision) {
                    entity.solidArea.x = col; 
                    entity.solidArea.y = row;
                    return true;
                }
        entity.solidArea.x = col;
        entity.solidArea.y = row;
        return false;
    }

    // METHOD OF CHECKING OBJECT.
    // WHEN PLAYER TOUCH THE OBJECT, IT RETURN THE INDEX OF THAT OBJECT.
    public void checkObject(Player entity) {
        simulateNode(entity);

        for (int i = 0; i < gp.obj.length; i++) {
            // Checks that the object exists, that it intersects with the object
            if (gp.obj[i] != null && entity.solidArea.intersects(gp.obj[i].solidArea)){
                entity.pickUpObject(i);
                break;
            }
        }
        
        entity.solidArea.x = col;
        entity.solidArea.y = row;

    } 

    // NPC or Enemy
    public void checkEntity(Entity entity) {
        int index = 0;
        simulateNode(entity);

        for (int i = 0; i < gp.enemy.length; i++) {

            if (gp.enemy[i] != null) {
                if(gp.enemy[i] != entity) {

                    if (entity.solidArea.intersects(gp.enemy[i].solidArea)) {
                        // index = i;
                        entity.collisionOn = true;
                    }
                }
                else index = i;
             }
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            gp.player.captured(index);
        }

        entity.solidArea.x = col;
        entity.solidArea.y = row;
    }

    public void simulateNode(Entity entity){
        row = entity.solidArea.y;
        col = entity.solidArea.x;
        switch (entity.direction) {
            case "up":
                entity.solidArea.y -=  entity.speed;
                break;

            case "down":
                entity.solidArea.y += entity.speed;
                break;

            case "left":
                entity.solidArea.x -= entity.speed;
                break;

            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
    }
}