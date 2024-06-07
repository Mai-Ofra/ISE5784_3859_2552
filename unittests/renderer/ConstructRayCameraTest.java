package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ConstructRayCameraTest {
    void testIntersection(Geometry shape, Camera camera, int expected, String testType )
    {
        int counter=0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<Point> Intersections = shape.findIntersections(
                        camera.constructRay(3,3,i,j));
                counter+= Intersections != null ? Intersections.size() : 0;
            }
        assertEquals(expected,counter,"wrong number of intersections in"+ testType);
    }

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

    @Test
    void testSphereConstructRay() {

        Sphere sphere1=new Sphere(new Point(0,0,-3),1);
        Sphere sphere2=new Sphere(new Point(0,0,-2.5),2.5);
        Sphere sphere3=new Sphere(new Point(0,0,-2),2);
        Sphere sphere4=new Sphere(new Point(0,0,0),4);
        Sphere sphere5=new Sphere(new Point(0,0,1),0.5);

        //TC01: two intersection points
        testIntersection(sphere1,camera000,2,"Sphere TC01");

        //TC02: 18 intersection points
        testIntersection(sphere2,camera1,18,"Sphere TC02");

        //TC03: 10 intersection points
        testIntersection(sphere3,camera1,10,"Sphere TC03");

        //TC04: 9 intersection points
        testIntersection(sphere4,camera1,9,"Sphere TC04");

        //TC05: 0 intersection points
        testIntersection(sphere5,camera1,0,"Sphere TC05");
    }
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
        testIntersection(triangle1,camera000,1,"Triangle TC01");

        //TC02: two intersection points
        testIntersection(triangle2,camera000,2, "Triangle TC02");
    }
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
        testIntersection(plane1,camera000,9,"Plane TC01");

        //TC02: 9 intersection points
        testIntersection(plane2,camera000,9,"Plane TC02");

        //TC03: 6 intersection points
        testIntersection(plane3,camera000,6,"Plane TC03");

    }
}