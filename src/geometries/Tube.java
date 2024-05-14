package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class to present 3D Tube
 */
public class Tube extends RadialGeometry{
    protected Ray ray;

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
        //calculate the distance between the head of the tube, and the center
        // of the tube next to the point that was received
        double t = ray.getDirections().dotProduct(p.subtract(ray.getHead()));
        if (t == 0)//Boundary Values, when (p-p0) is orthogonal to the ray of the tube
            return p.subtract(ray.getHead()).normalize();
        //calculate the center of the tube next to the point that was received
        Point center = ray.getHead().add(ray.getDirections().scale(t));
        //calculate the normal
        return p.subtract(center).normalize();
    }
}