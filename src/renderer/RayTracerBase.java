package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracers.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor that initializes the ray tracer with a scene.
     *
     * @param scene the scene to be used for ray tracing
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method to trace a ray and determine its color.
     *
     * @param ray the ray to be traced
     * @return the color of the ray
     */
    public abstract Color traceRay(Ray ray);
}
