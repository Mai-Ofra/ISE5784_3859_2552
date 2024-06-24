package lighting;

import primitives.*;

/**
 * SpotLight represents a light source that radiates light from a specific point
 * in a particular direction, creating a cone of light.
 */
public class SpotLight extends PointLight{
    private final Vector direction;

    /**
     * parameter ctor
     */
    public SpotLight(Color intensity, Point position,Vector direction) {
        super(intensity, position);
        this.direction=direction.normalize();
    }

    /**
     * Calculates the intensity of the light at a given point,
     * considering directionality and attenuation.
     * @param p the point at which the light intensity is to be calculated
     * @return the color intensity of the light at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        double dirDotL =Util.alignZero(direction.dotProduct(super.getL(p)));
        return intensity.scale(Math.max(0,dirDotL)).scale(denominator(p));
    }

    /**
     * Returns the direction from the light source to a given point.
     * @param p the point at which the light direction is required
     * @return the normalized vector representing the direction
     * from the light source to the point
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p).normalize();
    }

    /**-----------------Setters---------------------------------*/
    @Override
    public SpotLight setKc(double kc) {
        return (SpotLight) super.setKc(kc);
    }

    @Override
    public SpotLight setKl(double kl) {
        return (SpotLight)super.setKl(kl);
    }

    @Override
    public SpotLight setKq(double kq) {
        return (SpotLight)super.setKq(kq);
    }
}
