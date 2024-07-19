package lighting;

import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * DirectionalLight represents a light source that has a constant direction
 * and intensity.
 */
public class DirectionalLight extends Light implements LightSource{
    private final Vector direction;

    /**
     * parameter ctor
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction;
    }

    /**
     * Returns the intensity of the light at a given point.
     * @param p the point at which the light intensity is required
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * Returns the direction of the light at a given point.
     * @param p the point at which the light direction is required
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point newPoint) {
        return Double.POSITIVE_INFINITY;
    }

}
