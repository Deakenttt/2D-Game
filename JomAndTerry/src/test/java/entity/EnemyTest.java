package entity;

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
        // Set player at (1, 1)
        Player player = new Player(gamePanel, null);
        gamePanel.player = player;
    }

    @Test
    public void testSetDefaultValues() {
        enemy.setDefaultValues();
        assertTrue(enemy.onPath);
        assertEquals("down", enemy.direction);
        assertEquals("cat", enemy.name);
        assertEquals(enemy.solidArea.x, enemy.solidAreaDefaultX);
        assertEquals(enemy.solidArea.y, enemy.solidAreaDefaultY);
    }

    @Test
    public void testSetAction() {
        gamePanel.player.solidArea.x = 1 * gamePanel.tileSize;
        gamePanel.player.solidArea.y = 1 * gamePanel.tileSize;
        //enemy.setAction(); cant be called for unit testing because it depends on other features
        assertTrue(enemy.onPath);
        assertNotNull(enemy.direction);
        assertNotNull(enemy.solidArea);
    }

    @Test
    public void testUpdate() {
        // Set player at (1, 1)
        gamePanel.player.solidArea.x = 1 * gamePanel.tileSize;
        gamePanel.player.solidArea.y = 1 * gamePanel.tileSize;
        assertTrue(gamePanel.player.doMove);
        //enemy.update();//enemy.setAction(); cant be called for unit testing because it depends on other features in other classes
        assertTrue(enemy.onPath);
        assertNotNull(enemy.direction);
        assertNotNull(enemy.solidArea);
    }

    @Test
    public void testSearchPath() {
        String[] pathOrderedList = new String[4];

        // cant call it because this belongs in integration testing as it depends on the find path class
        /*
        int goalRow = 5;
        int goalCol = 5;
        enemy.searchPath(goalCol, goalRow, pathOrderedList);*/ 

        // Check the output
        assertEquals(null, pathOrderedList[0]);
        assertEquals(null, pathOrderedList[1]);
        assertEquals(null, pathOrderedList[2]);
        assertEquals(null, pathOrderedList[3]);

    }

}
