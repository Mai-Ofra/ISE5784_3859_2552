package geometries;
import primitives.*;

import java.util.List;

/**
 * Class Plane present a geometric flat Infinite surface
 */
public class Plane implements Geometry{
    private final Point p;
    private final Vector normal;

    /**
     * parameter ctor
     * @param p1 point to present the plane
     * @param p2 point to calculate the normal of the plane
     * @param p3 point to calculate the normal of the plane
     */
    public Plane(Point p1,Point p2,Point p3) {
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        this.normal = v1.crossProduct(v2).normalize();
        this.p = p1;
    }

    /**
     * parameter ctor
     * @param p point to present the plane
     * @param normal normal of the plane
     */
    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal.normalize();
    }

    /**
     * p getter
     * @return point that present the plane
     */
    public Point getP() {
        return p;
    }

    /**
     * normal getter
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
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
