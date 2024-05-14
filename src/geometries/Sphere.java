package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to present 3D Sphere
 */
public class Sphere extends RadialGeometry{
    private Point center;


    /**
     * parameters ctor
     * @param center of the Sphere
     * @param radius of the Sphere (sent to super)
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }
}
