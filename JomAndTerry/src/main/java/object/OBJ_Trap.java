package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

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
