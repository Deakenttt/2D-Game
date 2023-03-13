package main;


import ai.FindPath;
import entity.Player;
import entity.SmCat;
import object.AssetSetter;
import object.SuperObject;
import tile.TileManager;
import utility.CollisionChecker;
import entity.Enemy;
import utility.KeyHandler;

import javax.swing.*;
import java.awt.*;

import static utility.SizeNumber.*;
import static utility.SizeNumber.MAX_SCREEN_ROW;

/**
 * @Des This is a GamePanel class.
 */
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTING
    final int originalTileSize = ORIGINAL_TILE_SIZE; // 16x16 tile
    final int scale = SCALE;
    public final int maxScreenCol = MAX_MAP_COL;
    public final int maxScreenRow = MAX_SCREEN_ROW;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int screenWidth = tileSize * MAX_SCREEN_COL; // 48 * 20 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 48 * 16 pixels

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int gamePlay = 1;
    public final int gamePause = 2;
    public final int gameOverState = 3;
    public final int gameWinState = 4;

    public UI ui = new UI (this);

    // FPS
    int FPS = 60;

    public FindPath findPath = new FindPath(this);
    Thread gameThread; // To enable start and stop, needs involve Runnable interface.
    KeyHandler keyHandler = new KeyHandler(this); // Key handler class.

    public Player player = new Player(this, keyHandler); // Initiate a Player object.
    public Enemy enemy[] = new Enemy[3];
    public SmCat smartCat = new SmCat(this);
    public SuperObject[] obj = new SuperObject[20]; // 20 slots for object, can replace the content during the game.

    public TileManager tileManager = new TileManager(this); // Initiate tileManger object.
    public CollisionChecker collisionChecker = new CollisionChecker(this); // Initiate a CollisionChecker object.
    public AssetSetter assetSetter = new AssetSetter(this); // Initiate AssetSetter object.

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        Rectangle rect = new Rectangle(1, 1, 1, screenHeight);
        this.scrollRectToVisible(rect);
        this.setBackground(Color.black);

        this.addKeyListener(keyHandler); // So this GamePanel can recognize key input.
        this.setFocusable(true); // With this, this main.GamePanel can be "focused" to receive key input.
    }

    // Method of setting up object placement.
    public void setUpGame() {
        //assetSetter.setEnemy();
        assetSetter.setObject();
        gameState = titleState;
    }

    // Method of Starting Game Thread.
    public void startGameThread() {

        // passing GamePanel class to this thread's constructor.
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // System.nanoTime(); // Returns the current value of the running Java Virtual Machine's high-resolution time source in nanoseconds.
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval; // The allocated time for single loop is 0.01666 seconds.

        // Game Loop
        while (gameThread != null) {

            // 1. UPDATE: update info such as character position.
            update();

            // 2. DRAW: draw the screen with the updated information.
            repaint(); // is the way to call paintComponent method.

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // this returns how much time remaining until the nextDrawTime.
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (gameState == gamePlay) {

            player.update();
            smartCat.update();
        }
    }

    // This is built-in java method on JPanel.
    // Graphics is a class that has many functions to draw objects on the screen, just like pencil or paintbrush.
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // using the parent class JPanel's method.

        // convert Graphics to Graphics2D class extends the Graphics class to provide more sophisticated control over
        // geometry, coordinate transformations, color management, and text layout.
        Graphics2D g2 = (Graphics2D) g;

        // Game state is title state.
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // Game state is gamePlay or gamePause.
        else if (gameState == gamePlay || gameState == gamePause
                || gameState == gameOverState || gameState == gameWinState) {

            tileManager.draw(g2);
//            System.out.println("player ");
            player.draw(g2);

            smartCat.draw(g2);
            // Enemies
            for (int i = 0; i < enemy.length; i++) {
                if (enemy[i] != null) {
                    System.out.println("enemy " + i);
                    enemy[i].draw(g2);
                }
            }

            // Draw objects
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // Draw UI
            ui.draw(g2);
        }

        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using.
    }

}
