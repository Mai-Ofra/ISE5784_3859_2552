package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Simple implementation of a ray tracer.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructor that initializes the simple ray tracer with a scene.
     *
     * @param scene the scene to be used for ray tracing
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and determines its color.
     *
     * @param ray the ray to be traced
     * @return the color of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Intersectable.GeoPoint> pointList = scene.geometries.findGeoIntersections(ray);
        return pointList == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(pointList), ray);
    }

    /**
     * Calculates the color at a given point.
     *
     * @param geoPoint the point at which to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray ray) {
        // Retrieve material properties
        Double3 Kd = geoPoint.geometry.getMaterial().kd;
        Double3 Ks = geoPoint.geometry.getMaterial().ks;
        double nsh = alignZero(geoPoint.geometry.getMaterial().Shininess);

        // Calculate normal and view vectors
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector V = ray.getDirections();

        // Initialize pongAddition with black color (no color)
        Color pongAddition = Color.BLACK;

        // Iterate through all light sources in the scene
        List<LightSource> lights = scene.lights;
        for (LightSource light : lights) {

            Vector l = light.getL(geoPoint.point);
            Vector r = l.add(n.scale(l.dotProduct(n)).scale(-2));

            double LxN = alignZero(Math.abs(l.dotProduct(n)));
            double VxN = alignZero(V.dotProduct(n));

            // Check if light and view vectors are on the same side of the surface
            if (l.dotProduct(n) * VxN >= 0) {
                // Calculate specular and diffuse components
                Double3 specular = Ks.scale(Math.pow(Math.max(0, V.scale(-1).dotProduct(r)), nsh));
                Double3 diffuse = Kd.scale(LxN);

                // Get light intensity and add to pongAddition
                Color lightIntensity = light.getIntensity(geoPoint.point);
                pongAddition = pongAddition.add(lightIntensity.scale(specular.add(diffuse)));
            }
        }

        // Return the final color including ambient light and emission
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(pongAddition);
    }
}