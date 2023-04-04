package object;

import main.GamePanel;

/**
 * @Des This is a trap class inheritance from SuperObject.
 */
public class Trap extends SuperObject{
    public Trap(GamePanel gp) {

        super(gp);
    }
    public void setDefaultValues(){
        name = "trap";
        points = -1;
        super.setDefaultValues();
    }
}
