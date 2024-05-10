package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class to present 3D Tube
 */
public class Tube extends RadialGeometry{
    private Ray ray;

    /**
     * parameters ctor
     * @param radius of Tube
     * @param ray of Tube
     */
    public Tube(double radius, Ray ray)
    {
        super(radius);
        this.ray = ray;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
