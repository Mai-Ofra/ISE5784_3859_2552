package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Simple implementation of a ray tracer.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;


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
     * @param ray the ray that intersects the point
     * @return the color at the given point
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the local effects of lighting at a given point.
     * @param geoPoint the point at which to calculate the local effects
     * @param ray the ray that intersects the point
     * @return the color resulting from the local effects
     */
    private Color calcLocalEffects(Intersectable.GeoPoint geoPoint, Ray ray) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirections();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return scene.background;

        Material mat = geoPoint.geometry.getMaterial();
        Color color = geoPoint.geometry.getEmission();

        for (LightSource light : scene.lights) {
            Vector l = light.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0&& unshaded(geoPoint,l,n)) {
                Color lightIntensity = light.getIntensity(geoPoint.point);
                color = color.add(lightIntensity.scale(
                        calcDiffusive(mat, nl).add(calcSpecular(mat, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the specular reflection component of lighting.
     * @param mat the material properties of the intersected geometry
     * @param n the normal vector at the intersection point
     * @param l the direction vector from the light source to the intersection point
     * @param nl the dot product of the normal vector and the light direction vector
     * @param v the direction vector from the viewer to the intersection point
     * @return the specular reflection component
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.add(n.scale(nl).scale(-2));
        return mat.ks.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), mat.Shininess));
    }

    /**
     * Calculates the diffuse reflection component of lighting.
     * @param mat the material properties of the intersected geometry
     * @param nl the dot product of the normal vector and the light direction vector
     * @return the diffuse reflection component
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kd.scale(Math.abs(nl));
    }

    /**
     * Determines whether a given point on a surface is unshaded from a particular light source.
     * @param geoPoint The geometric point on the intersect object.
     * @param l The vector from the light source to the point.
     * @param n The normal vector at the point of intersection.
     * @return true if the point is unshaded, otherwise false.
     */
    private boolean unshaded(Intersectable.GeoPoint geoPoint, Vector l, Vector n)
    {
        Vector lightDirection = l.scale(-1);
        Ray ray = new Ray(geoPoint.point, lightDirection);
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections==null || intersections.isEmpty();
    }
}
