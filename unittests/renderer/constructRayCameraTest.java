package renderer;

import geometries.RadialGeometry;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConstructRayCameraTest {
    void testIntersection(RadialGeometry shape,Camera camera,int expected)
    {
        int counter=0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<Point> Intersections = shape.findIntersections(camera.constructRay(3,3,i,j));
                counter+= Intersections != null ? Intersections.size() : 0;
            }
        assertEquals(expected,counter,"wrong number of intersections");
    }

    @Test
    void testSphereConstructRay() {
        Vector minusZ=new Vector(0,0,-1);
        Vector y = new Vector(0,1,0);
        Camera camera000=Camera.getBuilder()
                .setViewPlaneSize(3,3)
                .setViewPlaneDistance(1)
                .setLocation(Point.ZERO)
                .setDirection(minusZ,y).build();
        Camera camera1=Camera.getBuilder()
                .setViewPlaneSize(3,3)
                .setViewPlaneDistance(1)
                .setLocation(new Point(0,0,0.5))
                .setDirection(minusZ,y).build();
        Sphere sphere1=new Sphere(new Point(0,0,-3),1);
        Sphere sphere2=new Sphere(new Point(0,0,-2.5),2.5);
        Sphere sphere3=new Sphere(new Point(0,0,-2),2);
        Sphere sphere4=new Sphere(new Point(0,0,0),4);
        Sphere sphere5=new Sphere(new Point(0,0,1),0.5);

        //TC01: two intersection points
        testIntersection(sphere1,camera000,2);

        //TC02: 18 intersection points
        testIntersection(sphere2,camera1,18);

        //TC03: 10 intersection points
        testIntersection(sphere3,camera1,10);

        //TC04: 9 intersection points
        testIntersection(sphere4,camera1,9);

        //TC05: 0 intersection points
        testIntersection(sphere5,camera1,0);
    }
    @Test
    void testTriangleConstructRay() {

    }
    @Test
    void testPlaneConstructRay() {

    }
}