package entity;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity {
    public Enemy(GamePanel gp) {
        super(gp);
    }

    public Enemy(GamePanel gp, int col, int row){
        super(gp);

        x = col * gp.tileSize;
        y = row * gp.tileSize;
        solidArea.x = x + solidArea.width;
        solidArea.y = y + solidArea.height;
        System.out.println("SolidArea.x = " + solidArea.x + " SolidArea.y = " + solidArea.y);

        getPlayerImage();
        speed = 48;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat

    }

    public void setDefaultValues() {
        getPlayerImage();
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void getPlayerImage() {

        up1 = setup("smart_cat_up_1");
        up2 = setup("smart_cat_up_2");

        down1 = setup("smart_cat_down_1");
        down2 = setup("smart_cat_down_2");

        left1 = setup("smart_cat_left_1");
        left2 = setup("smart_cat_left_2");

        right1 = setup("smart_cat_right_1");
        right2 = setup("smart_cat_right_2");
    }
    public BufferedImage setup(String imageName) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/cat/" + imageName + ".png")));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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
                System.out.println("Direction set = " + direction );
                collisionOn = false;
                if(gp.collisionChecker.checkTile(this)){
                    System.out.println("tile invalid");
                    continue;
                }
                if(gp.collisionChecker.checkEntity(this)){
                    System.out.println("tile has cat");
                    continue;
                };
                break;
            }
            System.out.println(collisionOn);
    }

    public void update() {
        if (gp.player.doMove) {
            System.out.println("ENEMY: ");
            setAction();
            super.update();    
        }
    }

    public void retry() {
        onPath = true;
    }

    public void searchPath(int goalCol, int goalRow, String[] pathOrderedList) {
        // we found the path
        if (gp.findPath.aStarSearch()) {
            // next world x and world y
            int nextX = gp.findPath.pathList.get(0).col * gp.tileSize ;
            int nextY = gp.findPath.pathList.get(0).row * gp.tileSize ;
            System.out.println("nextX = " + nextX/48 + " solidArea.x = " + solidArea.x/48 + " nextY = " + nextY/48 + " solidArea.y = " + solidArea.y/48);

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