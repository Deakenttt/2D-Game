package object;

import main.GamePanel;

/**
 * @Des This is a cheese class inheritance from SuperObject.
 */
public class OBJ_Cheese extends SuperObject {

    public OBJ_Cheese(GamePanel gp) {
        super(gp);
    }
    public void setDefaultValues(){
        name = "cheese";
        points = 1;
        super.setDefaultValues();
    }
}
