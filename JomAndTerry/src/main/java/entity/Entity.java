package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

// Blueprint for character classes
import java.awt.*;

/**
 * @Des This is an Entity class for Player, Cat
 */
public class Entity {

    // ENTITY'S COORDINATE AND SPEED
    public int x, y;
    public int speed;
    GamePanel gp;

    // It describes an Image with an accessible buffer of image data.
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public boolean doMove = true; // only move the entity when doMove is true.

    // FOR ANIMATION
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean onPath = false;


    public Entity(GamePanel gp) {
        this.gp = gp;
        // SOLID AREA FOR COLLISION
        solidArea = new Rectangle(); // (x, y, width, height)
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 22;
        solidArea.height = 22;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        onPath = true;  // Using the A* setAction on SmCat
    }

    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 8;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat
    }

    public void setAction() {
    }

    public void update() {

        gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
        System.out.println("collisionOn = " + collisionOn);
        if (gp.player.doMove) {
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 3) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            gp.assetSetter.steak_update();
            }
            
        }
    }

    // FOR COLLISION
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // SAVING THE DEFAULT VALUE OF X AND Y
    public int solidAreaDefaultX, solidAreaDefaultY;

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (x + solidArea.x) / gp.tileSize;
        int startRow = (y + solidArea.y) / gp.tileSize;

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
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method

                if (collisionOn)
                    direction = "left";
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                collisionOn = false;
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if (collisionOn)
                    direction = "right";
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                collisionOn = false;
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if (collisionOn)
                    direction = "left";
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                collisionOn = false;
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
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
