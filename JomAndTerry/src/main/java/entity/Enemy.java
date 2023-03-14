package entity;

import main.GamePanel;
import object.AssetSetter;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Enemy extends Entity{
    public Pos p1;
    public Pos p2;
    public Pos[] dest = {p1, p2};
    public AssetSetter assetSetter = new AssetSetter(gp);

    public Enemy(GamePanel gp, Color setColour, int posX, int posY){
        super(gp);
        x = posX;
        y = posY;
        getPlayerImage();

        speed = 48;
        colour = setColour;
        direction = "right";
        onPath = true;  // Using the A* setAction on SmCat
    }
    public void setDefaultValues() {
        x = 48;
        y = 48;
        speed = 10;
        direction = "down";
    }


    public void getPlayerImage() {

        up1 = setup("dumb_cat_up_1");
        up2 = setup("dumb_cat_up_2");

        down1 = setup("dumb_cat_down_1");
        down2 = setup("dumb_cat_down_2");

        left1 = setup("dumb_cat_left_1");
        left2 = setup("dumb_cat_left_2");

        right1 = setup("dumb_cat_right_1");
        right2 = setup("dumb_cat_right_2");
    }
    public BufferedImage setup(String imageName) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/cat/" + imageName + ".png")));
            image = utilityTool.scaleImage(image, gp.tileSize - 10, gp.tileSize - 10);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    
    public void setAction() {
//        actionLockC++;
//        if (actionLockC == 25){
//            int i = (int) ((Math.random() * (100 - 1)) + 1);
//            if (i <= 25) {
//                direction = "up";
//            }
//
//            else if (i > 25 && i <= 50){
//                direction = "down";
//            }
//
//            else if (i > 25 && i <= 50){
//                direction = "left";
//            }
//
//            else{
//                direction = "right";
//            }
//            actionLockC = 0;
//            //System.out.println("the Pos is: " + x + ", " + y);
//        }
        int R = (int) ((Math.random() * (10 - 1)) + 10) % 2;

        int goalCol = dest[R].x/gp.tileSize;
        int goalRow = dest[R].y/gp.tileSize;
        searchPath(goalCol, goalRow);

    }

    public void update() {
        setAction();
        collisionOn = false;
        super.update();
    }

    public void draw(Graphics2D g2) {

//        g2.setColor(colour);
//        g2.drawRect(x, y, 48, 48);
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

    Color colour;
    int actionLockC = 0;

}
