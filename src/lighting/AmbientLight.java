package lighting;

import primitives.*;
/**
 * Represents the ambient light in a scene.
 */
public class AmbientLight extends  Light {
    final public static AmbientLight NONE= new AmbientLight(Color.BLACK,0);

    /**
     * Constructs an ambient light with the given intensity and scale factor.
     * using the father ctor
     * @param intensity the intensity of the ambient light
     * @param KA the scale factor for the intensity
     */
    public AmbientLight(Color intensity, Double3 KA) {
        super(intensity.scale(KA));
    }

    /**
     * Constructs an ambient light with the given intensity and scale factor.
     * using the father ctor
     * @param IA the intensity of the ambient light
     * @param KA the scale factor for the intensity
     */
    public AmbientLight(Color IA, double KA) {
        super(IA.scale(KA));
    }

}