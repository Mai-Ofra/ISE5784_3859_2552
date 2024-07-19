package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * Class Plane present a geometric flat Infinite surface
 */
public class Plane extends Geometry {
    private final Point p;
    private final Vector normal;


    /**
     * parameter ctor
     *
     * @param p1 point to present the plane
     * @param p2 point to calculate the normal of the plane
     * @param p3 point to calculate the normal of the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        this.normal = v1.crossProduct(v2).normalize();
        this.p = p1;
    }

    /**
     * parameter ctor
     *
     * @param p      point to present the plane
     * @param normal normal of the plane
     */
    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal.normalize();
    }

    /**
     * p getter
     *
     * @return point that present the plane
     */
    public Point getP() {
        return p;
    }

    /**
     * normal getter
     *
     * @return the normal that presents the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        //in case the ray starts at point that present the plane
        if (p.equals(ray.getHead()))
            return null;

        Vector u = p.subtract(ray.getHead());
        double denominator = alignZero(normal.dotProduct(ray.getDirection()));
        //in case the ray direction is parallel to the plane
        if (isZero(denominator))
            return null;
        double t = alignZero(normal.dotProduct(u) / denominator);
        // If t is negative, the intersection is behind the ray's start point.
        if (t <= 0) {
            return null;
        }
        if (alignZero(t - maxDistance)<0)
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        return null;
    }
}