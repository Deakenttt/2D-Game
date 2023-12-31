package object;

import main.GamePanel;

/**
 * @Des This is a hole class inheritance from SuperObject.
 */
public class Hole extends SuperObject {
    public Hole(GamePanel gp) {
        super(gp);
    }

    public void setDefaultValues(){
        name = "hole";
        x = 19 * 48;
        y = 14 * 48;
        super.setDefaultValues();
        randomPosition = false;
    }
}
