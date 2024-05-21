package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * interface for all the geometries
 */
public interface Geometry extends Intersectable{
    public Vector getNormal(Point p);
}
