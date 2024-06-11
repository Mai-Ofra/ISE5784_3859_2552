package geometries;

import primitives.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static primitives.Util.*;

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
        // If the ray starts at the sphere's center
        if (ray.getHead().equals(center)) {
            return List.of(center.add(ray.getDirections().scale(radius)));
        }

        // Vector from ray head to sphere center
        Vector u = center.subtract(ray.getHead());

        double uLength = alignZero(u.length());
        double tm = alignZero(ray.getDirections().dotProduct(u));


        // If the ray starts at the sphere's surface and goes inside
        if (uLength == radius) {
            if (tm > 0)//if the angle smaller than 90 degrees
            {
                return List.of(ray.getPoint(tm + tm));
            } else//The ray is tangent to the sphere or goes outside (no intersections)
            {
                return null;
            }
        }

        // If the ray's direction is aligned with vector u
        if (u.normalize().equals(ray.getDirections())) {
            if (tm < radius || isZero(tm - radius)) {
                return List.of(center.add(ray.getDirections().scale(radius)));
            } else {
                return Stream.of(
                        center.add(ray.getDirections().scale(radius)),
                        center.add(ray.getDirections().scale(-radius))).
                        sorted(Comparator.comparingDouble(p ->p.distance(ray.getHead()))).toList();
            }
        }
        // If the ray's direction is opposite to vector u
        else if (u.normalize().equals(ray.getDirections().scale(-1))) {
            if (uLength > radius) {
                return null;
            } else {
                return List.of(center.add(ray.getDirections().scale(radius)));
            }
        }

        // Calculate the squared distance from the ray to the sphere's center
        double distanceSquared = alignZero(u.lengthSquared() - tm * tm);
        double radiusSquared = alignZero(radius * radius);

        // If the distance from the ray to the center is greater than or equal to the radius
        if (distanceSquared > radiusSquared || isZero(distanceSquared - radiusSquared)) {
            return null;
        }

        // If the ray's head is outside the sphere
        if (uLength > radius) {
            if (tm < 0 || distanceSquared > radiusSquared) {
                return null;
            }
        }

        double th = alignZero(Math.sqrt(radiusSquared - distanceSquared));

        // If the ray's head is inside the sphere
        if (uLength < radius) {
            return List.of(ray.getPoint(tm + th));
        }

        // Calculate the intersection points
        Point p1 = ray.getPoint(tm - th);
        Point p2 = ray.getPoint(tm + th);
        return Stream.of(p1, p2).
                sorted(Comparator.comparingDouble(p ->p.distance(ray.getHead()))).toList();
    }
}
