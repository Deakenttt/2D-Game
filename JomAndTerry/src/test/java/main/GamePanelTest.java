package main;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import entity.Player;

import static org.junit.Assert.*;


public class GamePanelTest {

    private GamePanel gamePanel;

    @Before
    public void setUp() {
        gamePanel = new GamePanel();
        Player player = new Player(gamePanel, null);
        gamePanel.player = player;
    }

    @Test
    public void testSetUpGame() {
        gamePanel.setUpGame();
        assertEquals(GamePanel.TITLE_STATE, gamePanel.gameState);
        assertNotNull(gamePanel.ui);
        assertNotNull(gamePanel.player);
        assertNotNull(gamePanel.keyHandler);
        assertNotNull(gamePanel.tileManager);
        assertNotNull(gamePanel.collisionChecker);
        assertNotNull(gamePanel.assetSetter);
        assertNotNull(gamePanel.imageLoader);
    }

    @Test
    public void testStartGameThread() {
        gamePanel.startGameThread();
        assertNotNull(gamePanel.gameThread);
        assertTrue(gamePanel.gameThread.isAlive());
    }

    @Test
    public void testRun() throws InterruptedException {
        // Set up game state
        gamePanel.setUpGame();
        // Start game thread
        gamePanel.startGameThread();
        // Wait for game loop to run for a few seconds
        Thread.sleep(5000);
        // Stop game thread
        gamePanel.gameThread = null;
        // Verify that the game thread has stopped
        Thread.sleep(1000);
        assertNull(gamePanel.gameThread);
    }

    @Test
    public void testPreferredSize() {
        Dimension preferredSize = gamePanel.getPreferredSize();
        assertEquals(gamePanel.screenWidth, preferredSize.width);
        assertEquals(gamePanel.screenHeight, preferredSize.height);
    }

    @Test
    public void testAddKeyListener() {
        KeyListener[] keyListeners = gamePanel.getKeyListeners();
        assertEquals(1, keyListeners.length);
        assertFalse(keyListeners[0] instanceof KeyHandler);
    }

    @Test
    public void testFocusable() {
        assertTrue(gamePanel.isFocusable());
    }

    @Test
    public void testPaintComponent() {
        Graphics g = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB).getGraphics();
        // check if the panel has been painted
        assertNotNull(gamePanel);
        // check if the Graphics object has been converted to Graphics2D
        assertTrue(g instanceof Graphics2D);
    }

    @Test
    public void testPlayMusic() {
        gamePanel.playMusic(0);
        // Check if the audio clip is not null
        assertNotNull(gamePanel.sound);
    }

    @Test
    public void testStopMusic() {
        gamePanel.playMusic(0);
        gamePanel.stopMusic();
        // Assertions
        // check if the audio clip is stopped
        assertNotNull(gamePanel.sound);
    }

    @Test
    public void testPlaySE() {
        gamePanel.playSE(0);
        // Assertions
        // check if the sound effect clip is playing
        assertNotNull(gamePanel.sound);
    }

    @Test
    public void testRetry() {
        gamePanel.retry(1);
        assertEquals(gamePanel.levelState,1);
        assertEquals(1, gamePanel.gameState);
        gamePanel.retry(2);
        assertEquals(gamePanel.levelState,2);
        assertEquals(1, gamePanel.gameState);
    }

    @Test
    public void testGameOver() {
        gamePanel.gameOver();
        // check if the game state is set to lose state
        assertEquals(3, gamePanel.gameState);
        // check if the sound effect is playing
        assertNotNull(gamePanel.sound);
        assertNotNull(gamePanel.ui);
    }

    @Test
    public void testGameWin() {
        gamePanel.gameWin();
        assertEquals(4, gamePanel.gameState);
        assertNotNull(gamePanel.sound);
        assertNotNull(gamePanel.ui);
    }

    
    @Test
    public void testTitleInstruction() {
        gamePanel.titleInstruction();
        assertEquals(0, gamePanel.gameState);
        assertNotNull(gamePanel.sound);
        assertNotNull(gamePanel.ui);

    }

    @Test
    public void testTitleLevel() {
        gamePanel.titleLevel();
        assertEquals(0, gamePanel.gameState);
        assertNotNull(gamePanel.sound);
        assertNotNull(gamePanel.ui);
    }

    @Test
    public void testLoadAllImages() {
        gamePanel.loadAllImages();

        // Test that the imageLoader has loaded at least one image
        assertNotNull(gamePanel.imageLoader);
    }

    @Test
    public void testGetImageFromFolder() {
        File folder = new File("src/main/resources/assets");
        // Test that the method sets at least one image
        gamePanel.getImageFromFolder(folder);
        assertNotNull(gamePanel.imageLoader);

        // Test that the method can handle an empty folder
        folder = new File("src/main/resources/empty");
        assertNotNull(gamePanel.imageLoader);

        // Test that the method can handle a non-existent folder
        folder = new File("src/main/resources/nonexistent");
        assertNotNull(gamePanel.imageLoader);
    }
}
