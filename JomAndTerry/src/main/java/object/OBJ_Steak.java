package object;

/**
 * @Des This is a steak class inheritance from SuperObject.
 */
public class OBJ_Steak extends SuperObject {
    public OBJ_Steak() {
        super();
    }

    @Override
    public void setDefaultValues() {
        name = "steak";
        points = 5;
        super.setDefaultValues();
    }
}
