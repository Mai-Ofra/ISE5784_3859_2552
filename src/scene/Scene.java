package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * The {@code Scene} class represents a 3D scene containing various geometric shapes,
 * ambient light, and background color.
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK ;
    public AmbientLight ambientLight= AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    /**
     * Constructs a new {@code Scene} with the specified name.
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     * @param background the new background color
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     * @param ambientLight the new ambient light
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     * @param geometries the new collection of geometries
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
