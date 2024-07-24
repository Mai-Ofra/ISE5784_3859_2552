package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * the interface to present light sources
 */
public interface LightSource {

    /**
     * calc the color in a given point
     *
     * @param p the point we want to calc its color
     * @return the color of the point
     */
    Color getIntensity(Point p);

    /**
     * Returns the direction of the light at a given point.
     *
     * @param p the point at which the light direction is required
     * @return the direction of the light at the specified point
     */
    Vector getL(Point p);

    /**
     * Calculates the distance from this light to a point.
     *
     * @param newPoint The point to which the distance is to be calculated.
     * @return The distance between this light and the point.
     */
    double getDistance(Point newPoint);
}