package object;

/**
 * @Des This is a trap class inheritance from SuperObject.
 */
public class OBJ_Trap extends SuperObject{
    public OBJ_Trap() {

        super();
    }
    public void setDefaultValues(){
        name = "trap";
        points = -1;
        super.setDefaultValues();
    }
}
