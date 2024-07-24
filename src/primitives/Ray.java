package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A fundamental object in 3D geometry,
 * the set of points on a straight line that are on one side of a given point
 * called the head of the ray
 * Defined by point and direction fields
 */
public class Ray {
    private static final double DELTA = 0.1;
    private final Point head;
    private final Vector direction;

    /**
     * parameter ctor
     *
     * @param head      the beginning point of the ray
     * @param direction the direction vector of the ray
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Constructs a ray with a given starting point, direction, and normal vector for small offset.
     *
     * @param point     The starting point of the ray.
     * @param direction The direction vector of the ray.
     * @param n         The normal vector at the starting point, used for small offset.
     */
    public Ray(Point point, Vector direction, Vector n) {
        this.direction = direction.normalize();
        double dotProduct = alignZero(this.direction.dotProduct(n));
        this.head = isZero(dotProduct) ? point : point.add(n.scale(dotProduct < 0 ? -DELTA : DELTA));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray ray)
                && this.head.equals(ray.head) && this.direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    @Override
    public String toString() {
        return "Ray: " +
                "head=" + head +
                ", directions=" + direction;
    }

    /**
     * getter of directions of the ray
     *
     * @return the direction of the ray
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * getter of the head point of the ray
     *
     * @return the head point of the ray
     */
    public Point getHead() {
        return head;
    }

    /**
     * returns a point on the ray at a distance t from the ray's head
     * If t is zero, the head point of the ray is returned
     *
     * @param t the distance from the ray's head
     * @return the point on the ray at distance t
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        try {
            return head.add(direction.scale(t));
        } catch (Exception e) {
            return head;
        }
    }


    /**
     * Finds the closest point to the head point from a list of points.
     *
     * @param geoPoints the list of points that the ray go throw
     * @return the point from the list that is closest to the head point,
     * or null if the list is empty
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints.isEmpty())
            return null;
        // Initialize the minimum distance and closest point with the first point in the list
        double min = geoPoints.getFirst().point.distanceSquared(head);
        GeoPoint minGeoPoint = geoPoints.getFirst();
        double distanceSquared;
        for (int i = 1; i < geoPoints.size(); i++) {
            distanceSquared = geoPoints.get(i).point.distanceSquared(head);
            // Update the minimum distance and closest point if a closer point is found
            if (min > distanceSquared) {
                min = distanceSquared;
                minGeoPoint = geoPoints.get(i);
            }
        }
        return minGeoPoint;
    }

    /**
     * Finds the closest point to the ray's origin from a list of points.
     *
     * @param points The list of points to consider.
     * @return The closest point to the ray's origin, or null if the list is null or empty.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }
}