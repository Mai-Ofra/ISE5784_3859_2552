package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase{

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> pointList = scene.geometries.findIntersections(ray);
        if(pointList==null)
            return scene.background;
        return calcColor(ray.findClosestPoint(pointList));

    }
    private Color calcColor(Point point)
    {
        return scene.ambientLight.getIntensity();
    }
}
