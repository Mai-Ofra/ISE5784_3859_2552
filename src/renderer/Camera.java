package renderer;
import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class to represent a 3D camera.
 */
public class Camera implements Cloneable{
    /**
     * Builder class for Camera to support the Builder design pattern.
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Default constructor that initializes a new Camera instance.
         */
        public Builder() {
            camera = new Camera();
        }

        /**
         * Constructor that initializes the builder with an existing Camera instance.
         *
         * @param camera the Camera instance to initialize the builder with
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the location of the camera.
         * @param p the location point
         * @return the current Builder instance for chaining
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         * @param vTo the forward direction vector
         * @param vUp the up direction vector
         * @return the current Builder instance for chaining
         * @throws IllegalArgumentException if the vectors are not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("Vectors must be orthogonal");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the current Builder instance for chaining
         * @throws IllegalArgumentException if height or width is not greater than zero
         */
        public Builder setViewPlaneSize(double width, double height) {
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance of the view plane from the camera.
         * @param distance the distance value
         * @return the current Builder instance for chaining
         * @throws IllegalArgumentException if the distance is not greater than zero
         */
        public Builder setViewPlaneDistance(double distance) {
            camera.distance=distance;
            return this;
        }

    /**
     * Builds and returns the Camera instance.
     * @return the constructed Camera instance
     * @throws MissingResourceException   if any required field is missing
     * @throws IllegalArgumentException   if the direction vectors are parallel
     */
        public Camera build() {
            final String MISSING_RENDER_DATA_ERROR = "Missing rendering data";
            //making sure all the values were received
            if (isZero(camera.height))
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing height value", Camera.class.getName());
            if (isZero(camera.width))
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing width value", Camera.class.getName());
             if (isZero(camera.distance))
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing distance value", Camera.class.getName());
            if (camera.vTo == null)
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing vTo value", Camera.class.getName());
            if (camera.vUp == null)
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing vUp value", Camera.class.getName());
            if (camera.p0 == null)
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing p0 value", Camera.class.getName());
            //make sure all values are correct
            if (alignZero(camera.height) <= 0 || alignZero(camera.width) <= 0)
                throw new IllegalArgumentException("Height and width must be greater than zero");
            if (alignZero(camera.distance) <= 0)
                throw new IllegalArgumentException("Distance must be greater than zero");
            //making sure that the vectors are normalized
            if(camera.vUp.length()!=1)
                camera.vUp=camera.vUp.normalize();
            if(camera.vTo.length()!=1)
                camera.vTo=camera.vTo.normalize();
            //calc the vRight
            camera.vRight=camera.vTo.crossProduct(camera.vUp).normalize();
            try {
                return  (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Vector vTo = null;
    private Vector vUp = null;
    private Vector vRight;
    private Point p0 = null;
    private double height = 0d;
    private double width = 0d;
    private double distance = 0d;

    /**
     * private empty ctor
     */
    private Camera() {
    }


    /**
     * Gets a new Builder instance for Camera.
     * @return a new Builder instance
     */
    public static Builder getBuilder(){
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        //the center point of the view plane
        Point pc= p0.add(vTo.scale(distance));

        if (nY == 0 || nX == 0) {
            throw new IllegalArgumentException("It is impossible to divide by 0");
        }

        //the size of the height and width of a pixel
        double Ry = height/nY;
        double Rx = width/nX;

        //calculate the number of steps right left up and down
        double Yi= -(i- (double) (nY - 1) /2)*Ry;
        double Xj= (j- (double) (nX - 1) /2)*Rx;

        Point pIJ=pc;
        if(!isZero(Xj))
            pIJ=pIJ.add(vRight.scale(Xj));
        if(!isZero(Yi))
            pIJ=pIJ.add(vUp.scale(Yi));
        return new Ray(p0,pIJ.subtract(p0));
    }
  /**-----------------Getters---------------------------------*/
    public Vector getvTo() {
        return vTo;
    }
    public Vector getvUp() {
        return vUp;
    }
    public Vector getvRight() {
        return vRight;
    }
    public Point getP0() {
        return p0;
    }
    public double getWidth() {
        return width;
    }
    public double getheight() { return height; }
    public double getDistance() { return distance; }
}