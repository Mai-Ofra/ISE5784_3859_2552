package primitives;

/**
 * Class Point is a basic object in 3-dimensional geometry,
 * which has three coordinates to represent the location
 */
public class Point {

    protected Double3 xyz;

    /** Zero triad (0,0,0) */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * parameter ctor
     * @param x the first coordinate of this point
     * @param y the second coordinate of this point
     * @param z the third coordinate of this point
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point point)
        && xyz.equals(point.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * Subtracts the given point from the current point and returns the resulting vector.
     * @param p the point to subtract from the current point
     * @return a new Vector representing the difference between the current point
     * and the given point
     */
    public Vector subtract(Point p)
    {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adds the given vector to the current point and returns the resulting point.
     * @param vec the vector to add to the current point
     * @return a new Point representing the sum of the current point and the given vector
     */
    public Point add(Vector vec)
    {
        return new Point(xyz.add(vec.xyz));
    }

    /**
     * Calculates the squared distance between the current point and the given point.
     * @param p the point to which the squared distance is calculated
     * @return the squared distance between the current point and the given point
     */
    public double distanceSquared(Point p){
        return (
                xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1)+
                (xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+
                (xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
    }

    /**
     * Calculates the distance between the current point and the given point.
     * @param p the point to which the distance is calculated
     * @return the  distance between the current point and the given point
     */
    public double distance(Point p)
    {
        return Math.sqrt(this.distanceSquared(p));
    }
}
