package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    final public static AmbientLight NONE= new AmbientLight(Color.BLACK,0);
    final private Color intensity;

    public AmbientLight(Color IA, Double3 KA) {
        intensity= IA.scale(KA);
    }
    public AmbientLight(Color IA, double KA) {
        intensity= IA.scale(KA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
