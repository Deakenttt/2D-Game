package main;

import ai.FindPath;
import entity.Player;
import entity.Enemy;
import object.AssetSetter;
import object.SuperObject;
import tile.TileManager;
import utility.CollisionChecker;
import utility.ImageLoader;
import utility.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static utility.SizeNumber.*;
import static utility.SizeNumber.MAX_SCREEN_ROW;

/**
 * The GamePanel class is extends from the JPanel Object and implement with the Runnable interface, to handle player object,
 * enemies objects and the UI elements, and display them on the panel.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen setting
    final int originalTileSize = ORIGINAL_TILE_SIZE; // 16x16 tile
    final int scale = SCALE;
    public final int maxScreenCol = MAX_MAP_COL;
    public final int maxScreenRow = MAX_SCREEN_ROW;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int screenWidth = tileSize * MAX_SCREEN_COL; // 48 * 20 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 48 * 16 pixels

    // Game state
    public int gameState;
    public static final int titleState = 0;
    public static final int gamePlay = 1;
    public static final int gamePause = 2;
    public static final int gameOverState = 3;
    public static final int gameWinState = 4;
    public int levelState = 1;
    public SuperObject[][] objectsMap;


    // UI and sound
    public UI ui;
    public UI playUI;
    public GameTitleUI titleUI;
    public GameOverUI overUI;
    public GameWinUI winUI;
    Sound sound = new Sound();

    // Fps
    int FPS = 60;
    public boolean exitcondition = true;

    Thread gameThread; // To enable start and stop, needs involve Runnable interface.
    KeyHandler keyHandler = new KeyHandler(this); // Key handler class.

    public FindPath findPath = new FindPath(this);
    public Player player;
    public Enemy[] enemy = new Enemy[3];
    public SuperObject[] obj = new SuperObject[20]; // 20 slots for object, can replace the content during the game.

    public TileManager tileManager = new TileManager(this); // Initiate tileManger object.
    public CollisionChecker collisionChecker = new CollisionChecker(this); // Initiate a CollisionChecker object.
    public AssetSetter assetSetter = new AssetSetter(this); // Initiate AssetSetter object.
    public ImageLoader imageLoader = new ImageLoader();


    public GamePanel() {
        loadAllImages();
        ui = titleUI;
        playUI = new UI(this);
        titleUI = new GameTitleUI(this);
        overUI = new GameOverUI(this);
        winUI = new GameWinUI(this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        Rectangle rect = new Rectangle(1, 1, 1, screenHeight);
        this.scrollRectToVisible(rect);
        this.setBackground(Color.black);

        this.addKeyListener(keyHandler); // So this GamePanel can recognize key input.
        this.setFocusable(true); // With this, this main.GamePanel can be "focused" to receive key input.
    }

    /**
     * Method of setting up object placement.
     */
    public void setUpGame() {
        gameState = titleState;
        ui = titleUI;
        playMusic(0);
        player = new Player(this, keyHandler); // Initiate a Player object.
    }

    /**
     * Method of Starting Game Thread.
     */
    public void startGameThread() {

        gameThread = new Thread(this); // passing GamePanel class to this thread's constructor.
        gameThread.start();
    }

    /**
     * Call the run() to start a new thread, draw all the objects and update them.
     */
    @Override
    public void run() {

        // System.nanoTime(); // Returns the current value of the running Java Virtual Machine's high-resolution time source in nanoseconds.
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval; // The allocated time for single loop is 0.01666 seconds.

        // Game Loop
        while (gameThread != null) {

            // 1. UPDATE: update info such as character position.
            if (gameState == gamePlay) {
                update();
            }

            // 2. DRAW: draw the screen with the updated information.
            repaint(); // is the way to call paintComponent method.

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // This returns how much time remaining until the nextDrawTime.
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

    /**
     * Method of update() is updating the player object and enemies objects.
     */
    public void update() {
       
        player.update();
        if (player.cheeseCount >= 6) {
            assetSetter.exitOpen();
        }

        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].update();
            }
        }
    }

    /**
     * This is a built-in java method on JPanel, draw all the objects such as, player, enemy.
     *
     * @param g Graphics is a class that has many functions to draw objects on the screen, just like pencil or paintbrush.
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // using the parent class JPanel's method.

        // convert Graphics to Graphics2D class extends the Graphics class to provide more sophisticated control over
        // geometry, coordinate transformations, color management, and text layout.
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);

        // When the game is playing, pause, over, or win.
        if (gameState != titleState) {
            tileManager.draw(g2);
            player.draw(g2);

            // Draw objects
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // Draw Enemies
            for (int i = 0; i < enemy.length; i++) {
                if (enemy[i] != null) {
                    enemy[i].draw(g2);
                }
            }
        }
        
        ui.draw(g2);
        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using.
    }

    /**
     * Method for adding the clip file and play it.
     *
     * @param i The index for accessing different clips on the array.
     */
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Method for stop the clip.
     */
    public void stopMusic() {
        sound.stop();
    }

    /**
     * Method for play the sound effect for once.
     *
     * @param i The index for accessing different clips on the array.
     */
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    /**
     * Method for triggering the retry button.
     *
     * @param level For selecting different map.
     */
    public void retry(int level) {
        levelState = level;
        tileManager.setUpMap();
        player.setDefaultValues();
        ui = playUI;
        ui.resetUI();

        if (levelState == 1){
            enemy = new Enemy[2];
        }
        else if (levelState == 2){
            enemy = new Enemy[3];
        }
        assetSetter.setEnemy();
        assetSetter.setObject();

        gameState = gamePlay;
        ui = playUI;
    }

    public void gameOver(){
        gameState = 3;
        ui = overUI;
        ui.text = "You Lose!";
        playSE(2);
    }

    public void gameWin(){
        gameState = GamePanel.gameWinState;
        ui = overUI;
        ui.text = "You Win!";
        playSE(3);
    }

    public void titleMain(){
        gameState = 0;
        ui = titleUI;
        String subText = "You are the mouse (Terry). Collect 6 cheeses to unlock the exit door. " +
        "Avoid the smart cats (Joms; yes they're all named Jom). " +
        "Cheese = 1 point, Steak (bonus) = 5 points, mouse trap (punishment) = -5 points. " +
        "Negative points = Automatic loss. " +
        "Exit with max points and the fastest time, there is no time limit. Enjoy!";
    }
    public void titleLevel(){
        gameState = 0;
        ui = titleUI;

    }

    public void loadAllImages(){
        File folder;
        folder = new File("src/main/resources/assets");
        getImageFromFolder(folder);
    }

    public void getImageFromFolder(File folder){
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                imageLoader.setImage(file);
            } else if (file.isDirectory()) {
                getImageFromFolder(file);
            }
        }

    }
}
