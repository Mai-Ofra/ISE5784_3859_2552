package imageCreator;

import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import java.util.Random;


class NeilArmstrongTest {
    Random rand = new Random();
    private static final double KD = 0.5;
    private static final double KS = 0.5;
    private static final int SHININESS = 100;
    private static final Color STAR_COLOR = new Color(255, 252, 117);

    @Test
    void testNeilArmstrong() {
        Scene scene = new Scene("Neil Armstrong Scene");

        // astronaut helmet (sphere)
        Point sphereCenter = new Point(5, -5, 3);
        double sphereRadius = 1.5;
        Geometry helmet1 = new Sphere(sphereCenter, sphereRadius)
                .setEmission(new Color(180, 180, 180))
                .setMaterial(new Material().setKd(KD).setKs(0.2).setShininess(50));
        Geometry helmet2 = new Sphere(new Point(4.57, -5.35, 3.13), 1.2)
                .setEmission(new Color(3, 3, 3))
                .setMaterial(new Material().setKr(0.5).setShininess(100).setKs(0.9).setKd(0.1));

        Geometry sun = new Sphere(new Point(0, 10, 2), 0.8)
                .setEmission(new Color(255,154,63)).setMaterial(new Material().setKt(0.9));
        scene.lights.add(new PointLight(
                new Color(0, 2, 204),
                new Point(0, 10, 2)
        ));


        // planets (spheres)
        Geometry planet1 = new Sphere(new Point(-1, -4, 4), 0.5)
                .setEmission(new Color(205, 234, 0))//yellow
                .setMaterial(new Material().setShininess(100));
        Geometry planet2 = new Sphere(new Point(0.5, -10, 3), 0.9)
                .setEmission(new Color(156, 56, 255))
                .setMaterial(new Material().setShininess(100));
        Geometry planet3 = new Sphere(new Point(2.2, -10, 7), 0.8)
                .setEmission(new Color(255, 97, 46))
                .setMaterial(new Material().setShininess(100));
        Geometry planet4 = new Sphere(new Point(0, -9, 10), 0.8)
                .setEmission(new Color(65, 255, 190))
                .setMaterial(new Material().setShininess(100));
        Geometry planet5 = new Sphere(new Point(1, -6.8, 2.8), 0.6)
                .setEmission(new Color(255, 2, 140))
                .setMaterial(new Material().setShininess(100));
        Geometry planet6 = new Sphere(new Point(2, -14, 6), 0.8)
                .setEmission(new Color(255, 0, 25))
                .setMaterial(new Material().setShininess(100));
        Geometry planet7 = new Sphere(new Point(-1, -7, 6.7), 0.8)
                .setEmission(new Color(255, 246, 50))
                .setMaterial(new Material().setShininess(100));

        //planetFloor (Polygon)
        Geometry planetFloor = new Polygon(
                new Point(-20, -20, 2),
                new Point(10, -10, 2),
                new Point(10, 10, 2),
                new Point(-10, 10, 2))
                .setEmission(new Color(139, 69, 19)) // Brown planetFloor
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));

        //star1 (4 triangle)
        Geometry triangle11 = new Triangle(
                new Point(2.35, -3, 5.45),
                new Point(2.2, -3, 5.17),
                new Point(2.5, -3, 5.17))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle12 = new Triangle(
                new Point(2.35, -3, 5.17),
                new Point(2.1, -3, 5.3),
                new Point(2.6, -3, 5.3))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle13 = new Triangle(
                new Point(2.2, -3, 5.17),
                new Point(2.5, -3, 5.17),
                new Point(2.55, -3, 5.07))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle14 = new Triangle(
                new Point(2.2, -3, 5.17),
                new Point(2.5, -3, 5.17),
                new Point(2.13, -3, 5.07))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));

        //star2 (4 triangle)
        Geometry triangle21 = new Triangle(
                new Point(1.55, 0, 5.45),
                new Point(1.4, 0, 5.17),
                new Point(1.7, 0, 5.17))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle22 = new Triangle(
                new Point(1.55, 0, 5.17),
                new Point(1.3, 0, 5.3),
                new Point(1.8, 0, 5.3))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle23 = new Triangle(
                new Point(1.4, 0, 5.17),
                new Point(1.7, 0, 5.17),
                new Point(1.75, 0, 5.07))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle24 = new Triangle(
                new Point(1.4, 0, 5.17),
                new Point(1.7, 0, 5.17),
                new Point(1.33, 0, 5.07))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));

        //star3 (4 triangle)
        Geometry triangle31 = new Triangle(
                new Point(1.55, 5, 6.155),
                new Point(1.4, 5, 5.77),
                new Point(1.7, 5, 5.77))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle32 = new Triangle(
                new Point(1.55, 5, 5.77),
                new Point(1.3, 5, 6),
                new Point(1.8, 5, 6))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle33 = new Triangle(
                new Point(1.4, 5, 5.77),
                new Point(1.7, 5, 5.77),
                new Point(1.75, 5, 5.67))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
        Geometry triangle34 = new Triangle(
                new Point(1.4, 5, 5.77),
                new Point(1.7, 5, 5.77),
                new Point(1.33, 5, 5.67))
                .setEmission(STAR_COLOR)
                .setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));

        //wall of stars1 (spheres)
        double x, z;
        double num = 2;
        for (int k = 0; k < 8; k++)
            for (int i = 0; i < 35; i++) {
                x = rand.nextDouble(-1, 6);
                z = rand.nextDouble(2, 6);
                scene.geometries.add(new Sphere(
                        new Point(x + k, 0, z + k), 0.012 * num)
                        .setEmission(new Color(250, 250, 250)));
                num = num < 2 ? num += 0.5 : 0.5;
            }

        //wall of stars2 (spheres)
        double y;
        for (int k = 0; k < 8; k++)
            for (int i = 0; i < 5; i++) {
                y = rand.nextDouble(-16, -3);
                z = rand.nextDouble(2, 20);
                scene.geometries.add(new Sphere(
                        new Point(-2.5, y + k, z + k), 0.035 * num)
                        .setEmission(new Color(250, 250, 250)));
                num = num < 2 ? num += 0.5 : 0.5;
            }

        //wall of stars3 (spheres)
        for (int k = 0; k < 8; k++)
            for (int i = 0; i < 5; i++) {
                x = rand.nextDouble(-1, 6);
                z = rand.nextDouble(2, 20);
                scene.geometries.add(new Sphere(
                        new Point(x + k, -17, z + k), 0.035 * num)
                        .setEmission(new Color(250, 250, 250)));
                num = num < 2 ? num += 0.5 : 0.5;
            }


        // Add geometries to the scene
        scene.geometries.add(helmet1, helmet2, planet1, planet2, planet3,
                planet4, planet5, planet6, planet7, planetFloor,sun);
        // Add stars to the scene
        scene.geometries.add(triangle11, triangle12, triangle13, triangle14);
        scene.geometries.add(triangle21, triangle22, triangle23, triangle24);
        scene.geometries.add(triangle31, triangle32, triangle33, triangle34);

        //Add light to the scene
        scene.lights.add(new DirectionalLight(
                new Color(255, 208, 66),
                new Vector(0, -1, -0.5)
        ));

        scene.lights.add(new SpotLight(
                new Color(0, 2, 204),
                new Point(12, -1, 10),
                new Vector(-1, -1, -0.5)
        ));

        //Add color to the scene
        scene.background = new Color(0, 6, 80);

        // Set up the camera
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(4, -14, 3))
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setViewPlaneDistance(1000)
                .setViewPlaneSize(500, 500)
                .setImageWriter(new ImageWriter("Neil Armstrong scene", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .setAntiAliasing(17)
               .setThreadsCount(2).setAdaptive(true);//;//
        // Render the image
        camera.build()

                .renderImage()
                .writeToImage();
    }
}