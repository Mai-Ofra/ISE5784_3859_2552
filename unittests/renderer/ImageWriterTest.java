package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ImageWriter class.
 */
class ImageWriterTest {
    /**{@link ImageWriter#writeToImage()}.*/
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("grid", 800, 500);
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        // Fill the image with a pink background
        for (int i = 0; i < Nx; i++)
            for (int j = 0; j < Ny; j++)
                imageWriter.writePixel(i, j, new Color(PINK));

        // Draw vertical black grid lines every interval pixels
        int interval = 50;
        for (int i = 0; i < Nx; i += interval)
            for (int j = 0; j < Ny; j++)
                imageWriter.writePixel(i, j, Color.BLACK);

        // Draw horizontal black grid lines every interval pixels
        for (int i = 0; i < Ny; i += interval)
            for (int j = 0; j < Nx; j++)
                imageWriter.writePixel(j, i, Color.BLACK);
        imageWriter.writeToImage();
    }
}