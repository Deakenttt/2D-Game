package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * @Des This is a trap class inheritance from SuperObject.
 */
public class OBJ_Trap extends SuperObject{
    public OBJ_Trap() {

        name = "Trap";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/items/mouse_trap.png")));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
