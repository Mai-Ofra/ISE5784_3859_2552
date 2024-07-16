package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class to represent a 3D camera.
 */
public class Camera implements Cloneable {
    Random rand = new Random();
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
         *
         * @param p the location point
         * @return the current Builder instance for chaining
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
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
         *
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
         *
         * @param distance the distance value
         * @return the current Builder instance for chaining
         * @throws IllegalArgumentException if the distance is not greater than zero
         */
        public Builder setViewPlaneDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter instance
         * @return the current Builder instance for chaining
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setMultySamples(int numSamples,int interval) {
            int powerOfTwo = numSamples - 1;
            while (powerOfTwo > 0 && powerOfTwo % 2 == 0) {
                powerOfTwo = powerOfTwo / 2;
            }
            if (powerOfTwo == 0) {
                camera.numSamples = numSamples;
            } else {
                int newNumSamples = 2;
                while (newNumSamples < numSamples - 1) {
                    newNumSamples *= 2;
                }
                camera.numSamples = (newNumSamples + 1);
            }
            if(interval<numSamples)
                throw new IllegalArgumentException("internal must be bigger than num of samples");
            camera.interval=interval;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracerBase instance
         * @return the current Builder instance for chaining
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required field is missing
         * @throws IllegalArgumentException if the direction vectors are parallel
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
            if (camera.imageWriter == null)
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing imageWriter value", Camera.class.getName());
            if (camera.rayTracer == null)
                throw new MissingResourceException(MISSING_RENDER_DATA_ERROR,
                        "Missing rayTracer value", Camera.class.getName());
            //make sure all values are correct
            if (alignZero(camera.height) <= 0 || alignZero(camera.width) <= 0)
                throw new IllegalArgumentException("Height and width must be greater than zero");
            if (alignZero(camera.distance) <= 0)
                throw new IllegalArgumentException("Distance must be greater than zero");
            //making sure that the vectors are normalized
            if (camera.vUp.length() != 1)
                camera.vUp = camera.vUp.normalize();
            if (camera.vTo.length() != 1)
                camera.vTo = camera.vTo.normalize();
            //calc the vRight
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private Point p0;
    private double height = 0d;
    private double width = 0d;
    private double distance = 0d;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numSamples = 1;
    private int interval = 1;

    /**
     * private empty ctor
     */
    private Camera() {
    }


    /**
     * Gets a new Builder instance for Camera.
     *
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a given pixel in the view plane.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the pixel's column index
     * @param i  the pixel's row index
     * @return the constructed Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //the center point of the view plane
        Point pc = p0.add(vTo.scale(distance));

        if (isZero(nY) || isZero(nX)) {
            throw new IllegalArgumentException("It is impossible to divide by 0");
        }

        //the size of the height and width of a pixel
        double Ry = height / nY;
        double Rx = width / nX;

        //calculate the number of steps right left up and down
        double Yi = -(i - (double) (nY - 1) / 2) * Ry;
        double Xj = (j - (double) (nX - 1) / 2) * Rx;

        Point pIJ = pc;
        if (!isZero(Xj))
            pIJ = pIJ.add(vRight.scale(Xj));
        if (!isZero(Yi))
            pIJ = pIJ.add(vUp.scale(Yi));
        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Renders the image by casting rays through each pixel and tracing them.
     */
    public Camera renderImage() {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        for (int i = 0; i < Nx; i++)
            for (int j = 0; j < Ny; j++)
                castRay(Nx, Ny, j, i);
        return this;
    }

    /**
     * Prints a grid on the image with the given interval and color.
     *
     * @param interval the interval between grid lines
     * @param color    the color of the grid lines
     */
    public Camera printGrid(int interval, Color color) {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for (int i = 0; i < Nx; i += interval)
            for (int j = 0; j < Ny; j++)
                imageWriter.writePixel(i, j, color);

        for (int i = 0; i < Ny; i += interval)
            for (int j = 0; j < Nx; j++)
                imageWriter.writePixel(j, i, color);
        writeToImage();
        return this;
    }

    /**
     * Writes the rendered image to a file.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Casts a ray through a specific pixel and writes the traced color to that pixel.
     *
     * @param Nx the number of pixels in the x direction
     * @param Ny the number of pixels in the y direction
     * @param i  the pixel's column index
     * @param j  the pixel's row index
     */
    private void castRay(int Nx, int Ny, int i, int j) {
        Color color;
        Ray ray;
        ray = constructRay(Nx, Ny, i, j);
        color = rayTracer.traceRay(ray);
        if (numSamples == 1 || interval == 1) {
            imageWriter.writePixel(i, j, color);
        } else {
            Point center=new Point(i,j,0);
            Point positionRay;
            color=color.scale((numSamples*numSamples)/3);
            int count=(numSamples*numSamples)/3;
            int left = Math.max(i - interval/2, 0);
            int right = Math.min(i +interval/2, Nx);
            int top = Math.max(j - interval/2, 0);
            int bottom = Math.min(j + interval/2, Ny);
            for (int k = left; k < right; k+=interval/numSamples)
                for (int l = top; l < bottom; l+= interval/numSamples) {
//                    int randomK = rand.nextInt( Math.max(k-interval/numSamples/2,0), Math.min(k+interval/numSamples/2,right)+1);
//                    int randomL = rand.nextInt(Math.max(l-interval/numSamples/2,0), Math.min(l+interval/numSamples/2,bottom)+1);
                      positionRay=new Point(k,l,0);
                    ray = constructRay(Nx, Ny, k,l);
                   double distance =positionRay.distance(center);
//                    if(distance<= (double) interval /6)
//                    {
//                        color=color.add(rayTracer.traceRay(ray).scale(3));
//                        count+=3;
//                    } else if (distance<=interval/3) {
//                        color=color.add(rayTracer.traceRay(ray).scale(2));
//                        count+=2;
//                    } else
                        if (distance<= (double) interval /2) {
                        color=color.add(rayTracer.traceRay(ray));
                        count++;
                    }
                }
            imageWriter.writePixel(i, j, color.scale(1.0/count));
        }
    }

    /**
     * -----------------Getters---------------------------------
     */
    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public Point getP0() {
        return p0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
}