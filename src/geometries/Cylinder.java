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
        if(p.equals(ray.getHead()))//if so, can't calc t and we know the needed output
            return ray.getDirections().scale(-1);
        //calculate the distance between the head of the cylinder, and the center
        // of the cylinder next to the point that was received
        double t = ray.getDirections().dotProduct(p.subtract(ray.getHead()));
        if (t == 0)//if the distance i 0, the point is on the bottom base
            return ray.getDirections().scale(-1);
        if (t == height)//if the distance is equal to the height, the point is on the top base
            return ray.getDirections();
        //else, on the sides of the cylinder
        //calculate the center of the cylinder next to the point that was received
        Point center = ray.getHead().add(ray.getDirections().scale(t));
        //calculate the normal
        return p.subtract(center).normalize();
    }
}