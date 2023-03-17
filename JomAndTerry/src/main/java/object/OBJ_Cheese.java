package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * @Des This is a cheese class inheritance from SuperObject.
 */
public class OBJ_Cheese extends SuperObject {

    public OBJ_Cheese() {

        name = "Cheese";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/items/cheese.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
