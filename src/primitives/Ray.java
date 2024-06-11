package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.*;

/**
 * A fundamental object in 3D geometry,
 * the set of points on a straight line that are on one side of a given point
 * called the head of the ray
 * Defined by point and direction fields
 */
public class Ray {
    private final Point  head;
    private final Vector directions;

    /**
     * parameter ctor
     * @param head the beginning point of the ray
     * @param directions the direction vector of the ray
     */
    public Ray(Point head, Vector directions) {
        this.head = head;
        this.directions = directions.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
      return (obj instanceof Ray ray)
       && this.head.equals(ray.head) && this.directions.equals(ray.directions);
    }
    @Override
    public int hashCode() {
        return Objects.hash(head, directions);
    }

    @Override
    public String toString() {
        return "Ray: " +
                "head=" + head +
                ", directions=" + directions;
    }

    /**
     * getter of directions of the ray
     * @return the direction of the ray
     */
    public Vector getDirections() {
        return directions;
    }

    /**
     * getter of the head point of the ray
     * @return the head point of the ray
     */
    public Point getHead() {
        return head;
    }

    /**
     * returns a point on the ray at a distance t from the ray's head
     * If t is zero, the head point of the ray is returned
     * @param t the distance from the ray's head
     * @return the point on the ray at distance t
     */
    public Point getPoint(double t) {
        if(isZero(t))
            return head;
        return head.add(directions.scale(t));
    }

    /**
     * Finds the closest point to the head point from a list of points.
     * @param pointList the list of points that the ray go throw
     * @return the point from the list that is closest to the head point,
     *         or null if the list is empty
     */
    public Point findClosestPoint(List<Point> pointList)
    {
        if(pointList.isEmpty())
            return null;
        // Initialize the minimum distance and closest point with the first point in the list
        double min= pointList.getFirst().distanceSquared(head);
        Point minPoint=pointList.getFirst();
        double distanceSquared;
        for (int i = 1; i < pointList.size(); i++)
        {
            distanceSquared= pointList.get(i).distanceSquared(head);
            // Update the minimum distance and closest point if a closer point is found
            if(min>distanceSquared) {
                min = distanceSquared;
                minPoint = pointList.get(i);
            }
        }
        return minPoint;
    }
}