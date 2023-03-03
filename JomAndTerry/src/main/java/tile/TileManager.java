package tile;

import main.GamePanel;

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

    public TileManager (GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // mapTileNum will store all numbers in map.txt.

        getTileImage(); // Set each tiles bufferedImage.
        loadMap("/assets/Map/map02.txt"); // Load a map text into 2D array and draw the map.
    }

    public void getTileImage() {
        try {

            // Floor
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/floor.png")));

            // Wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/wall.png")));
            tile[1].collision = true;

            // outter wall
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/outer_wall.png")));
            tile[2].collision = true;

            // table
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/table.png")));
            tile[3].collision = true;

            // chair
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/chair.png")));
            tile[4].collision = true;
            
            // candles
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/candles.png")));
            tile[5].collision = true;

            // oven
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/oven.png")));
            tile[6].collision = true;

            // plant
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/new_tiles/plant.png")));
            tile[7].collision = true;
            
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

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
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