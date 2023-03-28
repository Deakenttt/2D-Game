package object;

import main.GamePanel;

/**
 * @Des This is a trap class inheritance from SuperObject.
 */
public class OBJ_Trap extends SuperObject{
    public OBJ_Trap(GamePanel gp) {

        super(gp);
    }
    public void setDefaultValues(){
        name = "trap";
        points = -1;
        super.setDefaultValues();
    }
}
