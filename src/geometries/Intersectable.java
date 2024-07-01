package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * interface to present all shapes that are intersectable
 */
public abstract class Intersectable {

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * parameters ctor
         * @param geometry the geometry associated with this GeoPoint.
         * @param point the point associated with this GeoPoint.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object GeoPoint) {
            if (this == GeoPoint) return true;
            return (GeoPoint instanceof GeoPoint geoPoint)&&
                    geoPoint.geometry==geometry && geoPoint.point.equals(point);
         }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint:" +
                    "geometry=" + geometry +
                    ", point=" + point;
        }
    }

    /**
     * method that return all the points that the ray intersect with
     * @param ray The ray with which you are looking for intersection points on the shape
     * @return list of all intersection points on the shape
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();}

    public final List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance)
    {
        return findGeoIntersectionsHelper(ray,maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}