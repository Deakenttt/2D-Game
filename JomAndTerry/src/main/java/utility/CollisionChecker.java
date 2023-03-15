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
        System.out.println("col/entity.speed = " + col/entity.speed);
            tileNum = gp.tileManager.mapTileNum[col/entity.speed][row/entity.speed];
                if (gp.tileManager.tile[tileNum].collision) {
                        return true;
                    }
        return false;
    }

    // METHOD OF CHECKING OBJECT.
    // WHEN PLAYER TOUCH THE OBJECT, IT RETURN THE INDEX OF THAT OBJECT.
    public void checkObject(Player entity) {
        
        for (int i = 0; i < gp.obj.length; i++) {
            // Checks that the object exists, that it intersects with the object, and that collision is off
            if (gp.obj[i] != null && entity.solidArea.intersects(gp.obj[i].solidArea) && !gp.obj[i].collision) {
                entity.pickUpObject(i);
                break;
            }
        }
    } 

    // NPC or Enemy
    public void checkEntity(Entity entity) {
        int index = 0;
        simulateNode(entity);

        for (int i = 0; i < gp.enemy.length; i++) {

            if (gp.enemy[i] != null) {
                if(gp.enemy[i] != entity) {

                // GET ENTITY'S SOLID AREA POSITION.
                // entity.solidArea.x = entity.x + entity.solidArea.x;
                // entity.solidArea.y = entity.y + entity.solidArea.y;

                // GET OBJECT'S SOLID AREA POSITION.
                // gp.enemy[i].solidArea.x = gp.enemy[i].x + gp.enemy[i].solidArea.x;
                // gp.enemy[i].solidArea.y = gp.enemy[i].y + gp.enemy[i].solidArea.y;

                // CHECK ENTITY'S DIRECTION.
                // Simulating entity's movement and check where it will be after it moved.
                if (entity.solidArea.intersects(gp.enemy[i].solidArea)) {
                    // index = i;
                    entity.collisionOn = true;
                }

                // RESET THE POSITION WITH DEFAULT VALUE.
                // entity.solidArea.x = entity.solidAreaDefaultX;
                // entity.solidArea.y = entity.solidAreaDefaultY;
                // gp.enemy[i].solidArea.x = gp.enemy[i].solidAreaDefaultX;
                // gp.enemy[i].solidArea.y = gp.enemy[i].solidAreaDefaultY;
            }
            else{
                index = i;
            }
        }
    }
    if (entity.solidArea.intersects(gp.player.solidArea)) {
        gp.player.captured(index);
    }
    }

    // Simulating entity's movement
    public void checkEntityDirection(Entity entity) {
        switch (entity.direction) {
            case "up" -> {
                entity.solidArea.y -= entity.speed;
                //System.out.println("up collision!");
            }
            case "down" -> {
                entity.solidArea.y += entity.speed;
                //System.out.println("down collision!");
            }
            case "left" -> {
                entity.solidArea.x -= entity.speed;
                //System.out.println("left collision!");
            }
            case "right" -> {
                entity.solidArea.x += entity.speed;
                //System.out.println("right collision!");
            }
        }
    }

public void simulateNode(Entity entity){
    try{
        switch (entity.direction) {
            case "up":
                row = entity.solidArea.y - entity.speed;
                col = entity.solidArea.x;

                break;
            case "down":
                row = entity.solidArea.y + entity.speed;
                col = entity.solidArea.x;

                break;
            case "left":
                col = entity.solidArea.x - entity.speed;
                row = entity.solidArea.y;

                break;
            case "right":
                col = entity.solidArea.x + entity.speed;
                row = entity.solidArea.y;

                break;
        }
    }   catch (Exception e) {
        System.out.println(e); 
        System.out.println("CAUGHT IN COLLISION CHECKER"); 
    }      
}
}