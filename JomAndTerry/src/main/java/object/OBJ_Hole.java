package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Hole extends SuperObject {
    public OBJ_Hole() {

        name = "Hole";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Object/door.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
