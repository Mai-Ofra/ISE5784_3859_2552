package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * interface for all the geometries
 */
public interface Geometry extends Intersectable{
    /**
     * Returns the normal vector to the geometry at the specified point.
     * @param p the point on the geometry where the normal is to be calculated
     * @return the normal vector to the geometry at the specified point
     */
    Vector getNormal(Point p);
}
