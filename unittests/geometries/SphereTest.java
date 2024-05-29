package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Ofra & Mai
 */
class SphereTest {
    private final Point p100 = new Point(1, 0, 0);

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test if the ctor throw error when the radius is negative
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sphere(new Point(0, 0, 0), -4),
                "ctor must throw error when radius is negative");
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(new Point(0, 0, 0), 1);

        // TC01: the point is on the sphere
        assertEquals(new Vector(1, 0, 0),
                sp.getNormal(new Point(1, 0, 0)),
                "the normal to the sphere is not correct");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final Point gp110 = new Point(1, 1, 0);
        final Point p200 = new Point(2,0, 0);
        final var exp1 = List.of(gp1, gp2);
        final var exp2 = List.of(gp110);
        final var exp3 = List.of(p200);
        final var exp4 = List.of(gp110,new Point(1,-1,0));
        final var exp5 = List.of(p200);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v100 = new Vector(1, 0, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(0.5,0.5,0), v110))
                .stream().sorted(Comparator.
                        comparingDouble(p ->p.distance(new Point(0.5,0.5,0)))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp2, result2, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v310.scale(-1))),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(gp110,p200.subtract(gp110) ))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(gp110))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp3, result3, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p01,v110)),
                "Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(
                new Ray(new Point(1,2,0), new Vector(0,-1,0)))
                .stream().sorted(Comparator.comparingDouble(
                        p ->p.distance(new Point(1,2,0)))).toList();
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp4, result4, "Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result5 = sphere.findIntersections(new Ray(Point.ZERO, v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(Point.ZERO))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp5, result5, "Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        final var result6 = sphere.findIntersections(new Ray(new Point(1.5,0,0), v100))
                .stream().sorted(Comparator.
                        comparingDouble(p ->p.distance(new Point(1.5,0,0)))).toList();
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(exp5, result6, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        final var result7 = sphere.findIntersections(new Ray(p100, v100))
                .stream().sorted(Comparator.comparingDouble(p ->p.distance(p100))).toList();
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(exp5, result7, "Ray crosses sphere");

     /**/   // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200,v100)),
                "no intersaction with the ray, starts at the sphere");

     /**/   // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0),v100)),
                "Ray's line out of sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0),v100)),
                "tangent ray");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(gp110,v100)),
                "tangent ray that start on the sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0),v100)),
                "Ray's line out of sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1,2,0),v100)),
                "Ray's line out of sphere");

    }
}