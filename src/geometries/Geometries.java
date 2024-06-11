package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * The {@code Geometries} class represents a collection of geometric shapes
 * that can be intersected by a ray.
 * This class implements the {@code Intersectable} interface.
 */
public class Geometries extends Intersectable{

    final List<Intersectable> geometries=new LinkedList<>();


    /**
     * Default constructor for creating an empty collection of geometries.
     */
    public Geometries() {
    }

    /**
     * Constructor for creating a collection of geometries with the provided intersectional.
     * @param geometries list of geometries representing the geometries to add
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds one or more geometries to the collection.
     * @param geometries list of geometries representing the geometries to add
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Finds all intersection points between the provided ray and the geometries in the collection.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or {@code null} if there are no intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable intersectable : geometries) {
            List<GeoPoint> i = intersectable.findGeoIntersections(ray);
            if (i != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(i);
            }
        }
        return intersections;
    }
}