package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Polygons
 * @author Ofra & Mai
 */
class TriangleTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Point[] pts =
                {
                        new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)
                };
        Triangle tri = new Triangle(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA,
                "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0, result.dotProduct(pts[i].subtract(pts[i+1])) , DELTA,
                    "Triangle's normal is not orthogonal to one of the edges");
    }

    @Test
    void testFindIntersections() {
        Point p010 = new Point(0,1,0);
        Point pt110 = new Point(1,1,0);
        Point pt2 = new Point(0.5,0.5,1);
        Triangle triangle = new Triangle(Point.ZERO,pt2,pt110);
        final var exp1 = List.of(new Point(0.5,0.5,0.5));


        // ============ Equivalence Partitions Tests =============
        // TC01: Ray intersects the triangle (1 point)
        final var result1 = triangle.findIntersections(
                new Ray(p010,new Point(0.5,0.5,0.5).subtract(p010) ))
                .stream().toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses triangle");

        // TC02: Ray does not intersect the triangle, intersect point opposite a side(0 point)
        assertNull(triangle.findIntersections(
                new Ray(p010,new Point(1.25,1.25,0.75).subtract(p010))),
                "Ray does not intersect the triangle");

        // TC03: Ray does not intersect the triangle, intersect point opposite a corner point(0 point)
        assertNull(triangle.findIntersections(
                new Ray(p010,new Point(0.5,0.5,1.5).subtract(p010))),
                "Ray does not intersect the triangle");

        // =============== Boundary Values Tests ==================
        // TC11:the ray intersect the triangle on a corner point (0 point)
        assertNull(triangle.findIntersections(
                        new Ray(p010,pt110.subtract(p010))),
                "Ray does not intersect the triangle");

        // TC12: the ray intersect the triangle on one of its sides  (0 point)
        assertNull(triangle.findIntersections(
                        new Ray(p010,new Point(0.75,0.75,0.5).subtract(p010))),
                "Ray does not intersect the triangle");

        // TC13: the ray intersect the triangle in continuation of the side of the triangle(0 point)
        assertNull(triangle.findIntersections(
                        new Ray(p010,new Point(1,1,2).subtract(p010))),
                "Ray does not intersect the triangle");

    }


    @Test
    void testFindGeoIntersectionsWithDistance() {
        Triangle triangle = new Triangle(
                Point.ZERO,
                new Point(0, 0, 2),
                new Point(0, 2, 0));
        Ray ray = new Ray(new Point(-2, 0.5, 0.5), new Vector(1, 0, 0));

        // ================= Equivalence Partitions Tests ===========================
        // TC01: the triangle is not too far
        List<Intersectable.GeoPoint> result = triangle.findGeoIntersections(ray, 3);
        assertEquals(1, result.size());

        // TC02: the triangle is too far
        result = triangle.findGeoIntersections(ray, 1);
        assertNull(result);

        // ================= BVA Tests ===========================
        // TC03: the intersection is exactly at the max distance (0 points)
        result = triangle.findGeoIntersections(ray, 2);
        assertNull(result);

    }
}