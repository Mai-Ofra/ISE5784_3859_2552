package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    @Test
    void testConstructor() {
        // TC01: check if the direction vector is normalized
        Ray ray= new Ray(new Point(1,0,0),new Vector(1,2,3));
        assertEquals(1,ray.getDirections().length(),
                "vector directions not normalized");
    }

    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Test ==============
        // TC01: when the distance is positive
        Ray ray = new Ray(new Point(1,0,0),new Vector(1,0,0));
        double t = -2;
        assertEquals(new Point(-1,0,0),ray.getPoint(t),"Wrong point");

        // TC01: when the distance is positive
        double t2=1;
        assertEquals(new Point(2,0,0),ray.getPoint(t2),"Wrong point");

        // =============== Boundary Values Test ==================
        // TC11: when the distance is zero
        double t3=0;
        assertEquals(new Point(1,0,0),ray.getPoint(t3),"Wrong point");

    }

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1,1,1),new Vector(0,1,2));
        Point closest =new Point(1,3,5);
        List<Point> points=List.of(new Point(1,4,7),closest,new Point(1,5,9));
        // ============ Equivalence Partitions Test ==============
        // TC01: where the closest point is in the middle of the list
        assertEquals(closest,ray.findClosestPoint(points),
                "wrong point");
        // =============== Boundary Values Test ==================
        // TC11: where the closest point in the start of the list
        points=List.of(closest,new Point(1,4,7),new Point(1,5,9));
        assertEquals(closest,ray.findClosestPoint(points),
                "wrong point");
        // TC12: where the closest point is in the end of the list
        points=List.of(new Point(1,4,7),new Point(1,5,9),closest);
        assertEquals(closest,ray.findClosestPoint(points),
                "wrong point");
        // TC13: when the list is empty
        points=List.of();
        assertNull(ray.findClosestPoint(points), "the method should return null");
    }
}