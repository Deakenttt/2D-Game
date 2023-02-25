package utility;

import entity.Entity;
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

        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX / gp.tileSize;
        int entityRightCol = entityRightX / gp.tileSize;
        int entityTopRow = entityTopY / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    // METHOD OF CHECKING OBJECT.
    // WHEN PLAYER TOUCH THE OBJECT, IT RETURN THE INDEX OF THAT OBJECT.
    public int checkObject(Entity entity, boolean player) {

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
                switch (entity.direction) {
                    case "up" -> {

                        // Simulating entity's movement and check where it will be after it moved.
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            System.out.println("up collision!");
                            if (player)
                                index = i;
                        }
                    }
                    case "down" -> {

                        // Simulating entity's movement and check where it will be after it moved.
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            System.out.println("down collision!");
                            if (player)
                                index = i;
                        }
                    }
                    case "left" -> {

                        // Simulating entity's movement and check where it will be after it moved.
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            System.out.println("left collision!");
                            if (player)
                                index = i;
                        }
                    }
                    case "right" -> {

                        // Simulating entity's movement and check where it will be after it moved.
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            System.out.println("right collision!");
                            if (player)
                                index = i;
                        }
                    }
                }

                // RESET THE POSITION WITH DEFAULT VALUE.
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
