package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import java.util.List;
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
        if(p1.subtract(p2).normalize()==p1.subtract(p3).normalize())
            throw new IllegalArgumentException("The points are aligned and cannot form a triangle.");
        vertices = List.of(p1,p2,p3);
        size=3;
        plane= new Plane(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
