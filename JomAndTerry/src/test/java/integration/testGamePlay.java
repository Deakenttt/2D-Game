package integration;

import static org.junit.Assert.assertEquals;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import main.GamePanel;
import ui.UI;
import utility.KeyHandler;

public class testGamePlay {
    GamePanel gp = new GamePanel();
    
    KeyHandler key = gp.getKeyHandler();
    UI ui = gp.ui;

    @Before
    public void setUp(){
        gp.setUpGame();
    }

    @Test
    public void testGameMovement() {
        gp.retry(1);

        // Simulate user input to move the character
        assertEquals(0, gp.player.x);
        assertEquals(48, gp.player.y);
        moveUp();
        stop();
        moveRight();
        assertEquals(48, gp.player.x);
        assertEquals(48, gp.player.y);
        moveDown();
        assertEquals(48, gp.player.x); 
        assertEquals(48*2, gp.player.y);                       
        moveUp();
        assertEquals(48, gp.player.x);   
        assertEquals(48, gp.player.y); 
        moveLeft();
        assertEquals(0, gp.player.x); 
        assertEquals(48, gp.player.y);  
    }

    @Test
    public void testMouseWallCollision(){
        gp.retry(1);

        // Simulate collision with a wall;
        moveRight();
        moveDown();
        assertEquals(48, gp.player.x); 
        assertEquals(48*2, gp.player.y);               
        moveLeft();
        assertEquals(48, gp.player.x); 
        assertEquals(48*2, gp.player.y);               
        assertEquals(2, gp.gameState);



        // Verify that the game is over
        // assertTrue(game.isOver());
        
        // Verify that the game score is correct
        // assertEquals(10, game.getScore());
    }
    @Test
    public void testMouseCatCollision(){
        // Simulate cat captures mouse with 0 points
        gp.retry(1);
        gp.player.captured(0);
        assertEquals(3, gp.gameState);

        // Simulate cat captures mouse with 1 point
        gp.retry(1);
        gp.player.handleScore("cheese");
        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(1, gp.player.cheeseCount);
        assertEquals(1, gp.player.scoreCount);

        // Simulate cat captures mouse with 6 cheeses
        gp.retry(1);
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(6, gp.player.cheeseCount);
        assertEquals(6, gp.player.scoreCount);

        // Simulate cat captures mouse with 6 cheeses + steak
        gp.retry(1);
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("cheese");
        gp.player.handleScore("steak");

        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(6, gp.player.cheeseCount);
        assertEquals(11, gp.player.scoreCount);

        gp.retry(1);

        // Simulate cat captures mouse with 1 cheese + steak
        gp.player.handleScore("cheese");
        gp.player.handleScore("steak");

        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(1, gp.player.cheeseCount);
        assertEquals(6, gp.player.scoreCount);
    
    }

    @Test
    public void testCatCatCollision(){

    }

    @Test
    public void testMouseTrapCollsion(){

    }

    @Test
    public void testMouseCheeseCollision(){

    }

    @Test
    public void testCatTrapCollision(){

    }
    public void stop(){
        key.upPressed = false;
        key.downPressed = false;
        key.leftPressed = false;
        key.rightPressed = false;
    }

    public void moveUp(){
        gp.player.setKeyHoldTimer(-1);
        key.upPressed = true;
        gp.update();
        stop();
    }
    public void moveDown(){
        gp.player.setKeyHoldTimer(-1);
        key.downPressed = true;
        gp.update();
        stop();
    }
    public void moveRight(){
        gp.player.setKeyHoldTimer(-1);
        key.rightPressed = true;
        gp.update();
        stop();
    }
    public void moveLeft(){
        gp.player.setKeyHoldTimer(-1);
        key.leftPressed = true;
        gp.update();
        stop();
    }





}
