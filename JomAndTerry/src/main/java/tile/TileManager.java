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

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // mapTileNum will store all numbers in map.txt.

        getTileImage(); // Set each tiles bufferedImage.
        loadMap("/assets/Map/map02.txt"); // Load a map text into 2D array and draw the map.
    }

    public void getTileImage() {

        setup(0, "floor", false);
        setup(1, "wall", true);
        setup(2, "outer_wall", true);
        setup(3, "table", true);
        setup(4, "chair", true);
        setup(5, "candles", true);
        setup(6, "oven", true);
        setup(7, "plant", true);

    }

    // METHOD OF SETTING UP THE IMAGE FOR TILES.
    public void setup(int index, String imageName, boolean collision) {

        UtilityTool utilityTool = new UtilityTool();

        try {

            // Handle all the half-duplicated part like Instantiation, import image, scale...
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            if(!Objects.equals(imageName, "floor")){
                tile[index].exist = true;
            }
            // if tile[index].image = "/assets/new_tiles/floor.png":  # add res on floor tile


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // METHOD OF LOADING MAP FILE INTO 2D ARRAY.
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

    // METHOD FOR DRAWING MAP WHEN ONLY IN A SCREEN.
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