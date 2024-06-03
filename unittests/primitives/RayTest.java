package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Test ==============
        // TC01: when the distance is positive
        Ray ray = new Ray(new Point(1,0,0),new Vector(1,0,0));
        double t = -2;
        assertEquals(new Point(-1,0,0),ray.getPoint(t),"Wrong point");

        // TC01: when the distance is positive
        double t2=1;
        assertEquals(new Point(2,0,0),ray.getPoint(t2),"Wrong point");

        // =============== Boundary Values Test ==================
        // TC11: when the distance is zero
        double t3=0;
        assertEquals(new Point(1,0,0),ray.getPoint(t3),"Wrong point");

    }
}