package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;

    /**
     * parameter ctor
     */
    public SpotLight(Color intensity, Point position,Vector direction) {
        super(intensity, position);
        this.direction=direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity.
                scale(Math.max(0,direction.dotProduct(super.getL(p)))).scale(denominator(p));
    }

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
