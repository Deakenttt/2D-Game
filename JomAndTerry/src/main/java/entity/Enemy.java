package entity;

import main.GamePanel;
import utility.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity{
    
    public Enemy(GamePanel gp, Color setColour){
        super(gp);
        direction = "down";
        speed = 1;        
        colour = setColour;
         // SOLID AREA FOR COLLISION
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void draw(Graphics2D g2) {
        System.out.println("Setting Colour");

        g2.setColor(colour);
        g2.drawRect(x, y, 48, 48);
    }
    
    public void setAction() {
        actionLockC++;
        
        if (actionLockC == 90){
            Random random = new Random();
            int i = random.nextInt(100) + 1;
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
        }
    }
    

    Color colour;
    int actionLockC = 0;


    



    
}
