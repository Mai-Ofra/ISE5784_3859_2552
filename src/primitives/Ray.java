package primitives;

import java.util.Objects;

/**
 * A fundamental object in 3D geometry, the set of points on a straight line that are on one side of a given point
 * called the head of the ray
 * Defined by point and direction fields
 */
public class Ray {
    private Point head;
    private Vector directions;

    /**
     * parameter ctor
     * @param head the beginning point of the ray
     * @param directions the direction vector of the ray
     */
    public Ray(Point head, Vector directions) {
        this.head = head;
        this.directions = directions.normalize();
    }

    /**
     * check if object is equal to one Ray
     * @param obj the object that is being checked
     * @return true if equal, false if not
     */
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

    /**
     * present a single Ray
     * @return string of the Ray's appearance
     */
    @Override
    public String toString() {
        return "Ray: " +
                "head=" + head +
                ", directions=" + directions;
    }

/**
 * can we do that?
 */

    public Vector getDirections() {
        return directions;
    }

    public Point getHead() {
        return head;
    }
}
