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

    /**-----------------Setters---------------------------------*/
    @Override
    public SpotLight setKC(double KC) {
        return (SpotLight) super.setKC(KC);
    }

    @Override
    public SpotLight setKL(double KL) {
        return (SpotLight)super.setKL(KL);
    }

    @Override
    public SpotLight setKQ(double KQ) {
        return (SpotLight)super.setKQ(KQ);
    }
}
