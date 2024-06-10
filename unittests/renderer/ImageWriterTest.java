package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter= new ImageWriter("grid",800,500);
        for(int i=0; i<imageWriter.getNx();i++)
            for (int j=0;j<imageWriter.getNy();j++)
                imageWriter.writePixel(i,j,new Color(java.awt.Color.PINK));

        for(int i=0; i<imageWriter.getNx();i+=50)
            for (int j=0;j<imageWriter.getNy();j++)
                imageWriter.writePixel(i,j,new Color(java.awt.Color.black));

        for(int i=0; i<imageWriter.getNy();i+=50)
            for (int j=0;j<imageWriter.getNx();j++)
                imageWriter.writePixel(j,i,new Color(java.awt.Color.black));

        imageWriter.writeToImage();
    }
}