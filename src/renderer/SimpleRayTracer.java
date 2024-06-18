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
                : calcColor(ray.findClosestGeoPoint(pointList), ray);
    }

    /**
     * Calculates the color at a given point.
     * @param geoPoint the point at which to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray ray)
    {
        Double3 Kd = geoPoint.geometry.getMaterial().kd;
        Double3 Ks = geoPoint.geometry.getMaterial().ks;
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        Vector V= ray.getDirections();
        double nsh = geoPoint.geometry.getMaterial().Shininess;
        Color pongAddition=Color.BLACK;
        List<LightSource> lights= scene.lights;
        for (LightSource light : lights) {
            Vector l = light.getL(geoPoint.point);
            Vector r = l.add(normal.scale(l.dotProduct(normal)).scale(-2));
            double LxN = Math.abs(l.dotProduct(normal));
            if(l.dotProduct(normal)*V.dotProduct(normal)>=0) {
                Double3 specular = Ks.scale(Math.pow(Math.max(0, V.scale(-1).dotProduct(r)), nsh));
                Double3 diffuse = Kd.scale(LxN);
                Color lightIntensity = light.getIntensity(geoPoint.point);
                pongAddition = pongAddition.add((lightIntensity)
                        .scale(specular.add(diffuse)));
            }
            }
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission()).add(pongAddition);
    }
}
