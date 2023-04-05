package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import main.GamePanel;
import ui.UI;
import utility.KeyHandler;

public class testMenuInteractions {
    GamePanel gp = new GamePanel();
    KeyHandler key;

    @Before
    public void setUp(){
        key = gp.getKeyHandler();
        UI ui = gp.ui;
        gp.setUpGame();
    }

    @Test
    public void testTitleMenuStates(){
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

    @Test 
    public void testLoseGameMenuStates(){
        // test retry
        gp.retry(1);
        assertEquals(1,  gp.gameState); 

        gp.player.captured(0);
        key.setOverStateInputTesting(10);
        assertEquals(1,  gp.gameState); 
        assertEquals(1, gp.levelState);

        // test change level
        gp.player.captured(0);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(KeyEvent.VK_S);

        key.setOverStateInputTesting(10);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(10);

        assertEquals(1,  gp.gameState); 
        assertEquals(2, gp.levelState);

        // test home screen
        gp.player.captured(0);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(10);
        assertEquals(0,  gp.gameState); 
        
        // test exit button
        key.setOverStateInputTesting(10);
        key.setOverStateInputTesting(10);
        assertEquals(1,  gp.gameState); 
        gp.player.captured(0);
        assertEquals(2,  gp.gameState);
        key.setOverStateInputTesting(KeyEvent.VK_S);
        key.setOverStateInputTesting(10);
        assertNull(gp);

        gp = new GamePanel();
    }

    @Test
    public void testWinGameMenuStates(){
        // test retry
        gp.retry(1);
        assertEquals(1,  gp.gameState); 
        gp.gameWin();
        key.setWinStateInputTesting(10);
        assertEquals(1,  gp.gameState); 
        assertEquals(1, gp.levelState);

        // test change level
        gp.gameWin();
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(10);
        key.setWinStateInputTesting(10);
        assertEquals(1,  gp.gameState); 
        assertEquals(1, gp.levelState);

        // test home screen
        gp.gameWin();
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(10);
        assertEquals(0,  gp.gameState); 
        
        // test exit button
        key.setWinStateInputTesting(10);
        key.setWinStateInputTesting(10);
        assertEquals(1,  gp.gameState); 
        gp.player.captured(0);
        assertEquals(2,  gp.gameState);
        key.setWinStateInputTesting(KeyEvent.VK_S);
        key.setWinStateInputTesting(10);
        assertNull(gp);

        gp = new GamePanel();
    }
}
