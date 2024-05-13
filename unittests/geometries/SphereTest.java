package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Polygons
 * @author Ofra & Mai
 */
class SphereTest {
    /** Test method for {@link geometries.Sphere#getNormal(Point)}. */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test if the ctor throw error when the radius is negative
        assertThrows(
                IllegalArgumentException.class,
                ()-> new Sphere(new Point(0,0,0),-4),
                "ctor must throw error when radius is negative");
    }
    /** Test method for {@link geometries.Sphere#getNormal(Point)}. */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(new Point(0,0,0),1);
        // TC01: the point is on the sphere
        assertEquals(new Vector(1,0,0),
                sp.getNormal(new Point(1,0,0)),
                "the normal to the sphere is not correct");
        // TC02: the point is not on the sphere
        assertThrows(
                IllegalArgumentException.class,
                ()->sp.getNormal(new Point(2,0,0)),
                "need to throw an exception when point is not on the sphere");
    }
}