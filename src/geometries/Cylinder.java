package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        if(height<=0)
            throw new IllegalArgumentException("height of cylinder must be bigger than 0");
        this.height = height;
    }

    /**
     *get normal
     * @param p that the normal
     * @return normal from the point that was received (on the cylinder)
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
