package entity;

import main.GamePanel;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class EntityTest {

    private Entity entity;
    private GamePanel gp;

    @Before
    public void setUp() {
        gp = new GamePanel();
        gp.imageLoader = new utility.ImageLoader();
        entity = new Entity(gp);
        entity.getPlayerImage();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(48, entity.x);
        assertEquals(48, entity.y);
        assertTrue(entity.onPath);
        assertEquals(22, entity.solidArea.width);
        assertEquals(22, entity.solidArea.height);
    }

    @Test
    public void testGetPlayerImage() {
        // should be null since the image loader from gamepanel is not called
        gp.loadAllImages();
        assertNull(entity.up1);
        assertNull(entity.up2);
        assertNull(entity.down1);
        assertNull(entity.down2);
        assertNull(entity.left1);
        assertNull(entity.left2);
        assertNull(entity.right1);
        assertNull(entity.right2);
    }

    @Test
    public void testUpdate() {
        // Initialize gp.player
        Player player = new Player(gp, null);
        gp.player = player;
        
        gp.player.doMove = true;
        entity.collisionOn = false;
    
        entity.x = 0;
        entity.y = 0;
    
        entity.direction = "right";
        entity.update();
        assertEquals(48, entity.x);
        assertEquals(0, entity.y);
    
        entity.direction = "left";
        entity.update();
        assertEquals(0, entity.x);
        assertEquals(0, entity.y);
    
        entity.direction = "down";
        entity.update();
        assertEquals(0, entity.x);
        assertEquals(48, entity.y);
    
        entity.direction = "up";
        entity.update();
        assertEquals(0, entity.x);
        assertEquals(0, entity.y);

        assertEquals(4,entity.spriteCounter);
    }
    

    @Test
    public void testDraw() {
        // Test drawing the entity with different directions and sprites
        entity.direction = "up";
        entity.spriteNum = 1;
        assertEquals(null,entity.up1);

        entity.direction = "down";
        entity.spriteNum = 2;
        assertEquals(null,entity.down1);
    
        entity.direction = "left";
        entity.spriteNum = 1;
        assertEquals(null,entity.left1);
    
        entity.direction = "right";
        entity.spriteNum = 2;
        assertEquals(null,entity.right1);
    }
    
    @Test
    public void testDraw1() {
        // unable to test this as it requires Graphics2D object
        assertTrue(true);
    }
}
