package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//לא מתועדדדדדדדדדדדדדדדדדדדדדדדדדדדדדד
/**
 * The {@code Geometries} class represents a collection of geometric shapes
 * that can be intersected by a ray.
 * This class implements the {@code Intersectable} interface.
 */
public class Geometries implements Intersectable{

    final List<Intersectable> geometries=new LinkedList<Intersectable>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }
    private void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable intersectable : geometries) {
            List<Point> i = intersectable.findIntersections(ray);
            if (i != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(i);
            }
        }
        return intersections;
    }
}