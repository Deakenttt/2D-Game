package main;


import java.awt.*;

public class AssetSetter {
    
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setEnemy() {
        gp.enemy[0] = new Enemy(gp, Color.BLUE);
        gp.enemy[1] = new Enemy(gp, Color.RED);
        gp.enemy[2] = new Enemy(gp, Color.ORANGE);
    }
    
}
