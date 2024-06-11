package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Simple implementation of a ray tracer.
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * Constructor that initializes the simple ray tracer with a scene.
     * @param scene the scene to be used for ray tracing
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and determines its color.
     * @param ray the ray to be traced
     * @return the color of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Intersectable.GeoPoint> pointList = scene.geometries.findGeoIntersections(ray);
        return pointList == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(pointList));
    }

    /**
     * Calculates the color at a given point.
     * @param geoPoint the point at which to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint)
    {
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }
}
