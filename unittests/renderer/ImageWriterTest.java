package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ImageWriter class.
 */
class ImageWriterTest {
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("grid", 800, 500);
        // Fill the image with a pink background
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, new Color(java.awt.Color.PINK));
        // Draw vertical black grid lines every interval pixels
        int interval =50;
        for (int i = 0; i < imageWriter.getNx(); i += interval)
            for (int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, Color.BLACK);
        // Draw horizontal black grid lines every interval pixels
        for (int i = 0; i < imageWriter.getNy(); i += interval)
            for (int j = 0; j < imageWriter.getNx(); j++)
                imageWriter.writePixel(j, i, Color.BLACK);
        imageWriter.writeToImage();
    }
}