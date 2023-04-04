package integration;

import static org.junit.Assert.assertEquals;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import main.GamePanel;
import ui.UI;
import utility.KeyHandler;

public class testMenuInteractions {
    GamePanel gp = new GamePanel();
    
    KeyHandler key = gp.getKeyHandler();
    UI ui = gp.ui;

    @Before
    public void setUp(){
        gp.setUpGame();
    }

    @Test
    public void testGameMenuStates(){
        // Check that game starts in title state
        assertEquals(0, gp.gameState); 
        // Check that game is in main title state
        assertEquals(0,  gp.ui.titleScreenState); 

        // Check that game is in instruction title state
        key.setTitleStateInputTesting(KeyEvent.VK_S);
        key.setTitleStateInputTesting(10);
        assertEquals(1,  gp.ui.titleScreenState); 

        // Check that game is back in main title state
        key.setTitleStateInputTesting(KeyEvent.VK_S);
        key.setTitleStateInputTesting(10);
        assertEquals(0,  gp.ui.titleScreenState); 

        // Check that game is in level title state
        key.setTitleStateInputTesting(KeyEvent.VK_W);
        key.setTitleStateInputTesting(10);
        assertEquals(2,  gp.ui.titleScreenState); 

        // Check that game is in level 2
        key.setTitleStateInputTesting(KeyEvent.VK_S);
        key.setTitleStateInputTesting(10);
        assertEquals(2,  gp.levelState); 

    }

}
