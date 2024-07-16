package lighting;

import primitives.Color;

/**
 * this is class to present light
 */
abstract class Light {
    protected Color intensity;
    protected static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * parameter ctor
     * @param intensity the light intensity
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter of intensity
     * @return the intensity
     */
    public Color getIntensity() {
        return intensity;
    }

}
