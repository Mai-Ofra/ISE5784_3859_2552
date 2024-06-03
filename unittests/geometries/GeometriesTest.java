package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    /**
     * Test method for {@link Geometries#findIntersections(Ray)}.
     */
    @Test
    void findIntersections()
    {

        // ============ Equivalence Partitions Tests ==============
        //TC01 Some of geometries intersect but not all
        //ray that intersects sphere with 1 point
        Sphere sphere1 = new Sphere(new Point(1, 0, 1), 1);
        //ray that intersects triangle with 1 point
        Triangle triangle1 = new Triangle(
                new Point(1, -3, 0),
                new Point(1, 3, 0),
                new Point(1, 0, 3));
        // ray doesn't intersect the plane
        Plane plane = new Plane(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(1, 1, 0));
        Geometries g1 = new Geometries(sphere1, triangle1, plane);
        assertEquals(2, g1.findIntersections(new Ray(
                new Point(0, 0, 1),
                new Vector(1, 0, 0)))
                .size(), "Some of geometries intersect but not all");

        // =============== Boundary Values Tests ==================
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);

        //TC11: list is empty
        Ray r = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        Geometries g2 = new Geometries();
        assertNull(g2.findIntersections(r), "list is empty");

        //TC12: none of the geometries intersect
        Geometries g3 = new Geometries(sphere, plane);
        assertNull(g3.findIntersections(new Ray(
                new Point(0, 0, 1),
                new Vector(1, 0, 0))),
                "None of the geometries intersect");

        //TC13: one of the geometries intersects
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-1, -1, 0));
        Geometries g4 = new Geometries(sphere, triangle);
        assertEquals(1, g4.findIntersections(new Ray(
                new Point(1, 0, 0),
                new Vector(0, 0, 1))).size(),
                "Wrong number of points");

        //TC14: all geometries intersect
        //plane that intersects with 1 point
        Plane plane2 = new Plane(
                new Point(2, -3, 0),
                new Point(2, 3, 0),
                new Point(2, 0, 5));
        //sphere that intersects ray with 1 point
        //triangle that intersects ray with 1 point
        Geometries g5 = new Geometries(sphere1, triangle1, plane2);
        assertEquals(3,
                g5.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 0, 0)))
                        .size(),"all geometries intersect");
    }
}