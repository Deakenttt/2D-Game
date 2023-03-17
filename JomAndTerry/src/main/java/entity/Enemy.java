package entity;
import main.GamePanel;

public class Enemy extends Entity {


    public Enemy(GamePanel gp) {
        super(gp);
    }

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

    public void setDefaultValues() {
        onPath = true;  // Using the A* setAction on SmCat
        direction = "down";
        name = "cat";
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;

    }

    public void setAction() {
        // distinction

        int goalCol = (gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.solidArea.y) / gp.tileSize;
        int startCol = (solidArea.x) / gp.tileSize;
        int startRow = (solidArea.y) / gp.tileSize;

        String[] pathOrderedList = new String[4];

        gp.findPath.setNode(startCol, startRow, goalCol, goalRow, this);
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

    public void update() {
        if (gp.player.doMove) {
            setAction();
        }
        super.update();    
    }


    public void searchPath(int goalCol, int goalRow, String[] pathOrderedList) {
        // we found the path
        if (gp.findPath.aStarSearch()) {

            // next world x and world y
            int nextX = gp.findPath.pathList.get(0).col * gp.tileSize ;
            int nextY = gp.findPath.pathList.get(0).row * gp.tileSize ;

            Boolean takeNextX= Math.abs(nextX - solidArea.x) > Math.abs(nextY - solidArea.y);
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