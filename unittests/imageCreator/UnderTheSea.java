package imageCreator;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

public class UnderTheSea {
    /** Scene for the tests */
    private final Scene          scene         = new Scene("under the sea scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection( Vector.X,Vector.Z)
            .setRayTracer(new SimpleRayTracer(scene));
    @Test
    void testUnderTheSea() {

    }
}
