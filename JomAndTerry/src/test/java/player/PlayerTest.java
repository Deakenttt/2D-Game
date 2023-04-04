package player;

import entity.Enemy;
import entity.Player;
import main.GamePanel;
import org.junit.Test;
import utility.KeyHandler;

import static org.junit.Assert.*;

public class PlayerTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyHandler;
    Player player = new Player(gp, keyHandler);
    Enemy enemy = new Enemy(gp);

    @Test
    public void testSetAction(){
        // user doesn't press any key OR press invalid key on keyboard
        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.leftPressed = false;
        keyHandler.rightPressed = false;
        player.setAction();
        assertFalse(player.doMove);
        // user press up key on keyboard
        keyHandler.upPressed = true;
        keyHandler.downPressed = false;
        keyHandler.leftPressed = false;
        keyHandler.rightPressed = false;
        player.setAction();
        assertTrue(player.doMove);
        assertEquals("up", player.direction);
        // user press down key on keyboard
        keyHandler.upPressed = false;
        keyHandler.downPressed = true;
        keyHandler.leftPressed = false;
        keyHandler.rightPressed = false;
        player.setAction();
        assertTrue(player.doMove);
        assertEquals("down", player.direction);
        // user press left key on keyboard
        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.leftPressed = true;
        keyHandler.rightPressed = false;
        player.setAction();
        assertTrue(player.doMove);
        assertEquals("left", player.direction);
        // user press right key on keyboard
        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.leftPressed = false;
        keyHandler.rightPressed = true;
        player.setAction();
        assertTrue(player.doMove);
        assertEquals("right", player.direction);
    }
    @Test
    public void testUpdate(){
        // there is an obstacle player can't go through
        player.x = 7 * gp.tileSize;
        player.y = 2 * gp.tileSize;
        int oldPlayerX = 7 * gp.tileSize;
        int oldPlayerY = 2 * gp.tileSize;
        keyHandler.upPressed = true;
        player.update();
        assertEquals(player.x, oldPlayerX);
        assertEquals(player.y, oldPlayerY);
        // there is not an obstacle player can't go through
        player.x = 7 * gp.tileSize;
        player.y = 3 * gp.tileSize;
        oldPlayerX = 7 * gp.tileSize;
        oldPlayerY = 3 * gp.tileSize;
        keyHandler.upPressed = true;
        player.update();
        assertNotEquals(player.x, oldPlayerX);
        assertNotEquals(player.y, oldPlayerY);
        // there is a cat in front of the player
        player.x = 7 * gp.tileSize;
        player.y = 3 * gp.tileSize;
        oldPlayerX = 7 * gp.tileSize;
        oldPlayerY = 3 * gp.tileSize;
        enemy.x = 7 * gp.tileSize;
        enemy.y = 2 * gp.tileSize;
        keyHandler.upPressed = true;
        player.update();
        assertEquals(player.x, oldPlayerX);
        assertEquals(player.y, oldPlayerY);
    }

    @Test
    public void testPickUpObjectAndPickUpObjectEffect(){
        // object is cheese
        int object = 0;
        String objectName = gp.obj[object].name;
        player.pickUpObject(object);
        int oldScore = player.scoreCount;
        int newScore = oldScore + 1;
        player.pickupObjectEffect(objectName, "You got a cheese!", object,  4);
        assertEquals(objectName, "cheese");
        assertEquals(newScore, player.scoreCount);
        assertNull(gp.obj[object]);


        // object is steak
        object = 6;
        objectName = gp.obj[object].name;
        player.pickUpObject(object);
        oldScore = player.scoreCount;
        newScore = oldScore + 5;
        player.pickupObjectEffect(objectName, "You got a cheese!", object,  4);
        assertEquals(objectName, "steak");
        assertEquals(newScore, player.scoreCount);
        assertNull(gp.obj[object]);
        // object is trap
        object = 7;
        objectName = gp.obj[object].name;
        player.pickUpObject(object);
        oldScore = player.scoreCount;
        newScore = oldScore - 5;
        player.pickupObjectEffect(objectName, "You got a cheese!", object,  4);
        assertEquals(objectName, "trap");
        assertEquals(newScore, player.scoreCount);
        assertNull(gp.obj[object]);
    }
}
