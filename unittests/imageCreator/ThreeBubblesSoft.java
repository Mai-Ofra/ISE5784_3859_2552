//package imageCreator;
//
//import geometries.Geometry;
//import geometries.Polygon;
//import geometries.Sphere;
//import lighting.DirectionalLight;
//import org.junit.jupiter.api.Test;
//import primitives.*;
//import renderer.Camera;
//import renderer.ImageWriter;
//import renderer.SimpleRayTracer;
//import scene.Scene;
//
//
//public class ThreeBubblesSoft {
//
//    @Test
//    void testTreeBubbles() {
//        Scene scene = new Scene("ThreeBubblesSoft");
//        //bubbles
//        Geometry bubble1 = new Sphere(new Point(4,-10,3.8), 0.47)
//                .setEmission(new Color(137,21,59))
//                .setMaterial(new Material().setKs(0.2).setKd(0.7).setShininess(30).setKt(0.5));//pink
//        Geometry bubble2 = new Sphere(new Point(3,-7,3), 0.6)
//                .setEmission(new Color(223,84,255))
//                .setMaterial(new Material().setKs(0.2).setKd(0.7).setShininess(30));//purple
//        Geometry bubble3 = new Sphere(new Point(5.1,-8,4), 0.5)
//                .setEmission(new Color(25,63,52))
//                .setMaterial(new Material().setKs(0.2).setKd(0.7).setShininess(10).setKt(0.8));//light blue
//
//        Geometry floor = new Polygon(
//                new Point(-20, -20, 2),
//                new Point(10, -20, 2),
//                new Point(10, 10, 2),
//                new Point(-10, 10, 2))
//                .setEmission(new Color(80,124,208)) // Brown seafloor
//                .setMaterial(new Material().setKd(0.5).setKs(0.5));
//        Geometry mirror = new Polygon(
//                new Point(-1,-3,2),
//                new Point(9,-3,2),
//                new Point(9,-3,10),
//                new Point(-1,-3,10))
//                .setEmission(new Color(3, 3, 3))
//                .setMaterial(new Material().setKr(0.5).setShininess(100).setKs(0.9).setKd(0.1));
//
//        // Add geometries to the scene
//        scene.geometries.add(bubble1,bubble2,bubble3,mirror,floor);
//        //add light
//        scene.lights.add(new DirectionalLight(new Color(150,150,150), new Vector(0.7, 0, -1)));
//        scene.background=new Color(200,200,200);
//
//        // Set up the camera
//        Camera.Builder camera = Camera.getBuilder()
//                .setLocation(new Point(4.4, -16, 4.6))
//                .setDirection(new Vector(0, 1, -0.2), new Vector(0, 0.2, 1))
//                .setViewPlaneDistance(1000)
//                .setViewPlaneSize(500, 500)
//                .setImageWriter(new ImageWriter("ThreeBubblesSoft", 1500, 1500))
//                .setRayTracer(new SimpleRayTracer(scene)).setMultySamples(17);
//
//        // Render the image
//        camera.build()
//                .renderImage()
//                .writeToImage();
//
//    }
//}
