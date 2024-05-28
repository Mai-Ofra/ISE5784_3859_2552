package geometries;

import primitives.*;

import java.util.LinkedList;
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
        if(ray.getHead().equals(center))
            return List.of(center.add(ray.getDirections().scale(radius)));
        Vector u = center.subtract(ray.getHead());
        double tm = ray.getDirections().dotProduct(u);
        // TC11: Ray starts at sphere and goes inside (1 point)
        if(u.length()==radius)
            if (tm>0)
                return List.of(ray.getHead().add(ray.getDirections().scale(tm+tm)));
            else
                return  null;

        if (u.normalize().equals(ray.getDirections()))
            if(tm<radius||Util.isZero(tm-radius))
                return List.of(center.add(ray.getDirections().scale(radius)));
            else
                return List.of(center.add(ray.getDirections().scale(radius)),center.add(ray.getDirections().scale(-radius)));
        double distanceSquared = u.lengthSquared()-tm*tm;
        double radiusSquared = radius*radius;
        if(distanceSquared>radiusSquared || Util.isZero(distanceSquared-radiusSquared))
            return null;
        double th=Math.sqrt(radiusSquared-distanceSquared);
        Point p1 = ray.getHead().add(ray.getDirections().scale(tm-th));
        Point p2 = ray.getHead().add(ray.getDirections().scale(tm+th));
        return List.of(p1,p2);
    }
}
