package ai;

import entity.Player;
import junit.framework.TestCase;
import main.GamePanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tile.TileManager;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class FindPathTest {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    public ArrayList<Node> pathList = new ArrayList<>();
    boolean goalReached;
    int step = 0;
    FindPath findPathTest;

    @Before
    public void setUp() throws Exception {
        this.gp = new GamePanel();
        this.findPathTest = gp.findPath;
        this.openList = findPathTest.openList;
        this.pathList = findPathTest.pathList;
        this.node = findPathTest.node;
        this.step = findPathTest.step;


        gp.setUpGame();
        gp.startGameThread();
    }

    @Test
    public void testInstantiateNode() {
        assertEquals(gp.maxScreenCol, this.node.length);
        System.out.println("InstantiateNode() test passed");
    }

    @Test
    public void testResetNode() {
        findPathTest.resetNode();
        assertTrue(this.openList.isEmpty());
        assertTrue(this.pathList.isEmpty());
        for (int i = 0; i < gp.maxScreenCol; i++) {
            for (int j = 0; j < gp.maxScreenRow; j++) {
                assertFalse(this.node[i][j].open);
                assertFalse(this.node[i][j].checked);
                assertFalse(this.node[i][j].solid);
            }
        }
        assertEquals(0, this.step);
        assertFalse(this.goalReached);
        System.out.println("ResetNode() test passed");
    }

    @Test
    public void testSetNode() {
        // testing data
        int goalCol = 1;
        int goalRow = 1;
        int startCol = 13;
        int startRow = 13;

        this.gp.retry(1);
        this.findPathTest.setNode(startCol, startRow, goalCol, goalRow);
        this.startNode = findPathTest.startNode;
        this.goalNode = findPathTest.goalNode;
        this.currentNode = findPathTest.currentNode;


        assertEquals(startCol, this.startNode.col);
        assertEquals(startRow, this.startNode.row);

        assertEquals(goalCol, this.goalNode.col);
        assertEquals(goalRow, this.goalNode.row);

        for (int i = 0; i < gp.maxScreenCol; i++) {
            for (int j = 0; j < gp.maxScreenRow; j++) {
                int tileNum = this.gp.tileManager.mapTileNum[i][j];
                if (this.gp.tileManager.tile[tileNum].collision)
                    assertTrue(node[i][j].solid);
            }
        }

        System.out.println("testSetNode() test passed");
    }

    @Test
    public void testGetCost() {
        // testing data
        int goalCol = 12;
        int goalRow = 12;
        int startCol = 1;
        int startRow = 1;

        this.gp.retry(1);
        this.findPathTest.setNode(startCol, startRow, goalCol, goalRow);
        this.startNode = findPathTest.startNode;
        this.goalNode = findPathTest.goalNode;
        this.currentNode = findPathTest.currentNode;

        this.findPathTest.getCost(currentNode);

        assertEquals(0, currentNode.gCost); // test the cost from currentNode to startNode.
        assertEquals(22, currentNode.hCost); // test the cost from currentNode to goalNode.

        System.out.println("testGetCost() test passed");
    }

    @Test
    public void testAStarSearch() {
        // testing data
        int goalCol = 0;
        int goalRow = 1;
        int startCol = 1;
        int startRow = 4;

        this.gp.retry(1);
        this.findPathTest.setNode(startCol, startRow, goalCol, goalRow);

        this.startNode = findPathTest.startNode;
        this.goalNode = findPathTest.goalNode;
        this.currentNode = findPathTest.currentNode;
        this.goalReached = findPathTest.goalReached;

        this.findPathTest.aStarSearch();

        this.openList = findPathTest.openList;
        this.pathList = findPathTest.pathList;
        this.step = findPathTest.step;

        this.startNode = findPathTest.startNode;
        this.goalNode = findPathTest.goalNode;
        this.currentNode = findPathTest.currentNode;
        this.goalReached = findPathTest.goalReached;


        // expected path
        int[][] expectedPath = {{2, 4}, {2, 3}, {2, 2}, {1, 2}, {1, 1}, {0, 1}};

        for (int i = 0; i < pathList.size(); i++) {
            assertEquals(expectedPath[i][0], pathList.get(i).col);
            assertEquals(expectedPath[i][1], pathList.get(i).row);
        }

        System.out.println("testAStarSearch() test passed");

    }

    @Test
    public void testOpenNode() {
    }

    @Test
    public void testTrackPath() {
    }
}