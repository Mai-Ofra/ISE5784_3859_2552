package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Triangle presents flat triangle
 */
public class Triangle extends Polygon {
    /**
     * parameter ctor
     * @param vertices list of point to creat triangle
     */
    public Triangle(Point... vertices){ super(vertices);}

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
