package primitives;

import java.util.Objects;

/**
 * Class Point is a basic object in 3-dimensional geometry, which has three coordinates to represent the location
 */
public class Point {
    public static final Point ZERO = new Point(0, 0, 0);
    protected Double3 xyz;

    /**
     * parameter ctor
     * @param x the first coordinate of the point
     * @param y the second coordinate of the point
     * @param z the third coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz=new Double3(x,y,z);
    }

    /**
     * parameter ctor
     * @param xyz the point is a Double3 type
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * check if object is equal to one point
     * @param obj the object that is being checked
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point point)
        && xyz.equals(point.xyz);
    }

    /**
     * present a single point
     * @return string of the point's appearance
     */
    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * create a vector between p to this
     * @param p the second point
     * @return subtract p from this
     */
    public Vector subtract(Point p)
    {
      return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * add vector to the point
     * @param vec the vector that is being added
     * @return the new point
     */
    public Point add(Vector vec)
    {
        return new Point(xyz.add(vec.xyz));
    }

    /**
     * calculate the distance squared between two points
     * @param p the second point
     * @return the distance
     */
    public double distanceSquared(Point p){
        return (xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1)+(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
    }

    /**
     * calculate the distance between two points
     * @param p the second point
     * @return the distance
     */
    public double distance(Point p)
    {
        return Math.sqrt(this.distanceSquared(p));
    }
}
