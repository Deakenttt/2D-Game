package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Steak extends SuperObject {
    public OBJ_Steak() {

        name = "Steak";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/items/steak.png")));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
