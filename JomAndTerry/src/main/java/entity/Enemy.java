package entity;

import main.GamePanel;
import java.awt.*;

public class Enemy extends Entity{
    
    public Enemy(GamePanel gp, Color setColour, int posX, int posY){
        super(gp);
        x = posX;
        y = posY;

        direction = "left";
        speed = 48;
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

    public void update() {
        setAction();
        collisionOn = false;
        super.update();    
    }
    Color colour;
    int actionLockC = 0;

    
}
