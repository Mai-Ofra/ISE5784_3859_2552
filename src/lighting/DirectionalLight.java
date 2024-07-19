package lighting;

import primitives.*;


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

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point newPoint) {
        return Double.POSITIVE_INFINITY;
    }

}
