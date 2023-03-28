package object;

import main.GamePanel;

/**
 * @Des This is a steak class inheritance from SuperObject.
 */
public class OBJ_Steak extends SuperObject {
    public OBJ_Steak(GamePanel gp) {
        super(gp);
    }

    @Override
    public void setDefaultValues() {
        name = "steak";
        points = 5;
        super.setDefaultValues();
    }
}
