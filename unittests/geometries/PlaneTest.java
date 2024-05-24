package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

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
        Vector v001 = new Vector(0,0,1);
        Vector v111 = new Vector(1,1,1);
        Plane plane = new Plane(Point.ZERO,v001);
        final var exp1 = List.of(new Point(1,1,1));

        // ============ Equivalence Partitions Tests ==============
        // **** Group: The ray not orthogonal and not parallel to the plane
        // TC01: Ray intersects the plane
        final var result1 = plane.findIntersections(new Ray(Point.ZERO, v111))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(Point.ZERO))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses plane");

        // TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,2,3),v111)),
                "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(gp110, v110.scale(-1)))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(gp110))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp3, result3, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p01,v110)),
                "Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(p01, v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(p01))).toList();
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp4, result4, "Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result5 = sphere.findIntersections(new Ray(pZero, v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(pZero))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp5, result5, "Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        final var result6 = sphere.findIntersections(new Ray(new Point(1.5,0,0), v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(new Point(1.5,0,0)))).toList();
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(exp5, result6, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        final var result7 = sphere.findIntersections(new Ray(p100, v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(p100))).toList();
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(exp5, result7, "Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200,v100)),
                "no intersaction with the ray, starts at the sphere");
    }

}
}