package utility;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import entity.Entity;
import main.GamePanel;

public class CollisionCheckerTest {

    private CollisionChecker cc;
    private Entity entity;


    @Before
    public void setUp() {
        GamePanel gp = new GamePanel();
        cc = new CollisionChecker(gp);
        entity = new Entity(gp);
        entity.solidArea = new Rectangle(0, 0, 32, 32);
        entity.speed = 2;
        entity.direction = "right";
    }

    @Test
    public void testCheckTile() {
        assertNotNull(entity.solidArea.x/entity.speed);
        assertEquals(entity.solidArea.x, cc.col);
        assertEquals(entity.solidArea.y, cc.row);
    }

    @Test
    public void testCheckObject() {
        assertEquals(999, cc.checkObject(entity.solidArea));
        assertFalse(entity.collisionOn);
    }

    @Test
    public void testCheckEntity() {
        assertEquals(entity.solidArea.x, cc.col);
        assertEquals(entity.solidArea.y, cc.row);
    }

    @Test
    public void testSimulateNode() {
        cc.simulateNode(entity);
        assertEquals(2, entity.solidArea.x);
        assertEquals(0, entity.solidArea.y);
        entity.direction = "down";
        cc.simulateNode(entity);
        assertEquals(2, entity.solidArea.x);
        assertEquals(2, entity.solidArea.y);
        entity.direction = "left";
        cc.simulateNode(entity);
        assertEquals(0, entity.solidArea.x);
        assertEquals(2, entity.solidArea.y);
        entity.direction = "up";
        cc.simulateNode(entity);
        assertEquals(0, entity.solidArea.x);
        assertEquals(0, entity.solidArea.y);
    }

}
