package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double KC=1;
    private double KL=0;
    private double KQ=0;

    /**
     * Parameter ctor
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }

    /**-----------------Setters---------------------------------*/
    public PointLight setKC(double KC) {
        this.KC = KC;
        return this;
    }

    public PointLight setKL(double KL) {
        this.KL = KL;
        return this;
    }

    public PointLight setKQ(double KQ) {
        this.KQ = KQ;
        return  this;
    }
}
