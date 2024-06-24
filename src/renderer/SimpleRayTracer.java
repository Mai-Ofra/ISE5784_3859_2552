package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

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
        Double3 diffuseCoefficient = geoPoint.geometry.getMaterial().kd;
        Double3 specularCoefficient = geoPoint.geometry.getMaterial().ks;
        double shininess = geoPoint.geometry.getMaterial().Shininess;

        // Calculate normal and view vectors
        Vector normalVector = geoPoint.geometry.getNormal(geoPoint.point);
        Vector viewVector = ray.getDirections();

        // Initialize accumulated light addition with black color
        Color accumulatedLight = Color.BLACK;

        // Iterate through all light sources in the scene
        List<LightSource> lightSources = scene.lights;
        for (LightSource lightSource : lightSources) {
            // Calculate light vector and reflection vector
            Vector lightVector = lightSource.getL(geoPoint.point);
            Vector reflectionVector = lightVector.add(normalVector.scale(lightVector.dotProduct(normalVector)).scale(-2));

            // Calculate dot products
            double lightDotNormal = Math.abs(lightVector.dotProduct(normalVector));
            double viewDotNormal = viewVector.dotProduct(normalVector);

            // Check if light and view vectors are on the same side of the surface
            if (lightVector.dotProduct(normalVector) * viewDotNormal >= 0) {
                // Calculate specular and diffuse components
                Double3 specularComponent = specularCoefficient.scale(Math.pow(Math.max(0, viewVector.scale(-1).dotProduct(reflectionVector)), shininess));
                Double3 diffuseComponent = diffuseCoefficient.scale(lightDotNormal);

                // Get light intensity and add to accumulated light
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                accumulatedLight = accumulatedLight.add(lightIntensity.scale(specularComponent.add(diffuseComponent)));
            }
        }

        // Return the final color including ambient light and emission
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(accumulatedLight);
    }
}