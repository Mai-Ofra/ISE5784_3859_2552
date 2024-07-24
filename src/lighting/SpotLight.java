package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * SpotLight represents a light source that radiates light from a specific point
 * in a particular direction, creating a cone of light.
 */
public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * parameter ctor
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double dirDotL = Util.alignZero(direction.dotProduct(super.getL(p)));
        return intensity.scale(Math.max(0, dirDotL)).scale(denominator(p));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p).normalize();
    }

    /**
     * -----------------Setters---------------------------------
     */
    @Override
    public SpotLight setKc(double kc) {
        return (SpotLight) super.setKc(kc);
    }

    @Override
    public SpotLight setKl(double kl) {
        return (SpotLight) super.setKl(kl);
    }

    @Override
    public SpotLight setKq(double kq) {
        return (SpotLight) super.setKq(kq);
    }
}