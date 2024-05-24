package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class to present 3D Sphere
 */
public class Sphere extends RadialGeometry{
    private final Point center;


    /**
     * parameters ctor
     * @param center center point of the Sphere
     * @param radius radius of the Sphere (sent to super)
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
