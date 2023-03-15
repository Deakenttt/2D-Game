package entity;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
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
        getPlayerImage();
        speed = 48;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat

    }

    public void setDefaultValues() {
        x = 13 * gp.tileSize;
        y = 12 * gp.tileSize;
        getPlayerImage();
        speed = 48;

        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat

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

        int goalCol = (gp.player.x + solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.y + solidArea.y) / gp.tileSize;
        //System.out.println("excauting the set AATION==========================================================================");
        searchPath(goalCol, goalRow);

        Rectangle solidAreaP = new Rectangle(gp.player.x,gp.player.y,30,30);
        Rectangle solidAreaC = new Rectangle(this.x,this.y,30,30);
        if(solidAreaP.intersects(solidAreaC))
            gp.ui.gameLose = true;
    }

    public void update() {
        setAction();
        collisionOn = false;
        super.update();    

    }
    public void draw(Graphics2D g2) {

//        g2.setColor(Color.cyan);
//        g2.fillRect(x, y, 38, 38);
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, x, y, null);
    }

    public void retry() {
        onPath = true;

    }

    public void searchPath(int goalCol, int goalRow) {
    int startCol = (x + solidAreaDefaultX) / gp.tileSize;
    int startRow = (y + solidAreaDefaultY) / gp.tileSize;
    gp.findPath.setNode(startCol, startRow, goalCol, goalRow, this);
    // we found the path
    if (gp.findPath.aStarSearch()) {
        // next world x and world y
        int nextX = gp.findPath.pathList.get(0).col * gp.tileSize ;
        int nextY = gp.findPath.pathList.get(0).row * gp.tileSize ;
        // Entity's solid Area pos
        int enLeftX = x + solidArea.x;
        int enRightX = x + solidArea.x + solidArea.width;
        int enTopY = y + solidArea.y;
        int enBottomY = y + solidArea.y + solidArea.height;
        if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
            direction = "up";
        } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
            direction = "down";
        } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
            // left or right
            if (enLeftX > nextX) {
                direction = "left";
            }
            if (enLeftX < nextX) {
                direction = "right";
            }
        } else if (enTopY > nextY && enLeftX > nextX) {
            // up or left
            direction = "up";
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            if (collisionOn)
                direction = "left";
        } else if (enTopY > nextY && enLeftX < nextX) {
            direction = "up";
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            if (collisionOn)
                direction = "right";
        } else if (enTopY < nextY && enLeftX > nextX) {
            direction = "down";
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            if (collisionOn)
                direction = "left";
        } else if (enTopY < nextY && enLeftX < nextX) {
            direction = "down";
            collisionOn = false;
            collisionOn = gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
            if (collisionOn)
                direction = "right";
        }
        // If reach the goal STOP search
        int nextCol = gp.findPath.pathList.get(0).col;
        int nextRow = gp.findPath.pathList.get(0).row;
        if (nextCol == goalCol && nextRow == goalRow)
             onPath = false;
    }
}
}
