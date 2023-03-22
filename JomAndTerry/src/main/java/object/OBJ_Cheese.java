package object;

/**
 * @Des This is a cheese class inheritance from SuperObject.
 */
public class OBJ_Cheese extends SuperObject {

    public OBJ_Cheese() {
        super();
    }
    public void setDefaultValues(){
        name = "cheese";
        points = 1;
        super.setDefaultValues();
    }
}
