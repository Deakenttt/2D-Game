package entity;

import main.GamePanel;

import java.awt.*;

public class SmCat extends Entity {
    public SmCat(GamePanel gp) {
        super(gp);
    }

    public void setDefaultValues() {
        x = 13 * gp.tileSize;
        y = 12 * gp.tileSize;
        speed = 5;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat
    }

    public void draw(Graphics2D g2) {
        //System.out.println("Setting Colour");

        g2.setColor(Color.cyan);
        g2.fillRect(x, y, 38, 38);
    }

    public void setAction() {
        if (onPath) {
            // distinction
            int goalCol = (gp.player.x + gp.player.solidAreaDefaultX) / gp.tileSize;
            int goalRow = (gp.player.y + gp.player.solidAreaDefaultY) / gp.tileSize;
            //System.out.println("excauting the set AATION==========================================================================");
            searchPath(goalCol, goalRow);
        }
    }
}
