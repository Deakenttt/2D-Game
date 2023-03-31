package utility;

import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import static utility.Size.*;
import java.io.File;

public class ImageLoader {
    private static Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    UtilityTool utilityTool = new UtilityTool();
    final int scale = SCALE;

    public final int tileSize = ORIGINAL_TILE_SIZE * scale; // 48x48 tile
    
    public BufferedImage getImage(String imageName){
        BufferedImage image = imageCache.get(imageName);
        return image;
    }

    public BufferedImage setImage(File imageName) {
        BufferedImage image = loadImage(imageName);
        imageCache.put(imageName.getName().replace(".png", ""), image);
        return image;
    }

    private BufferedImage loadImage(File imageName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageName);
            image = scaleImage(image, tileSize, tileSize);
            MediaTracker mediaTracker = new MediaTracker(new JPanel());
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForID(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Scales an image to the specified dimensions.
     *
     * @param original the original image to be scaled.
     * @param width the desired width of the scaled image.
     * @param height the desired height of the scaled image.
     * @return the scaled image.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

}
