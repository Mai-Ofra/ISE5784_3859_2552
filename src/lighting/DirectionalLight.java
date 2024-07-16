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

    @Override
    public Double3 getKtr(Intersectable.GeoPoint geoPoint, int numSamples, Vector l, Vector n, Scene scene) {
        Vector lightDirection = l.scale(-1);
        Ray ray = new Ray(geoPoint.point, lightDirection, n);
        List<Intersectable.GeoPoint> intersections = scene.geometries
                .findGeoIntersections(ray, this.getDistance(geoPoint.point));
        if (intersections == null || intersections.isEmpty())
            return Double3.ONE;
        Double3 Ktr = Double3.ONE;
        for (Intersectable.GeoPoint intersect : intersections) {
            Ktr = Ktr.product(intersect.geometry.getMaterial().kt);
            // Check if ktr is close to 0
            if (Ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return Ktr;
    }
}
