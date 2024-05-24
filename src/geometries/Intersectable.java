package geometries;
import primitives.*;
import java.util.List;
/**
 * interface to present all shapes that are intersectable
 */
public interface Intersectable {
    /**
     * method that return all the points that the ray intersect with
     * @param ray The ray with which you are looking for intersection points on the shape
     * @return list of all intersection points on the shape
     */
    List<Point> findIntersections(Ray ray);
}
