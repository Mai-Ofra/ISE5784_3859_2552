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
        assertEquals(1,
                planeE.getNormal().length(), DELTA, "plane's normal is not a unit vector");

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
        assertEquals(1, result.length(), DELTA,
                "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to two not parallel vectors on the plane
        Vector v1 = (new Point(0,0,1)).subtract(new Point(0,1,0));
        Vector v2 = (new Point(0,0,1)).subtract(new Point(1,0,0));
        assertTrue(isZero(result.dotProduct(v1)) && isZero(result.dotProduct(v2)),
                "getNormal result is not orthogonal to the plane");
    }

    /** Test method for {@link Plane#findIntersections(Ray)}  */
    @Test
    void testFindIntersections() {
        Vector v111 = new Vector(1,1,1);
        Point p001 = new Point(0,0,1);
        Plane plane = new Plane(p001,Vector.Z);
        final var exp1 = List.of(new Point(1,1,1));
        final var exp2 = List.of(p001);

        // ============ Equivalence Partitions Tests ==============
        // **** Group: The ray not orthogonal and not parallel to the plane
        // TC01: Ray intersects the plane (1 point)
        final var result1 = plane.findIntersections(new Ray(Point.ZERO, v111)).
                stream().sorted(Comparator.
                        comparingDouble(p ->p.distance(Point.ZERO))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses plane");

        // TC02: Ray does not intersect the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(1,2,3),v111)),
                "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11:the ray included in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0.5,0.5,1),Vector.X)),
                "Ray include the plane");

        // TC12: the ray does not included in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,2),Vector.X)),
                "Ray does not include the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 point)
        final var result2 = plane.findIntersections(new Ray(Point.ZERO, Vector.Z))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(Point.ZERO))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp2, result2, "Ray crosses plane");

        // TC14: Ray starts at plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0.5,0.5,1),Vector.Z)),
                "Ray orthogonal to the plane, starts at it");

        // TC15: Ray starts after the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,2),Vector.Z)),
                "Ray orthogonal to the plane, starts after it");

        // **** Group: The ray not orthogonal and not parallel to the plane
        // TC16: Ray starts at the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0.5,0.5,1),v111)),
                "Ray start at the plane");

        // TC17: Ray starts at the point that present the plane (0 points)
        assertNull(plane.findIntersections(new Ray(p001,v111)),
                "Ray start at the point that presents the plane ");
    }

    /** Test method for {@link Plane#findGeoIntersections(Ray, double)}  */
    @Test
    void testFindGeoIntersectionsWithDistance() {
        Plane plane = new Plane(
                new Point(1, 0, 0),
                new Point(-2, 0, -2),
                new Point(0, 0, 3)
        );
        Ray ray = new Ray(new Point(0, 2, 1), new Vector(0, -1, 0));

        // ================= Equivalence Partitions Tests ===========================
        // TC01: the plane is not too far
        List<Intersectable.GeoPoint> result = plane.findGeoIntersections(ray, 3);
        assertEquals(1, result.size());

        // TC02: the plane is too far
        result = plane.findGeoIntersections(ray, 1);
        assertNull(result);

        // ================= BVA Tests ===========================
        // TC03: the intersection is exactly at the max distance (0 points)
        result = plane.findGeoIntersections(ray, 2);
        assertNull(result);

    }
}
