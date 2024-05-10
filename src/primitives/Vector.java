package primitives;

/**
 * Class Vector is a fundamental object in 3D geometry, with direction and size
 * defined by a point
 * The class inherits from the Point class
 */
public class Vector extends Point{
    /**
     * parameter ctor
     * @param x the first coordinate of the vector
     * @param y the second coordinate of the vector
     * @param z the third coordinate of the vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(x==0 && y==0 && z==0)
            throw new IllegalArgumentException("cannot create a Vector with zero coordinates");
    }
    /**
     * parameter ctor
     * @param xyz the point is a Double3 type
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with zero coordinates");
    }


    @Override
    public boolean equals(Object obj) {
       return super.equals(obj);
    }


    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Add v to the vector
     * @param v the vector that is being added
     * @return the new vector
     */
    public Vector add(Vector v) {
        return new Vector(v.xyz.add(xyz));
    }

    /**
     * multiply vector by scalar
     * @param scale the scalar that the vector is being multiply by
     * @return the new vector
     */
    public Vector scale(double scale) {
        return new Vector(xyz.scale(scale));
    }

    /**
     * calculate the scalar product
     * @param v the vector that is scalar multiplied by the current vector
     * @return the scalar that was calculated
     */
    public double dotProduct(Vector v) {
        return v.xyz.d1*xyz.d1+v.xyz.d2*xyz.d2+v.xyz.d3*xyz.d3;
    }

    /**
     * calculate the cross product of two vectors
     * @param v the vector that is cross multiplied by the current vector
     * @return the new vector
     */
    public Vector crossProduct(Vector v) {
        return new Vector(xyz.d2*v.xyz.d3-xyz.d3*v.xyz.d2, xyz.d3*v.xyz.d1- xyz.d1*v.xyz.d3, xyz.d1*v.xyz.d2- xyz.d2*v.xyz.d1);
    }

    /**
     * calculate the squared length of the vector
     * @return the squared length
     */
    public double lengthSquared(){
        return this.dotProduct(this);
    }

    /**
     * calculate the length of the vector
     * @return the length
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * calculate the normalize vector
     * @return the normalize vector
     */
    public Vector normalize() {
        return new Vector(xyz.d1/length(), xyz.d2/length(), xyz.d3/length());
    }
}