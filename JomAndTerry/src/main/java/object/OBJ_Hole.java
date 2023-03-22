package object;

/**
 * @Des This is a hole class inheritance from SuperObject.
 */
public class OBJ_Hole extends SuperObject {
    public OBJ_Hole() {
        super();
    }

    public void setDefaultValues(){
        name = "hole";
        x = 19 * 48;
        y = 14 * 48;
        super.setDefaultValues();
        randomPosition = false;
    }
}
