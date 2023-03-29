package entity;

import main.GamePanel;

/**
The Enemy class represents a enemy entity that extends from the Entity class.
It keeps track of the enemy's path to catch the player.
*/
public class Enemy extends Entity {
    /**
     * Constructor for creating an enemy with a reference to the GamePanel.
     * @param gp The GamePanel that the enemy will be a part of.
     */
    public Enemy(GamePanel gp) {
        super(gp);
    }

    /**
     * Constructor for creating an enemy with a reference to the GamePanel and a specific starting position.
     * @param gp The GamePanel that the enemy will be a part of.
     * @param col The starting column of the enemy.
     * @param row The starting row of the enemy.
     */
    public Enemy(GamePanel gp, int col, int row){
        super(gp);
        x = col * gp.tileSize;
        y = row * gp.tileSize;
        solidAreaDefaultX = x + solidArea.width;
        solidAreaDefaultY = y + solidArea.height;
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;
        onPath = true;  // Using the A* setAction on SmCat
    }

    /**
     * Sets the default values for the enemy, including setting it on a path and a default direction and name.
     */
    public void setDefaultValues() {
        onPath = true;  // Using the A* setAction on SmCat
        direction = "down";
        name = "cat";
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;

    }
    
    /**
     * Sets the enemy's action, including searching for a path and moving in the appropriate direction.
     */
    public void setAction() {
        int goalCol = (gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.solidArea.y) / gp.tileSize;
        int startCol = (solidArea.x) / gp.tileSize;
        int startRow = (solidArea.y) / gp.tileSize;
        String[] pathOrderedList = new String[4];

        gp.findPath.setNode(startCol, startRow, goalCol, goalRow);
        collisionOn = false;
        searchPath(goalCol, goalRow, pathOrderedList);

        for(int i = 0; i < 4; i++){
            direction = pathOrderedList[i];
            collisionOn = false;
            if(gp.collisionChecker.checkTile(this)){
                continue;
            }
            if(gp.collisionChecker.checkEntity(this)){
                continue;
            };
            break;
        }
    }

    /**
     * Updates the enemy's action if the player is moving.
     */
    public void update() {
        if (gp.player.doMove) {
            setAction();
        }
        super.update();    
    }
    
    /**
     * Searches for a path to the player's location and determines the order of movements needed to get there.
     * @param goalCol The column of the player's location.
     * @param goalRow The row of the player's location.
     * @param pathOrderedList An array to store the order of movements.
     */
    public void searchPath(int goalCol, int goalRow, String[] pathOrderedList) {
        // if path(s) exist
        if (gp.findPath.aStarSearch()) {
            // next world x and world y
            int nextX = gp.findPath.pathList.get(0).col * gp.tileSize ;
            int nextY = gp.findPath.pathList.get(0).row * gp.tileSize ;

            boolean takeNextX= Math.abs(nextX - solidArea.x) > Math.abs(nextY - solidArea.y);
            if (takeNextX) {
              pathOrderedList[0] = (nextX < solidArea.x) ? "left" : "right";
              pathOrderedList[3] = (nextX < solidArea.x) ? "right" : "left";
              pathOrderedList[1] = (nextY < solidArea.y) ? "up" : "down";
              pathOrderedList[2] = (nextY < solidArea.y) ? "down" : "up";
            } else {
              pathOrderedList[0] = (nextY < solidArea.y) ? "up" : "down";
              pathOrderedList[3] = (nextY < solidArea.y) ? "down" : "up";
              pathOrderedList[1] = (nextX < solidArea.x) ? "left" : "right";
              pathOrderedList[2] = (nextX < solidArea.x) ? "right" : "left";
            }

            // If reach the goal STOP search
            int nextCol = gp.findPath.pathList.get(0).col;
            int nextRow = gp.findPath.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow)
                onPath = false;
        }
    }
}