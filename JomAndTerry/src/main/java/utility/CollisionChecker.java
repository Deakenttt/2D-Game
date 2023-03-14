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
    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        try{
            switch (entity.direction) {
                case "up":
                    entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

                    if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }

                    break;
                case "down":
                    entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                    if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }

                    break;
                case "left":
                    entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                    if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }

                    break;
                case "right":
                    entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                    if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }

                    break;
                } 
            }   catch (Exception e) {
                entity.collisionOn = true;
                System.out.println(e); 
                System.out.println("CAUGHT IN COLLISION CHECKER"); 
        

            }
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
                // checkEntityDirection(entity);
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    //System.out.println(gp.obj[i].collision + gp.obj[i].name);
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
                // checkEntityDirection(entity);
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