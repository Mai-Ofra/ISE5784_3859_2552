package geometries;

import primitives.Ray;

/**
 * class to present 3D Cylinder
 */
public class Cylinder extends Tube {
    protected double hight;

    /**
     * parameter ctor
     * @param radius of Cylinder (sent to super ctor)
     * @param ray of Cylinder (sent to super ctor)
     * @param hight of Cylinder
     */
    public Cylinder(double radius, Ray ray,double hight) {
        super(radius,ray);
        this.hight = hight;
    }
}
