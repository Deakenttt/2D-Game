package entity;

import entity.Enemy;
import main.GamePanel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {

    private GamePanel gamePanel;
    private Enemy enemy;

    @Before
    public void setUp() {
        gamePanel = new GamePanel();
        enemy = new Enemy(gamePanel);
    }

    @Test
    public void testSetDefaultValues() {
        enemy.setDefaultValues();
        assertTrue(enemy.onPath);
        assertEquals("down", enemy.direction);
        assertEquals("cat", enemy.name);
        assertEquals(enemy.x + enemy.solidArea.width, enemy.solidAreaDefaultX);
        assertEquals(enemy.y + enemy.solidArea.height, enemy.solidAreaDefaultY);
    }

    @Test
    public void testSetAction() {
        // Set player at (1, 1)
        gamePanel.getPlayer().getSolidArea().x = 1 * gamePanel.getTileSize();
        gamePanel.getPlayer().getSolidArea().y = 1 * gamePanel.getTileSize();

        enemy.setAction();
        assertFalse(enemy.isOnPath());
        assertNotNull(enemy.getDirection());
        assertNotNull(enemy.getSolidArea());
    }

    @Test
    public void testUpdate() {
        // Set player at (1, 1)
        gamePanel.getPlayer().getSolidArea().x = 1 * gamePanel.getTileSize();
        gamePanel.getPlayer().getSolidArea().y = 1 * gamePanel.getTileSize();

        enemy.update();
        assertFalse(enemy.isOnPath());
        assertNotNull(enemy.getDirection());
        assertNotNull(enemy.getSolidArea());
    }

    @Test
    public void testSearchPath() {
        // Set player at (1, 1)
        int goalCol = 1;
        int goalRow = 1;

        String[] pathOrderedList = new String[4];
        enemy.searchPath(goalCol, goalRow, pathOrderedList);

        assertNotNull(pathOrderedList[0]);
        assertNotNull(pathOrderedList[1]);
        assertNotNull(pathOrderedList[2]);
        assertNotNull(pathOrderedList[3]);
    }
}
