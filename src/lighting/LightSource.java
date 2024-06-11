package lighting;
import primitives.*;
///לא ממממממתתתוווועעעעעדדדדדד  אמן
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

    public Vector getL(Point p);

}
