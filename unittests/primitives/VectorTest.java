package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Polygons
 *
 * @author Mai & Ofra
 */
class VectorTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v2Opposite = new Vector(2, 4, 6);
    Vector v3 = new Vector(0, 3, -2);
    Vector v4 = new Vector(1, 2, 2);

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}
     * and {@link primitives.Vector#Vector(Double3)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Test ==================
        // TC01: check that the ctor throw exception when try to create the zero vector
        // (ctor that get two coordinates)
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "need to throw an exception when try to make the zero vector");
        // TC02: check that the ctor throw exception when try to create the zero vector
        // (ctor that get a Double3)
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vector(new Double3(0, 0, 0)),
                "need to throw an exception when try to make the zero vector");

    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        // =============== Boundary Values Test ==================
        // TC01: 2 vectors with the same length and the opposite direction
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(v1Opposite),
                "Vector + -itself does not throw an exception");
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test case for add function
        assertEquals(new Vector(-1, -2, -3), v1.add(v2),
                "Vector + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test case for scale function
        assertEquals(new Vector(2, 4, 6),
                v1.scale(2),
                "multiply vector with scalar does not work correctly");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        // =============== Boundary Values Test ==================
        // TC01: check dotProduct between orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v3)),
                "dotProduct() for orthogonal vectors is not zero");
        // TC02: check dotProduct between vector and normalized vector
        assertEquals(3,
                v1.dotProduct((new Vector(0, 0, 5).normalize())),
                DELTA,
                "vector * normalized vector, does not work correctly");
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test case for dotProduct between two vectors
        assertEquals(-28, v1.dotProduct(v2), DELTA, "dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    void testCrossProduct() {
        // =============== Boundary Values Test ==================
        // TC01: check CrossProduct parallel vectors with opposite direction and different length
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");
        // TC02: check CrossProduct parallel vectors with the same direction and different length
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v2Opposite),
                "crossProduct() for parallel vectors does not throw an exception");
        // TC03: check CrossProduct parallel vectors with the same direction and length
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v1),
                "crossProduct() for parallel vectors does not throw an exception");
        // TC04: check CrossProduct parallel vectors with the different direction and same length
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v1Opposite),
                "crossProduct() for parallel vectors does not throw an exception");
        // TC05: check cross product with orthogonal vectors
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),
                "crossProduct() result is not orthogonal to its operands");
        // ============ Equivalence Partitions Tests ==============
        // TC01: check cross product with vectors with obtuse angle between them
        vr = v1.crossProduct(v4);
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v4)),
                "crossProduct() result is not orthogonal to its operands");
        // TC02: check cross product with vectors with sharp angle between them
        vr = v2.crossProduct(v4);
        assertTrue(isZero(vr.dotProduct(v2)) && isZero(vr.dotProduct(v4)),
                "crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check squared length
        assertEquals(9, v4.lengthSquared(), DELTA, "lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple check squared length
        assertEquals(3, v4.length(), DELTA, "length() wrong value");
        // =============== Boundary Values Test ==================
        // TC01: check length when orthogonal vectors
        Vector vr = v1.crossProduct(v3);
        assertEquals(vr.length(),
                v1.length() * v3.length(), DELTA,
                "crossProduct() wrong result length, with orthogonal vectors");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test case for normalize function
        assertEquals(1, u.length(), DELTA,
                "the normalized vector is not a unit vector");
        // TC02: check if normalize vector is parallel to the original one
        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(u),
                "the normalized vector is not parallel to the original one");
        // TC03: check if normalize vector is not in opposite direction to the original one
        assertFalse(v.dotProduct(u) < 0,
                "the normalized vector is opposite to the original one");
    }
}