import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{

    public GamePanel() {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setBackground(Color.black);

        this.setFocusable(true); // With this, this GamePanel can be "focused" to receive key input.
    }

    @Override
    public void run() {

    }
}
