package utility;

import junit.framework.TestCase;
import main.GamePanel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static utility.Size.ORIGINAL_TILE_SIZE;

public class ImageLoaderTest {
    ImageLoader imageLoader;
    File folder;
    GamePanel gp;
    @Mock
    Graphics2D g2;

    @Before
    public void setUp() {
        this.gp = new GamePanel();
        this.imageLoader = new ImageLoader();
        this.folder = new File("src/main/resources/assets");
        folder = folder.exists() ? folder : new File("JomAndTerry/src/main/resources/assets");
    }

    @Test
    public void testGetImage() throws IOException {

        // test cat images
        final int tileSize = ORIGINAL_TILE_SIZE * 3;
        this.imageLoader = gp.imageLoader;


        // test the method
        File img = new File("src/main/resources/assets/cat/cat_up_2.png");
        BufferedImage in = ImageIO.read(img);
        BufferedImage expectedImg = new BufferedImage(tileSize, tileSize, in.getType());
        g2 = expectedImg.createGraphics();
        g2.drawImage(in, 0, 0, tileSize, tileSize, null);
        g2.dispose();

        // Loop over every pixel.
        for (int x = 0; x < expectedImg.getWidth(); x++) {
            for (int y = 0; y < expectedImg.getHeight(); y++) {
                // Compare the pixels for equality.
                assertEquals(expectedImg.getRGB(x, y), imageLoader.getImage("cat_up_2").getRGB(x, y));
            }
        }
    }

    @Test
    public void testSetImage() {
        String testPath = "src/main/resources/assets";

        // test 1 image
        File testFile1 = new File(testPath + "/cat/cat_down_1.png");
        BufferedImage testImg = imageLoader.setImage(testFile1);

        // test bufferedImage size correctness
        assertEquals(48, testImg.getWidth());
        assertEquals(48, testImg.getHeight());


        System.out.println("testGetImage() passed");
    }

    @Test
    public void testScaleImage() {
        String testPath = "src/main/resources/assets";

        // test image 1
        File testFile1 = new File(testPath + "/cat/cat_down_1.png");
        BufferedImage testImg1 = imageLoader.scaleImage(imageLoader.setImage(testFile1), 200, 200);

        // test bufferedImage size correctness
        assertEquals(200, testImg1.getWidth());
        assertEquals(200, testImg1.getHeight());

        // test image 2
        File testFile2 = new File(testPath + "/items/cheese.png");
        BufferedImage testImg2 = imageLoader.scaleImage(imageLoader.setImage(testFile2), 21, 80);

        // test bufferedImage size correctness
        assertEquals(21, testImg2.getWidth());
        assertEquals(80, testImg2.getHeight());


        System.out.println("testScaleImage() passed");
    }
}