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
    public static final int MAIN_TITLE = 0;
    public static final int INSTR_TITLE = 1;
    public static final int LEVEL_TITLE = 2;

    public static final int GAME_PLAY =1;

    public static final int LEVEL_BUTTON = 0;
    public static final int RETRY_BUTTON = 0;
    public static final int INSTR_BUTTON = 1;
    public static final int BACK_BUTTON = 1;
    public static final int QUIT_BUTTON = 2;


    /**
    * Constructs a new KeyHandler object.
    *
    * @param gp the GamePanel to handle key input for
    */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Invoked when a key has been typed.
     * Does nothing in this implementation.
     *
     * @param e the KeyEvent that occurred
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been pressed.
     * Handles key input for the GamePanel depending on the current game state.
     *
     * @param e the KeyEvent that occurred
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode(); // Returns the integer keyCode associated with the key in this event.
        switch(gp.gameState){
            case(GamePanel.TITLE_STATE):
                handleTitleStateInput(keyCode);
                break;
            case(GamePanel.PLAY_STATE):
                handlePlayStateInput(keyCode);
                break;
            case(GamePanel.LOSE_STATE):
                handleOverStateInput(keyCode);
                break;
            case(GamePanel.WIN_STATE):
                handleWinStateInput(keyCode);
                break;
            case(GamePanel.PAUSE_STATE):
                handleOverStateInput(keyCode);
                break;
            default:
                break;
        }
        if (keyCode == KeyEvent.VK_ENTER){
            gp.ui.commandNum = 0;
        }
    }
    /**
     * Invoked when a key has been released.
     * Sets the corresponding boolean value to false depending on which key was released.
     *
     * @param e the KeyEvent that occurred
     */
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
    /**
     * Handles input for the title screen.
     * Changes the titleScreenState depending on the user's input.
     *
     * @param keyCode
    */
    private void handleTitleStateInput(int keyCode) {
        switch(gp.ui.titleScreenState){
            case(MAIN_TITLE):
                switch(keyCode){
                    case(KeyEvent.VK_W):
                        gp.ui.commandNum--;
                        if (gp.ui.commandNum < 0)
                            gp.ui.commandNum = 2;
                        break;

                    case(KeyEvent.VK_S):
                        gp.ui.commandNum++;
                        if (gp.ui.commandNum > 2) 
                            gp.ui.commandNum = 0;
                        break;

                    case(KeyEvent.VK_ENTER):
                        switch(gp.ui.commandNum){
                            case LEVEL_BUTTON: // 
                                gp.titleLevel();
                                break;

                            case INSTR_BUTTON:
                                gp.titleInstruction();
                                break;

                            case QUIT_BUTTON:
                                System.exit(0);
                                break;

                            default:
                                break;
                        }
                    }
            break; // End of Main Title
            
            case INSTR_TITLE:
                System.out.println(gp.ui.commandNum);

                switch(keyCode){
                    case KeyEvent.VK_W:
                        gp.ui.commandNum--;
                        if (gp.ui.commandNum < 0) 
                            gp.ui.commandNum = 1;
                        break;

                    case KeyEvent.VK_S:
                        gp.ui.commandNum++;
                        if (gp.ui.commandNum > 1) 
                            gp.ui.commandNum = 0;
                        break;

                    case KeyEvent.VK_ENTER:
                        System.out.println(gp.ui.commandNum);
                        if (gp.ui.commandNum == 0)
                            gp.titleLevel();

                        if (gp.ui.commandNum == 1)
                            gp.titleMain();
                        break;            
                    }
                break;

            case LEVEL_TITLE:
                switch(keyCode){
                    case(KeyEvent.VK_W): 
                        gp.ui.commandNum--;
                        if (gp.ui.commandNum < 0) 
                            gp.ui.commandNum = 2;
                        break;
                
                    case(KeyEvent.VK_S):
                        gp.ui.commandNum++;
                        if (gp.ui.commandNum > 2)
                            gp.ui.commandNum = 0;
                        break;
                
                    case KeyEvent.VK_ENTER:
                        if (gp.ui.commandNum == 2) // go back to title page
                            gp.titleMain();
                            //this displays the game board and starts the game
                        else
                            gp.retry((gp.ui.commandNum + 1));                                           
                        break;

                    default: break;
            }
            default: break;

        }

    }

/**
 * Handles input for the win and game over screens.
 * Changes the gameState depending on the user's input.
 *
 * @param keyCode
 * @param retryAction the action to perform when the user selects "retry"
 */
private void handleEndStateInput(int keyCode, Runnable retryAction) {
    switch(keyCode) {
        case KeyEvent.VK_W:
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) 
                gp.ui.commandNum = 3;
            break;

        case KeyEvent.VK_S:
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 3) 
                gp.ui.commandNum = 0;
            break;

        case KeyEvent.VK_ENTER:
            switch(gp.ui.commandNum){
                case 0: // retry
                    retryAction.run();
                    break;
                case 1: //quit
                    System.exit(0);
                    break;         
                case 2: // back
                    gp.titleMain();
                    gp.ui.titleScreenState = 0;
                    break;
                case 3: // choose level
                    gp.titleLevel();
                    gp.ui.titleScreenState = LEVEL_TITLE;
                    break;
            }
            break;
        default: break;
    }
}

/**
 * Handles input for the win screen.
 * Changes the gameState depending on the user's input.
 *
 * @param keyCode
 */
private void handleWinStateInput(int keyCode) {
    handleEndStateInput(keyCode, () -> gp.retry(gp.levelState));
}

/**
 * Handles input for the game over screen.
 * Changes the gameState depending on the user's input.
 *
 * @param keyCode
 */
private void handleOverStateInput(int keyCode) {
    handleEndStateInput(keyCode, () -> gp.retry(gp.levelState));
}

    /**
    * Handles input for the play screen.
    * provides the input for gameplay based on the user's input.
    *
    * @param keyCode
    */

    private void handlePlayStateInput(int keyCode) {
        switch(keyCode){
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            default: break;
        }
    }
    public void setTitleStateInputTesting(int e){
        handleTitleStateInput(e);
    }

    public void setWinStateInputTesting(int e){
        handleWinStateInput(e);
    }

    public void setOverStateInputTesting(int e){
        handleOverStateInput(e);
    }


}

