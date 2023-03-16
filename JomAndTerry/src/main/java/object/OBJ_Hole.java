package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Hole extends SuperObject {
    public OBJ_Hole() {

        name = "Hole";
        x = 19 * 48;
        y = 14 * 48;
        solidArea.x = x;
        solidArea.y = y;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Object/door.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
  }
}
