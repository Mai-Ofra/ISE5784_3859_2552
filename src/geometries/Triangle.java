package geometries;

import primitives.Point;
import primitives.Vector;

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
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
