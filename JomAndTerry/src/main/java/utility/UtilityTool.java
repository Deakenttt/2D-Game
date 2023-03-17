package utility;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The UtilityTool class provides methods for dealing with images.
 */
public class UtilityTool {
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
