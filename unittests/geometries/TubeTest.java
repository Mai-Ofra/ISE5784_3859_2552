package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Tube
 * @author Mai
 */
class TubeTest {
    /** Test method for {@link geometries.Tube#Tube(double, Ray)}. */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test case when radius is negative
        assertThrows(
                IllegalArgumentException.class,
                ()-> new Tube(-4,new Ray(new Point(0,1,0),new Vector(2,1,2))),
                "need to throw an exception when the radius is negative");
    }
    /** Test method for {@link geometries.Tube#getNormal(Point)}. */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube t=new Tube(1,new Ray(new Point(0,1,0),new Vector(6,0,0)));

        // TC01: test case when point is on the tube
        assertEquals(
                new Vector(0,-4,0).normalize(),
                t.getNormal(new Point(4,0,0)),
                "get normal doesnt work correctly");

       // =============== Boundary Values Test ==================

        // TC01: test case when (p-p0) is orthogonal to the ray of the tube
        assertEquals(
                new Vector(0,-4,0).normalize(),
                t.getNormal(new Point(0,0,0)),
                "get normal doesnt work correctly");
    }

    @Test
    void testFindIntersections() {
    }
}