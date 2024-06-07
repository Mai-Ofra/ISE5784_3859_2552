package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Triangle presents flat triangle
 */
public class Triangle extends Polygon {
    /**
     * parameter ctor
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super();
        if(p1.subtract(p2).normalize()==p1.subtract(p3).normalize())
            throw new IllegalArgumentException("The points are aligned and cannot form a triangle.");
        vertices = List.of(p1,p2,p3);
        size=3;
        plane= new Plane(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        //no intersections
        if(intersections==null)
            return null;
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());
        Vector n1=v1.crossProduct(v2).normalize();
        Vector n2=v2.crossProduct(v3).normalize();
        Vector n3=v3.crossProduct(v1).normalize();
        double vxn1=alignZero(ray.getDirections().dotProduct(n1));
        double vxn2=alignZero(ray.getDirections().dotProduct(n2));
        double vxn3=alignZero(ray.getDirections().dotProduct(n3));
        if((vxn1<0&&vxn2<0&&vxn3<0)||(vxn1>0&&vxn2>0&&vxn3>0))
            return intersections;
        return null;
    }
}
