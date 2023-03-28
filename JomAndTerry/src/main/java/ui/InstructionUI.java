package ui;
import java.awt.Graphics2D;

import main.GamePanel;

public class InstructionUI extends GameTitleUI {
public InstructionUI(GamePanel gp) {
        super(gp);
        titleText = "Instructions";
        subText =  "You are the mouse (Terry). Collect 6 cheeses to unlock the exit door. " +
         "Avoid the smart cats (Joms; yes they're all named Jom). " +
         "Cheese = 1 point, Steak (bonus) = 5 points, mouse trap (punishment) = -5 points. " +
         "Negative points = Automatic loss. " +
         "Exit with max points and the fastest time, there is no time limit. Enjoy!";
         titleScreenState = 1;
    }
                    
    public void draw(Graphics2D g2){
        y = gp.tileSize * 3;
        super.draw(g2);
        g2.setFont(g2.getFont().deriveFont(40F));
        titleButtons("Play The Game", 0, g2);
        titleButtons("Go Back", 1, g2);
    }
}
