package tile;

import main.GamePanel;
import utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @Des TileManager class for handling all different types of tile.
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;


    }

    public void setUpMap() {
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // mapTileNum will store all numbers in map.txt.

        getTileImage(); // Set each tiles bufferedImage.
        String fileName = "/assets/Map/map0%d.txt".formatted(gp.levelState);
        System.out.println(fileName);
        loadMap(fileName); // Load a map text into 2D array and draw the map.
        int tileNum = mapTileNum[0][0];
        tile[tileNum].collision = true;
    }

    /**
     * This is a method for calling the setup methods for all tiles.
     */
    public void getTileImage() {

        setup(0, "floor", false);
        setup(1, "wall", true);
        setup(2, "outer_wall", true);
        setup(3, "table", true);
        setup(4, "chair", true);
        setup(5, "candles", true);
        setup(6, "oven", true);
        setup(7, "plant", true);
        setup(8, "floor", false);
        setup(9, "floor", true);
    }

    /**
     * This is a method for getting all buffered images files into each tile objects.
     *
     * @param index     Index for the tile[] array that stores all type of tiles. e.g. floor, wall, ...etc.
     * @param imageName The image's file name.
     * @param collision The collision attributes of tile object.
     */
    public void setup(int index, String imageName, boolean collision) {

        UtilityTool utilityTool = new UtilityTool();

        try {

            // Handle all the half-duplicated part like Instantiation, import image, scale...
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

            if (Objects.equals(imageName, "floor")) {
                tile[index].objectEnabled = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a method for loading a map.txt file into a 2D array that stored all tile's type in different position.
     *
     * @param filePath The map.txt files.
     */
    public void loadMap(String filePath) {
        try {

            // Import the txt file.
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = br.readLine(); // Read a line of text.

                while (col < gp.maxScreenCol) {

                    String[] numbers = line.split(" "); // Splits this string around matches of the given regular expression.

                    int num = Integer.parseInt(numbers[col]); // Converts string to integer.

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a method for trigger the tile's collision on the exit position to false.
     */
    public void exit_update() {
        int tileNum = mapTileNum[19][14];
        tile[tileNum].collision = false;
    }

    /**
     * This is a method for drawing the map for the game.
     *
     * @param g2 Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
     */
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row]; // Extract a tile number which is stored in mapTileNum[0][0].
            g2.drawRect(x, y, 48, 48);
            g2.drawImage(tile[tileNum].image, x, y, null);
            col++;

            x += gp.tileSize;
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}