package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kc =1;
    private double kl =0;
    private double kq =0;

    /**
     * Parameter ctor
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity.scale(denominator(p));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    protected double denominator(Point p){
        double distance = p.distance(position);
        return 1/(kc + kl *distance+ kq *distance*distance);
    }
    /**-----------------Setters---------------------------------*/
    public PointLight setKc(double kc) {
        this.kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        this.kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        this.kq = kq;
        return  this;
    }
}
