package geometries;

public abstract class RadialGeometry implements Geometry{
    protected double radius;

    /**
     * parameters ctor
     * @param radius the radius of the new object
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
    
}
