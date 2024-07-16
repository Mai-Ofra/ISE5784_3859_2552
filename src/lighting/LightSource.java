package lighting;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

/**
 * the interface to present light sources
 */
public interface LightSource {

    /**
     * calc the color in a given point
     * @param p the point we want to calc its color
     * @return the color of the point
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light at a given point.
     * @param p the point at which the light direction is required
     * @return the direction of the light at the specified point
     */
    public Vector getL(Point p);

    double getDistance(Point newPoint);
    Double3 getKtr(Intersectable.GeoPoint geoPoint,int numSamples, Vector l, Vector n, Scene scene);
}
