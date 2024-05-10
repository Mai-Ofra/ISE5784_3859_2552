package geometries;
import primitives.*;

/**
 * Class Plane present a geometric flat surface
 *
 */
public class Plane {
    private Point p;
    private Vector normal;

    /**
     * parameter ctor
     * @param p1 point to present the plane
     * @param p2 point to calculate the normal of the plane
     * @param p3 point to calculate the normal of the plane
     */
    public Plane(Point p1,Point p2,Point p3) {
        this.normal = null;
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
}
