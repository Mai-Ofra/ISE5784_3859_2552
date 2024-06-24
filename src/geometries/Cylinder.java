package geometries;
import primitives.*;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class to present 3D Cylinder
 */
public class Cylinder extends Tube {
    protected double height;

    /**
     * parameter ctor
     * @param radius of Cylinder (sent to super ctor)
     * @param ray of Cylinder (sent to super ctor)
     * @param height of Cylinder
     */
    public Cylinder(double radius, Ray ray,double height) {
        super(radius,ray);
        if(height<0 || isZero(height))
            throw new IllegalArgumentException("height of cylinder must be bigger than 0");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        if(p.equals(ray.getHead()))//if so, can't calc t
            return ray.getDirections().scale(-1);
        Vector pMinusPO = p.subtract(ray.getHead());
        double t = alignZero(ray.getDirections().dotProduct(pMinusPO));
        if (isZero(t))//if so, the point is on the bottom base
            return ray.getDirections().scale(-1);
        if (t == height)//if so, the point is on the top base
            return ray.getDirections();
        //else, on the sides of the cylinder
        Point center = ray.getPoint(t);
        //calculate the normal
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}