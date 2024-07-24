package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTest {

    /**
     * Test method for {@link Geometries#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01 Some of geometries intersect but not all
        //sphere, triangle - intersect, plane - doesn't intersect
        Geometries g1 = new Geometries(
                new Sphere(
                        new Point(1, 0, 1), 1),
                new Triangle(
                        new Point(1, -3, 0),
                        new Point(1, 3, 0),
                        new Point(1, 0, 3)
                ),
                new Plane(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(1, 1, 0)
                )
        );

        assertEquals(2, g1.findIntersections(new Ray(
                        new Point(0, 0, 1), Vector.X))
                .size(), "Some of geometries intersect but not all");

        // =============== Boundary Values Tests ==================
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);

        //TC11: list is empty
        Ray r = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        Geometries g2 = new Geometries();

        assertNull(g2.findIntersections(r), "list is empty");

        //TC12: none of the geometries intersect
        Geometries g3 = new Geometries(
                sphere,
                new Plane(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(1, 1, 0)
                )
        );

        assertNull(g3.findIntersections(new Ray(
                        new Point(0, 0, 1), Vector.X)),
                "None of the geometries intersect");

        //TC13: one of the geometries intersects
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-1, -1, 0));
        Geometries g4 = new Geometries(sphere, triangle);

        assertEquals(1, g4.findIntersections(new Ray(
                        new Point(1, 0, 0),
                        Vector.Z)).size(),
                "Wrong number of points");

        //TC14: all geometries intersect
        Plane plane2 = new Plane(
                new Point(2, -3, 0),
                new Point(2, 3, 0),
                new Point(2, 0, 5));
        Geometries g5 = new Geometries(
                new Sphere(
                        new Point(1, 0, 1), 1),
                new Triangle(
                        new Point(1, -3, 0),
                        new Point(1, 3, 0),
                        new Point(1, 0, 3)
                ),
                plane2);

        assertEquals(3,
                g5.findIntersections(new Ray(new Point(0, 0, 1), Vector.X))
                        .size(), "all geometries intersect");
    }
}