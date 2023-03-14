package entity;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SmCat extends Entity {
    public SmCat(GamePanel gp) {
        super(gp);
    }

    public SmCat(GamePanel gp, int col, int row){
        super(gp);
        x = col * gp.tileSize - 3;
        y = row * gp.tileSize - 3;
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
        int goalCol = (gp.player.x + gp.player.solidAreaDefaultX) / gp.tileSize;
        int goalRow = (gp.player.y + gp.player.solidAreaDefaultY) / gp.tileSize;
        //System.out.println("excauting the set AATION==========================================================================");
        searchPath(goalCol, goalRow);
        if (!onPath)
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
        x = 13 * gp.tileSize;
        y = 12 * gp.tileSize;
    }
}
