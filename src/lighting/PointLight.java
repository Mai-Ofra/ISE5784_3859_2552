package lighting;

import geometries.Intersectable;
import geometries.Plane;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.Random;


/**
 * PointLight represents a light source that radiates light uniformly in all directions
 * from a specific point in space. It attenuates over distance based on attenuation factors.
 */
public class PointLight extends Light implements LightSource {
    private final Point position;
    private double kc = 1;
    private double kl = 0;
    private double kq = 0;
    Random rand = new Random();
    private double radius;

    /**
     * Parameter ctor
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.radius=intensity.getColor().getRGB();
    }

    /**
     * Calculates the intensity of the light at a given point, considering attenuation.
     *
     * @param p the point at which the light intensity is to be calculated
     * @return the color intensity of the light at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity.scale(denominator(p));
    }

    /**
     * Returns the direction from the light source to a given point.
     *
     * @param p the point at which the light direction is required
     * @return the normalized vector representing the direction
     * from the light source to the point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point newPoint) {
        return position.distance(newPoint);
    }

    @Override
    public Double3 getKtr(Intersectable.GeoPoint geoPoint, int numSamples, Vector l, Vector n, Scene scene) {
//        Vector lightDirection = l.scale(-1);
//        Plane plane = new Plane(this.position,lightDirection);
//        Ray ray;
//        for (int i = 0; i < numSamples; i++) {
//            double x = rand.nextDouble(-5,5);
//            double y = rand.nextDouble(5,-5);
//            double z = rand.nextDouble(5,-5);
//            ray = new Ray(geoPoint.point,lightDirection.add(new Vector(x,y,z)));
//            List<Point> intersection = plane.findIntersections(ray);
//            if(intersection!=null&& !intersection.isEmpty())
//            {
//                Point point=intersection.getFirst();
//                if(point.distance(this.position)<)
//            }
//
//
return null;
        }


    /**
     * Calculates the denominator for intensity attenuation calculation.
     * Takes into account the distance from the light source and attenuation factors.
     *
     * @param p the point at which the attenuation factor is to be calculated
     * @return the denominator for attenuation calculation
     */
    protected double denominator(Point p) {
        double distance = p.distance(position);
        return Util.alignZero(1 / (kc + kl * distance + kq * distance * distance));
    }

    /**
     * -----------------Setters---------------------------------
     */
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
        return this;
    }
}
