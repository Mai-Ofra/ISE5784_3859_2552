package geometries;

import primitives.*;
/**
 * interface for all the geometries
 */
public abstract class Geometry extends Intersectable{
    protected Color emission=Color.BLACK;
    private Material material= new Material();
    /**
     * Returns the normal vector to the geometry at the specified point.
     * @param p the point on the geometry where the normal is to be calculated
     * @return the normal vector to the geometry at the specified point
     */
    public abstract Vector getNormal(Point p);

    /**
     * Retrieves the emission color of the geometry.
     * @return the current emission color.
     */
    public Color getEmission() {
        return emission;
    }

    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the emission color of the geometry.
     * @param emission the new emission color to be set.
     * @return the current instance of Geometry with the updated emission color.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
