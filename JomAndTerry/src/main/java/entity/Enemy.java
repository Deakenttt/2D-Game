package entity;

import main.GamePanel;
import utility.KeyHandler;

import java.awt.*;
import java.util.Random;
import object.AssetSetter.*;

public class Enemy extends Entity{
    
    public Enemy(GamePanel gp, Color setColour, int posX, int posY){
        super(gp);
        x = posX;
        y = posY;

        direction = "left";
        speed = 1;        
        colour = setColour;
    }
    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 48;
        direction = "down";
    }

    public void draw(Graphics2D g2) {

        g2.setColor(colour);
        g2.drawRect(x, y, 48, 48);
    }
    
    public void setAction() {
        actionLockC++;
        
        if (actionLockC == 25){
            Random random = new Random();
            int i = (int) ((Math.random() * (100 - 1)) + 1);
            if (i <= 25) {
                direction = "up";
            }

            else if (i > 25 && i <= 50){
                direction = "down";
            }

            else if (i > 25 && i <= 50){
                direction = "left";
            }

            else{
                direction = "right";
            }
            actionLockC = 0;
            //System.out.println("the Pos is: " + x + ", " + y);
        }
    }
    /*public void searchPath(int goalCol, int goalRow){
        int startCol = (x + solidArea.x)/gp.tileSize;
        int startRow = (y + solidArea.y)/gp.tileSize;

        gp.findPath.setNode(startCol, startRow, goalCol, goalRow, this);
        // we found the path
        if(gp.findPath.aStarSearch()){
            // next world x and world y
            int nextX = gp.findPath.pathList.get(0).col * gp.tileSize;
            int nextY = gp.findPath.pathList.get(0).row * gp.tileSize;

            // Entity's solid Area pos
            int enLeftX = x + solidArea.x;
            int enRightX = x + solidArea.x + solidArea.width;
            int enTopY = y + solidArea.y;
            int enBottomY = y + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextX && enBottomY < nextY + gp.tileSize){
                // left or right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                // up or left
                direction = "up";
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if(collisionOn)
                    direction = "left";
            }
            else if(enTopY > nextY && enLeftX < nextX){
                direction = "up";
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if(collisionOn)
                    direction = "right";
            }
            else if(enTopY < nextY && enLeftX > nextX){
                direction = "down";
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if(collisionOn)
                    direction = "left";
            }
            else if(enTopY < nextY && enLeftX < nextX){
                direction = "down";
                gp.collisionChecker.checkTile(this); // Calls CollisionChecker object's checkTile method
                if(collisionOn)
                    direction = "right";
            }

            // If reach the goal STOP search
            int nextCol = gp.findPath.pathList.get(0).col;
            int nextRow = gp.findPath.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow)
                onPath = false;
        }

    }*/
    

    Color colour;
    int actionLockC = 0;


    



    
}
