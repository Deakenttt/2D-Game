package utility;

import entity.Entity;
import entity.Player;
import main.GamePanel;

/**
 * @Des This is a class of checking collision.
 */
public class CollisionChecker {

    GamePanel gp;


    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
   
    // METHOD OF CHECKING ENTITY AND ENTITY
    public boolean checkTile(Entity entity, int row, int col) {
        int tileNum;

        try{
            switch (entity.direction) {
                case "up":
                    tileNum = gp.tileManager.mapTileNum[col/ gp.tileSize][(row - entity.speed)/ gp.tileSize];

                    if (gp.tileManager.tile[tileNum].collision) {
                        return true;
                    }

                    break;
                case "down":
                    tileNum = gp.tileManager.mapTileNum[col/ gp.tileSize][(entity.speed + row)/ gp.tileSize];

                    if (gp.tileManager.tile[tileNum].collision) {
                        return true;
                    }

                    break;
                case "left":
                    tileNum = gp.tileManager.mapTileNum[(col-entity.speed)/ gp.tileSize][row/ gp.tileSize];
                    if (gp.tileManager.tile[tileNum].collision) {
                        return true;

                    }

                    break;
                case "right":
                    tileNum = gp.tileManager.mapTileNum[(col+entity.speed)/ gp.tileSize][row/ gp.tileSize];
                    if (gp.tileManager.tile[tileNum].collision) {
                        return true;
                    }

                    break;
                } 
            }   catch (Exception e) {
                System.out.println(e); 
                System.out.println("CAUGHT IN COLLISION CHECKER"); 
            }
        return false;
    }

    // METHOD OF CHECKING OBJECT.
    // WHEN PLAYER TOUCH THE OBJECT, IT RETURN THE INDEX OF THAT OBJECT.
    public void checkObject(Player entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // GET ENTITY'S SOLID AREA POSITION.
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
 
                // GET OBJECT'S SOLID AREA POSITION.
                gp.obj[i].solidArea.x = gp.obj[i].x + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].y + gp.obj[i].solidArea.y;

                // CHECK ENTITY'S DIRECTION.
                // Simulating entity's movement and check where it will be after it moved.
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player)
                        index = i;
                }

                // RESET THE POSITION WITH DEFAULT VALUE.
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        if (index != 999) {
            entity.pickUpObject(index);
        }
    } 

    // NPC or Enemy
    public void checkEntity(Player entity) {

        int index = 999;

        for (int i = 0; i < gp.enemy.length; i++) {

            if (gp.enemy[i] != null) {

                // GET ENTITY'S SOLID AREA POSITION.
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                // GET OBJECT'S SOLID AREA POSITION.
                gp.enemy[i].solidArea.x = gp.enemy[i].x + gp.enemy[i].solidArea.x;
                gp.enemy[i].solidArea.y = gp.enemy[i].y + gp.enemy[i].solidArea.y;

                // CHECK ENTITY'S DIRECTION.
                // Simulating entity's movement and check where it will be after it moved.
                if (entity.solidArea.intersects(gp.enemy[i].solidArea)) {
                    index = i;
                }

                // RESET THE POSITION WITH DEFAULT VALUE.
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.enemy[i].solidArea.x = gp.enemy[i].solidAreaDefaultX;
                gp.enemy[i].solidArea.y = gp.enemy[i].solidAreaDefaultY;
            }
        }
        if (index != 999) {
            entity.captured(index);
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
}