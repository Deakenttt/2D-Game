package main;

import entity.Player;
import object.AssetSetter;
import object.SuperObject;
import tile.TileManager;
import utility.CollisionChecker;
import entity.Enemy;
import entity.Entity;
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
    public final int maxScreenCol = MAX_SCREEN_COL;
    public final int maxScreenRow = MAX_SCREEN_ROW;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int screenWidth = tileSize * maxScreenCol; // 48 * 20 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 48 * 16 pixels

    // UI
    public UI ui = new UI(this);

    // FPS
    int FPS = 60;

    Thread gameThread; // To enable start and stop, needs involve Runnable interface.
    KeyHandler keyHandler = new KeyHandler(); // Key handler class.

    public Player player = new Player(this, keyHandler); // Initiate a Player object.
    public Enemy enemy[] = new Enemy[3];
    // = new Enemy(this); // Initiate a Player object.
    public TileManager tileManager = new TileManager(this); // Initiate tileManger object.

    public CollisionChecker collisionChecker = new CollisionChecker(this); // Initiate a CollisionChecker object.

    public AssetSetter assetSetter = new AssetSetter(this); // Initiate AssetSetter object.
    public SuperObject[] obj = new SuperObject[10]; // 10 slots for object, can replace the content during the game.


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        this.addKeyListener(keyHandler); // So this GamePanel can recognize key input.
        this.setFocusable(true); // With this, this main.GamePanel can be "focused" to receive key input.
    }

    // Method of setting up object placement.
    public void setUpGame() {
        assetSetter.setEnemy();
        assetSetter.setObject();
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
        player.update();
        System.out.println("updating player");
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                System.out.println("updating enemy " + i);

                enemy[i].update();
            }
        }
    }

    // This is built-in java method on JPanel.
    // Graphics is a class that has many functions to draw objects on the screen, just like pencil or paintbrush.
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // using the parent class JPanel's method.

        // convert Graphics to Graphics2D class extends the Graphics class to provide more sophisticated control over
        // geometry, coordinate transformations, color management, and text layout.
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        System.out.println("player ");

        player.draw(g2);

        // Enemies
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                System.out.println("enemy " + i);
                enemy[i].draw(g2);
            }
        }

        // DRAW OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // DRAW UI
        ui.draw(g2);

        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using.
    }

}
