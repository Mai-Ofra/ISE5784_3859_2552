package geometries;

public abstract class RadialGeometry extends Geometry{
    protected double radius;

    /**
     * parameters ctor
     * @param radius the radius of the new object
     */
    public RadialGeometry(double radius) {
        if(radius<=0)
            throw  new IllegalArgumentException("The radius must be bigger than 0");
        this.radius = radius;
    }
    
}
