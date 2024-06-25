package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link renderer.Camera#constructRay(int, int, int, int)}
 */
class ConstructRayCameraTest {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(Vector.Z.scale(-1),Vector.Y)
            .setViewPlaneDistance(1)
            .setViewPlaneSize(3,3);

    /**
     * Helper method to test the number of intersections between rays from the camera
     * and a given geometric geometry.
     *
     * @param expected the expected number of intersections
     * @param geometry the geometric geometry to test
     * @param camera   the camera from which rays are constructed
     * @param testType description of the test case
     */
    void assertIntersection(int expected, Geometry geometry, Camera camera, String testType, int Nx, int Ny )
    {
        int counter=0;

        for (int i = 0; i < Nx; i++)
            for (int j = 0; j < Ny; j++) {
                List<Point> Intersections = geometry.findIntersections(
                        camera.constructRay(Nx,Ny,i,j));
                counter+= Intersections != null ? Intersections.size() : 0;
            }
        assertEquals(expected,counter,"wrong number of intersections in"+ testType);
    }
    // Camera instances
    Camera camera000=cameraBuilder.build();
    Camera camera1=cameraBuilder
            .setLocation(new Point(0,0,0.5))
            .build();
    /**
     * Test cases for ray-sphere intersections.
     */
    @Test
    void testSphereConstructRay() {

        Sphere sphere1=new Sphere(new Point(0,0,-3),1);
        Sphere sphere2=new Sphere(new Point(0,0,-2.5),2.5);
        Sphere sphere3=new Sphere(new Point(0,0,-2),2);
        Sphere sphere4=new Sphere(new Point(0,0,0),4);
        Sphere sphere5=new Sphere(new Point(0,0,1),0.5);

        //TC01: two intersection points
        assertIntersection(2, sphere1,camera000, "Sphere TC01",3,3);

        //TC02: 18 intersection points
        assertIntersection(18, sphere2,camera1, "Sphere TC02",3,3);

        //TC03: 10 intersection points
        assertIntersection(10, sphere3,camera1, "Sphere TC03",3,3);

        //TC04: 9 intersection points
        assertIntersection(9, sphere4,camera1, "Sphere TC04",3,3);

        //TC05: 0 intersection points
        assertIntersection(0, sphere5,camera1, "Sphere TC05",3,3);
    }
    /**
     * Test cases for ray-triangle intersections.
     */
    @Test
    void testTriangleConstructRay() {
        Triangle triangle1=new Triangle(
                new Point(0,1,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));
        Triangle triangle2=new Triangle(
                new Point(0,20,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));

        //TC01: one intersection point
        assertIntersection(1, triangle1,camera000, "Triangle TC01",3,3);

        //TC02: two intersection points
        assertIntersection(2, triangle2,camera000, "Triangle TC02",3,3);
    }
    /**
     * Test cases for ray-plane intersections.
     */
    @Test
    void testPlaneConstructRay() {
        Plane plane1= new Plane(new Point(0,0,-3),new Vector(0,0,-1));
        Plane plane2= new Plane(
                new Point(0, 0, -2),
                new Point(-3,0,0),
                new Point(-3,2,0));
        Plane plane3 =new Plane(
                new Point(0, 0, -4),
                new Point(-3,0,0),
                new Point(-3,2,0));

        //TC01: 9 intersection points
        assertIntersection(9, plane1,camera000, "Plane TC01",3,3);

        //TC02: 9 intersection points
        assertIntersection(9, plane2,camera000, "Plane TC02",3,3);

        //TC03: 6 intersection points
        assertIntersection(6, plane3,camera000, "Plane TC03",3,3);

    }
}