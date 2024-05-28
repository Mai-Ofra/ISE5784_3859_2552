package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Testing Point
 * @author Mai & Ofra
 */
class PointTest{
    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);
    Vector v1         = new Vector(1, 2, 3);
    Vector v2         = new Vector(-2, -4, -6);
    Vector v1Opposite = new Vector(-1, -2, -3);

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /** Test method for {@link primitives.Point#subtract(Point)}. */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Test ==============
        // TC01: simple subtract between two points
        assertEquals(new Vector(1, 2, 3), p2.subtract(p1),
                "Subtract does not work correctly");
        // =============== Boundary Values Test ==================
        // TC01: subtract between point and itself
        assertThrows(IllegalArgumentException.class,
                ()->p1.subtract(p1),
                "(point - itself) does not throw an exception"
        );
        // TC02: subtract between two vectors when the second vector is negative
        assertEquals(new Vector(3, 6, 9),v1.subtract(v2),
                "Vector - Vector does not work correctly");
        // TC03: subtract between two vectors when the result is the zero vector
        assertThrows(
                IllegalArgumentException.class,
                ()->v1.subtract(v1),
                "Vector - itself does not throw an exception");
    }

    /** Test method for {@link primitives.Point#add(Vector)}. */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Test ==============
        // TC01: simple add between point and vector
        assertEquals(
                new Point(2, 4, 6),p1.add(v1),
                "(point + vector) = other point, does not work correctly");
        // =============== Boundary Values Test ==================
        // TC01: add between point and vector that supposed to return zero
        assertEquals(
                Point.ZERO,p1.add(v1Opposite),
                "(point + vector) = center of coordinates does not work correctly");
    }

    /** Test method for {@link primitives.Point#distance(Point)}. */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Test ==============
        // TC01: simple distance between points
        assertEquals(3,p1.distance(p3),DELTA,"distance between points is wrong");
        // TC02: simple distance between points (the other way)
        assertEquals(3,p3.distance(p1),DELTA,"distance between points is wrong");
        // =============== Boundary Values Test ==================
        // TC01: distance between point to itself that supposed to return zero
        assertTrue(isZero(p1.distance(p1)),"point distance to itself is not zero");
          }

    /** Test method for {@link primitives.Point#distanceSquared(Point)}. */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Test ==============
        // TC01: simple squared distance between points
        assertEquals(9,p1.distanceSquared(p3),DELTA,
                "squared distance between points is wrong");
        // TC02: simple squared distance between points (the other way)
        assertEquals(9,p3.distanceSquared(p1),DELTA,
                "squared distance between points is wrong");
        // =============== Boundary Values Test ==================
        // TC01: squared distance between point to itself that supposed to return zero
        assertTrue(isZero(p1.distanceSquared(p1)),
                "point squared distance to itself is not zero");
    }
}