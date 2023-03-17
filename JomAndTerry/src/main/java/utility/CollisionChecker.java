package utility;

import entity.Entity;
import entity.Player;
import main.GamePanel;

/**
 * The CollisionChecker class provides methods for checking collisions between various entities in a game.
 * It includes methods for checking collisions between an entity and a tile, an entity and an object, and an entity and another entity.
 */
public class CollisionChecker {

    GamePanel gp;
    /**
     * Holds the original row and col values before collision is checked on the next tile.
     */
    int row = 0, col = 0;

    /**
     * Constructs a CollisionChecker object with the specified GamePanel object.
     * @param gp The GamePanel object that represents the game screen.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
   
    /**
     * Checks if the specified entity collides with a tile on the game screen.
     * @param entity The entity to check for collision.
     * @return true if there is a collision, false otherwise.
     */
    public boolean checkTile(Entity entity) {
        int tileNum;
        simulateNode(entity);
        if ((entity.solidArea.x/entity.speed) >= 0){
            tileNum = gp.tileManager.mapTileNum[entity.solidArea.x/entity.speed][entity.solidArea.y/entity.speed];
            if (gp.tileManager.tile[tileNum].collision) {
                entity.solidArea.x = col; 
                entity.solidArea.y = row;
                return true;
            }
        }
        entity.solidArea.x = col;
        entity.solidArea.y = row;
        return false;
    }

    /**
     * Checks if the specified player collides with an object on the game screen.
     * @param entity The player to check for collision.
     */
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

    /**
     * Checks if the specified entity collides with another entity on the game screen.
     * @param entity The entity to check for collision.
     * @return true if there is a collision, false otherwise.
     */
    public Boolean checkEntity(Entity entity) {
        int index = 0;
        simulateNode(entity);


        // Check collisions between entity and a cat
        for (int i = 0; i < gp.enemy.length; i++) {

            if (gp.enemy[i] != null) {
                if(gp.enemy[i] != entity && entity.solidArea.intersects(gp.enemy[i].solidArea)) {
                        entity.collisionOn = true;
                        entity.solidArea.x = col;
                        entity.solidArea.y = row;
                        // check if entity is player
                        if(entity == gp.player){
                            gp.player.captured(i);
                        }
                        return true;
                }
                else index = i;
             }
        }
        // Check collisions between entity and person
        if (entity.solidArea.intersects(gp.player.solidArea) && entity != gp.player) {
            gp.player.captured(index);
        }
        entity.solidArea.x = col;
        entity.solidArea.y = row;
        return false;
    }

    /**
     * Simulates the movement of the specified entity to the next tile based on its current direction and speed.
     * @param entity The entity to simulate movement for.
     */
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