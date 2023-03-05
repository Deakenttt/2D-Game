package utility;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @Des KeyHandler Class for handling the key input.
 */
public class KeyHandler implements KeyListener {

    public GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode(); // Returns the integer keyCode associated with the key in this event.

        if (gp.gameState == gp.titleState) {

            if (code == KeyEvent.VK_W) {
                gp.ui.menuCursor--;
                if (gp.ui.menuCursor < 0)
                    gp.ui.menuCursor = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.menuCursor++;
                if (gp.ui.menuCursor > 2)
                    gp.ui.menuCursor = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.menuCursor == 0) {
                    gp.gameState = gp.gamePlay;
                }
                if (gp.ui.menuCursor == 2) {
                    System.exit(0);
                }
            }
        } else if (gp.gameState == gp.gamePlay) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.gamePause;
            }
        } else if (gp.gameState == gp.gamePause) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.gamePlay;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the integer keyCode associated with the key in this event.

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}

