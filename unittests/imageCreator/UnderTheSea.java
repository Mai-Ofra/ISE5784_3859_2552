package imageCreator;

import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.WHITE;

public class UnderTheSea {

     private static final Color FIN_COLOR = new Color(0, 255, 0); // Green fins
    private static final Color BUBBLE_COLOR = new Color(173, 216, 230); // Light blue bubbles
    private static final Color CORAL_COLOR = new Color(255, 69, 0); // Orange coral
    private static final Color LIGHT_COLOR = new Color(250, 250, 0);
    private static final Vector LIGHT_DIRECTION = new Vector(1, 1, -0.5);

    private static final double KD = 0.5;
    private static final double KS = 0.5;
    private static final int SHININESS = 100;

    @Test
    void testUnderTheSea() {
        Scene scene = new Scene("Underwater Scene");

        // astronaut helmet (sphere)
        Point sphereCenter = new Point(5, -5, 3);
        double sphereRadius = 1.5;
        Geometry helmet1 = new Sphere(sphereCenter, sphereRadius)
                .setEmission(new Color(200,200,200))
                .setMaterial(new Material().setKd(KD).setKr(0.1).setKs(0.2).setShininess(100));
        Geometry helmet2 = new Sphere(new Point(4.57,-5.35,3.13), 1.2)
                .setEmission(Color.BLACK)
                .setMaterial(new Material().setKd(0.1).setKs(0.9).setKr(new Double3(0.5, 0, 0.4)).setShininess(100));



        // Bubbles (spheres)
        Geometry bubble1 = new Sphere(new Point(0, -8, 4), 1)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble2 = new Sphere(new Point(0.5, -10, 3), 1)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble3 = new Sphere(new Point(2.2, -9, 5), 0.8)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble4 = new Sphere(new Point(0, -3, 4), 0.8)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble5 = new Sphere(new Point(1, -6, 2), 0.8)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble6 = new Sphere(new Point(-1, -5, 6), 0.8)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));
        Geometry bubble7 = new Sphere(new Point(-1, -7, 6.7), 0.8)
                .setEmission(new Color(45,255,188))
                .setMaterial(new Material().setShininess(100));

//        //star
//        Geometry topTriangle = new Triangle(
//                new Point(4,-7,4),
//                new Point(3, -9, 3),
//                new Point(5, -9, 3))
//                .setEmission(FIN_COLOR)
//                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));

        Geometry seafloor = new Polygon(
                new Point(-20, -20, 2),
                new Point(10, -10, 2),
                new Point(10, 10, 2),
                new Point(-10, 10, 2))
                .setEmission(new Color(139, 69, 19)) // Brown seafloor
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));


        // Add geometries to the scene
        scene.geometries.add(helmet1,helmet2, bubble1, bubble2, bubble3, bubble4, bubble5, bubble6,bubble7,seafloor);
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.BLUE), LIGHT_DIRECTION));

        // Set up the camera
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(4, -14, 3))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneDistance(1000)
                .setViewPlaneSize(500, 500)
                .setImageWriter(new ImageWriter("UnderwaterScene", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene));

        // Render the image
        camera.build()
                .renderImage()
                .writeToImage();

    }
}
