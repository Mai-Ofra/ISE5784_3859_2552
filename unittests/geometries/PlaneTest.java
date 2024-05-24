package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Plane
 * @author Ofra & Mai
 */
class PlaneTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;
    /** Test method for {@link Plane#Plane(Point, Point, Point)} &
     * {@link Plane#Plane(Point, Vector)}  */

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test for Constructor that receives point and normal
        Plane planeE = new Plane(new Point(0, 0, 1),new Vector(-1,-1,-1));
        //ensure that plan's normal is a unit vector
        assertEquals(1, planeE.getNormal().length(), DELTA, "plane's normal is not a unit vector");

        // =============== Boundary Values Test ==================

        // TC01: ensure the ctor throw exception when the three points are on the same ray
        assertThrows(
                IllegalArgumentException.class,
                ()->new Plane(
                new Point(2, 0, 0),
                new Point(1, 0, 0),
                new Point(3, 0, 0)),
                "ctor need to throw exception when all points are on the same ray");

        // TC02: ensure the ctor throw exception when there are two equal points
        assertThrows(
                IllegalArgumentException.class,
                ()->new Plane(
                        new Point(2, 0, 0),
                        new Point(2, 0, 0),
                        new Point(0, 1, 0)),
                "ctor need to throw exception when two of the points are the same");

    }
    /** Test method for {@link Plane#getNormal(Point)}  */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
          Plane planeE = new Plane(
                  new Point(0, 0, 1),
                  new Point(1, 0, 0),
                  new Point(0, 1, 0));
        // generate the test result
        Vector result = planeE.getNormal();
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to two not parallel vectors on the plane
        Vector v1 = (new Point(0,0,1)).subtract(new Point(0,1,0));
        Vector v2 = (new Point(0,0,1)).subtract(new Point(1,0,0));
        assertTrue(isZero(result.dotProduct(v1)) && isZero(result.dotProduct(v2)),
                "getNormal result is not orthogonal to the plane");
    }

    @Test
    void testFindIntersections() {
    }
}