package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    /**
     * Scene of the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene)).setAntiAliasing(9)
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), Vector.Y)
            .setViewPlaneDistance(100)
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void renderTwoColorTest() {
        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(
                        new Point(-100, 0, -100),
                        new Point(0, 100, -100),
                        new Point(-100, 100, -100)), // up
                // left
                new Triangle(
                        new Point(-100, 0, -100),
                        new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(
                        new Point(100, 0, -100),
                        new Point(0, -100, -100),
                        new Point(100, -100, -100))); // down
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(75, 127, 90));

        // right
        camera
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();
    }

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void renderMultiColorTest() {
        scene.geometries.add( // center
                new Sphere(new Point(0, 0, -100), 50),
                // up left
                new Triangle(
                        new Point(-100, 0, -100),
                        new Point(0, 100, -100),
                        new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(
                        new Point(-100, 0, -100),
                        new Point(0, -100, -100),
                        new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(
                        new Point(100, 0, -100),
                        new Point(0, -100, -100),
                        new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2, 0.2, 0.2)));
        camera
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(WHITE))
                .writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        // enter XML file name and parse from XML file into scene object
        File file = new File(System.getProperty("user.dir")
                + "/XMLFiles/renderTestTwoColors.xml");
        Scene scene1 = new Scene("Test xml");
        scene.readFromXML(file.getPath());
        camera
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();
    }

    /**
     * Test for XML example - for bonus
     */
    @Test
    public void xmlExample() {
        // enter XML file name and parse from XML file into scene object
        File file = new File(System.getProperty("user.dir") + "/XMLFiles/XmlExample.xml");
        scene.readFromXML(file.getPath());
        camera
                .setImageWriter(new ImageWriter("xmlExample", 1000, 1000))
                .setThreadsCount(4)
                .build()
                .renderImage()
                .writeToImage();
    }
}