package primitives;

/**
 * Class Vector is a fundamental object in 3D geometry, with direction and size
 * defined by a point
 * The class inherits from the Point class
 */
public class Vector extends Point{
    public static Vector X=new Vector(1,0,0);
    public static Vector Y=new Vector(0,1,0);
    public static Vector Z=new Vector(0,0,1);
    /**
     * parameter ctor
     * @param x the first coordinate of this vector
     * @param y the second coordinate of this vector
     * @param z the third coordinate of this vector
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
     * Add v to the vector and return the result
     * @param v the vector that is being added
     * @return the result vector
     */
    public Vector add(Vector v) {
      return new Vector(v.xyz.add(xyz));
    }

    /**
     * multiply vector by scalar and return the result
     * @param scale the scalar that the vector is being multiply by
     * @return the result vector
     */
    public Vector scale(double scale) {
        return new Vector(xyz.scale(scale));
    }

    /**
     * calculate the scalar product
     * @param v the vector that is scalar multiplied by the current vector
     * @return the result scalar of the dotProduct
     */
    public double dotProduct(Vector v) {

        return v.xyz.d1*xyz.d1+v.xyz.d2*xyz.d2+v.xyz.d3*xyz.d3;
    }

    /**
     * calculate the cross product of this vector and another vector.
     * @param v the vector to be crossed with this vector.
     * @return a new Vector which is the cross product of this vector and the input vector.
      */
    public Vector crossProduct(Vector v) {
        return new Vector(xyz.d2*v.xyz.d3-xyz.d3*v.xyz.d2,
                xyz.d3*v.xyz.d1- xyz.d1*v.xyz.d3,
                xyz.d1*v.xyz.d2- xyz.d2*v.xyz.d1);
    }

    /**
     * calculate the squared length of this vector
     * @return the squared length
     */
    public double lengthSquared(){
        //taking less amount of time then calling dotProduct function
        return  xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3;
    }

    /**
     * calculate the length of this vector
     * @return the length
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Calculate the normal vector of this vector
     * @return the normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
}