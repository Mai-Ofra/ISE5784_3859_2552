package lighting;

import primitives.*;
/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight {
    final public static AmbientLight NONE= new AmbientLight(Color.BLACK,0);
    final private Color intensity;
    /**
     * Constructs an ambient light with the given intensity and scale factor.
     * @param IA the intensity of the ambient light
     * @param KA the scale factor for the intensity
     */
    public AmbientLight(Color IA, Double3 KA) {
        intensity= IA.scale(KA);
    }
    /**
     * Constructs an ambient light with the given intensity and scale factor.
     * @param IA the intensity of the ambient light
     * @param KA the scale factor for the intensity
     */
    public AmbientLight(Color IA, double KA) {
        intensity= IA.scale(KA);
    }
    /**
     * Returns the intensity of the ambient light.
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}