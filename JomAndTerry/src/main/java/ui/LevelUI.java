package ui;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class LevelUI extends GameTitleUI {
    public LevelUI(GamePanel gp) {
        super(gp);
        titleText = "Select the Level";
        subText = "Level 1 is easy and for beginners." +
                            "Level 2 is more difficult and for intermediates." +
                            " Choose Wisely! Good Luck! ";    
        titleScreenState = 2;                                                        
    }

    public void draw(Graphics2D g2){
        y = gp.tileSize * 3;
        super.draw(g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        titleButtons("Level 1", 0, g2);
        y = y - gp.tileSize;
        titleButtons("Level 2", 1, g2);
        titleButtons("Go Back", 2, g2);
    }
}
