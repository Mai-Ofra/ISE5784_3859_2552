package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Testing Cylinder
 *
 * @author Ofra & Mai
 */
class CylinderTest {

    /**
     * Test method for {@link Cylinder#Cylinder(double, Ray, double)}
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test if the ctor throw error when the height is negative
        assertThrows(
                IllegalArgumentException.class,
                () -> new Cylinder(
                        1,
                        new Ray(Point.ZERO, Vector.X), -4),
                "ctor must throw error when height is negative");

        // TC02: test if the ctor throw error when the radius is negative
        assertThrows(
                IllegalArgumentException.class,
                () -> new Cylinder(-1,
                        new Ray(Point.ZERO, Vector.X), 6),
                "ctor must throw error when radius is negative");

    }

    /**
     * Test method for {@link Cylinder#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test case point on the round surface
        Cylinder cylinder = new Cylinder(1,
                new Ray(new Point(0, 1, 0), new Vector(6, 0, 0)), 6);
        Vector cyDir = Vector.X;
        assertEquals(
                new Vector(0, -1, 0).normalize(),
                cylinder.getNormal(new Point(4, 0, 0)),
                "get normal give wrong normal");

        // TC02: test case point on the bottom base
        assertEquals(cyDir.scale(-1),
                cylinder.getNormal(new Point(0, 0.5, 0.5)),
                "get normal give wrong normal");

        // TC03: test case point on the top base
        assertEquals(cyDir,
                cylinder.getNormal(new Point(6, 0.5, 0.5)),
                "get normal give wrong normal");

        // =============== Boundary Values Test ==================

        // TC01: point in the center of top base
        assertEquals(cyDir,
                cylinder.getNormal(new Point(6, 1, 0)),
                "get normal give wrong normal");

        // TC02: point in the center of bottom base
        assertEquals(cyDir.scale(-1),
                cylinder.getNormal(new Point(0, 1, 0)),
                "get normal give wrong normal");
    }

}