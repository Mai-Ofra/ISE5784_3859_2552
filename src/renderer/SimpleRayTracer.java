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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Constructor that initializes the simple ray tracer with a scene.
     *
     * @param scene the scene to be used for ray tracing
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Intersectable.GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at a given point.
     *
     * @param geoPoint the point at which to calculate the color
     * @param ray      the ray that intersects the point
     * @return the color at the given point
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given intersection point, considering local and global effects.
     *
     * @param geoPoint The intersection point.
     * @param ray      The ray that caused the intersection.
     * @param level    The recursion level for global effects.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(Intersectable.GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return scene.background;
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color :
                color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the local effects of lighting at a given point.
     *
     * @param geoPoint the point at which to calculate the local effects
     * @param ray      the ray that intersects the point
     * @return the color resulting from the local effects
     */
    private Color calcLocalEffects(Intersectable.GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) return scene.background;

        Material mat = geoPoint.geometry.getMaterial();
        Color color = geoPoint.geometry.getEmission();

        for (LightSource light : scene.lights) {
            Vector l = light.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {
                Double3 ktr = transparency(geoPoint, light, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = light.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(lightIntensity.scale(
                            calcDiffusive(mat, nl).add(calcSpecular(mat, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the global effects (reflection and refraction) at a given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The ray that caused the intersection.
     * @param level    The recursion level for global effects.
     * @return The color at the intersection point, considering global effects.
     */
    private Color calcGlobalEffects(Intersectable.GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint, ray), level, k, material.kt)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint, ray), level, k, material.kr));
    }

    /**
     * Calculates the color contribution from a single global effect
     * (either reflection or refraction).
     * recursive function
     *
     * @param ray   The ray representing the global effect.
     * @param level The recursion level for global effects.
     * @return The color contribution from the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        Intersectable.GeoPoint geoPoint = findClosestIntersection(ray);
        return (geoPoint == null ? scene.background : calcColor(geoPoint, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Constructs a refracted ray at the given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The original ray.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(Intersectable.GeoPoint geoPoint, Ray ray) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        return new Ray(geoPoint.point, ray.getDirection(), n);
    }

    /**
     * Constructs a reflected ray at the given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The original ray.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(Intersectable.GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        return new Ray(geoPoint.point, v.subtract(n.scale(2 * nv)).normalize(), n);
    }

    /**
     * Finds the closest intersection point for a given ray.
     *
     * @param ray The ray to check for intersections.
     * @return The closest intersection point, or null if no intersections are found.
     */
    protected Intersectable.GeoPoint findClosestIntersection(Ray ray) {
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return (intersections == null || intersections.isEmpty()) ?
                null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the specular reflection component of lighting.
     *
     * @param mat the material properties of the intersected geometry
     * @param n   the normal vector at the intersection point
     * @param l   the direction vector from the light source to the intersection point
     * @param nl  the dot product of the normal vector and the light direction vector
     * @param v   the direction vector from the viewer to the intersection point
     * @return the specular reflection component
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.add(n.scale(nl).scale(-2));
        return mat.ks.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), mat.Shininess));
    }

    /**
     * Calculates the diffuse reflection component of lighting.
     *
     * @param mat the material properties of the intersected geometry
     * @param nl  the dot product of the normal vector and the light direction vector
     * @return the diffuse reflection component
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.kd.scale(Math.abs(nl));
    }

    /**
     * Calculates the transparency factor at a given intersection point,
     * considering the objects between the point and the light source.
     *
     * @param geoPoint    The intersection point.
     * @param lightSource The light source being considered.
     * @param l           The direction vector from the intersection point to the light source.
     * @param n           The normal vector at the intersection point.
     * @return The transparency factor as a Double3 object.
     */
    private Double3 transparency(Intersectable.GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        Ray ray = new Ray(geoPoint.point, lightDirection, n);
        List<Intersectable.GeoPoint> intersections = scene.geometries
                .findGeoIntersections(ray, lightSource.getDistance(geoPoint.point));
        if (intersections == null || intersections.isEmpty())
            return Double3.ONE;
        Double3 Ktr = Double3.ONE;
        for (Intersectable.GeoPoint intersect : intersections) {
            Ktr = Ktr.product(intersect.geometry.getMaterial().kt);
            // Check if ktr is close to 0
            if (Ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return Ktr;
    }
}
