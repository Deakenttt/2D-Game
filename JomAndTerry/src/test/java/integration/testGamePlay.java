package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import main.GamePanel;
import object.Steak;
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
        assertEquals(1, gp.gameState);
    }
    @Test
    public void testMouseCatCollision(){
        // Simulate cat captures mouse with 0 points
        gp.retry(1);
        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(0, gp.player.cheeseCount);
        assertEquals(0, gp.player.scoreCount);

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
        for (int i = 0; i < 6; i++)
            gp.player.handleScore("cheese");
        
        gp.enemy[0].setEntityXY(48*2, 48*2);
        gp.player.setEntityXY(48*2, 48);
        moveRight();
        assertEquals(3, gp.gameState);
        assertEquals(6, gp.player.cheeseCount);
        assertEquals(6, gp.player.scoreCount);

        // Simulate cat captures mouse with 6 cheeses + steak
        gp.retry(1);
        for (int i = 0; i < 6; i++)
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
        // Simulate cat vs cat collision to ensure cats don't take up the same space
        gp.retry(1);
        gp.enemy[0].setEntityXY(48*4, 48);
        gp.enemy[1].setEntityXY(48*4, 48*3);
        gp.player.setEntityXY(48*3, 48*2);
        moveUp();
        assertEquals(1, gp.gameState);
        assertFalse(gp.enemy[0].collisionOn);

        // Simulate cat vs mouse vs cat collision to ensure only 1 cat moves to mouse space
        gp.retry(1);
        gp.enemy[0].setEntityXY(48*5, 48);
        gp.enemy[1].setEntityXY(48*4, 48*2);
        gp.player.setEntityXY(48*4, 48);
        moveUp();
        assertEquals(3, gp.gameState);
        assertFalse(gp.enemy[0].collisionOn);
    }

    @Test 
    public void testMouseCheeseCollision(){
        // collect all cheese and check counts and object null after each cheese collected
        gp.retry(1);
        gp.enemy[0] = null;
        gp.enemy[1] = null;
        gp.player.setEntityXY(0, 0);

        for(int i = 0; i < 6; i++){
            if (gp.obj[i].name == "cheese")
                gp.obj[i].setObject(0, 48);
            else
                break;
            moveDown();
            checkScore(i+1, 0, i+1, i);
            moveUp();
        }

        // collect a steak
        moveUp();
        gp.obj[6] = new Steak(gp);
        gp.obj[6].setObject(0,48);
        moveDown();
        checkScore(6, 5, 11, 6);

        // collect a trap
        moveUp();
        gp.obj[7].setObject(0,48);
        moveDown();
        checkScore(6, 5, 6, 7);

        // exit through hole
        winGame();
    }

    @Test 
    public void testMouseTrapCollision(){
        // collect a trap 
        gp.retry(2);
        gp.enemy[0] = null;
        gp.enemy[1] = null;
        gp.enemy[2] = null;

        gp.player.setEntityXY(0, 0);

        gp.obj[7].setObject(0,48);
        moveDown();
        System.out.println(gp.player.x);
        System.out.println(gp.player.y);
        gp.update();
        checkScore(0, 0, -5, 7);
        assertEquals(3, gp.gameState);

        // collect 5 cheeses, a trap, a steak, and the last cheese
        gp.retry(2);
        gp.enemy[0] = null;
        gp.enemy[1] = null;
        gp.enemy[2] = null;

        gp.player.setEntityXY(0, 0);

        for(int i = 0; i < 5; i++){
            gp.obj[i].setObject(0, 48);
            moveDown();
            checkScore(i+1, 0, i+1, i);
            moveUp();
        }

        gp.obj[7].setObject(0,48);
        moveDown();
        checkScore(5, 0, 0, 7);
        assertEquals(1, gp.gameState);

        // collect a steak
        moveUp();
        gp.obj[6] = new Steak(gp);
        gp.obj[6].setObject(0,48);
        moveDown();
        checkScore(5, 5, 5, 6);
        
        // grab the last cheese
        moveUp();
        gp.obj[5].setObject(0, 48);
        moveDown();
        checkScore(6, 5, 6, 6);
        winGame();
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
    public void winGame(){
        gp.player.setEntityXY(48*18, 48*14);
        moveRight();
        assertEquals(4, gp.gameState);
    }

    public void checkScore(int cheese, int steak, int score, int object){
        assertEquals(cheese, gp.player.cheeseCount);
        assertEquals(score, gp.player.scoreCount);
        assertEquals(steak, gp.player.steakCount);
        if(object != 999)
            assertNull(gp.obj[object]); 
    }

}
