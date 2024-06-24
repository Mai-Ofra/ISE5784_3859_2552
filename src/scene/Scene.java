package scene;

import lighting.AmbientLight;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

import geometries.*;
import primitives.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;

/**
 * The {@code Scene} class represents a 3D scene containing various geometric shapes,
 * ambient light, and background color.
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a new {@code Scene} with the specified name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }


    /*-----------------Setters---------------------------------*/

    /**
     * Sets the background color of the scene.
     *
     * @param background the new background color
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the new ambient light
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries the new collection of geometries
     * @return the current {@code Scene} instance for chaining
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     *
     * @param lights the list of light sources to set
     * @return the current Scene object with updated light sources
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Reads scene configuration from an XML file and initializes the scene accordingly.
     * Parses background color, ambient light, and geometries from the XML structure.
     * @param filePath The path to the XML file containing scene configuration.
     */
    public void readFromXML(String filePath) {
        try {
            // Initialize the XML parser
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Parse background color
            Element sceneElement = (Element) doc.getElementsByTagName("scene").item(0);
            Color backgroundColor = readColor(sceneElement, "background-color");
            setBackground(backgroundColor);

            // Parse ambient light
            Element ambientLightElement = (Element) doc.getElementsByTagName("ambient-light").item(0);
            Color ambientColor = readColor(ambientLightElement, "color");
            double k = Double.parseDouble(ambientLightElement.getAttribute("k"));
            AmbientLight ambientLight = new AmbientLight(ambientColor, k);
            setAmbientLight(ambientLight);

            // Parse geometries
            NodeList geometryList = doc.getElementsByTagName("geometries").item(0).getChildNodes();
            createNodeList(geometryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Recursively parses the geometry elements from the XML
     * @param geometryList The NodeList containing geometry elements to be parsed.
     */
    public void createNodeList(NodeList geometryList) {
        Color emission;
        try {
            for (int i = 0; i < geometryList.getLength(); i++) {
                if (geometryList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) geometryList.item(i);
                    switch (geometryElement.getTagName()) {
                        case "geometries":
                            createNodeList(geometryElement.getChildNodes());
                            break;
                        case "sphere":
                            Point center = readPoint(geometryElement, "center");
                            double radius =
                                    Double.parseDouble(geometryElement.getAttribute("radius"));
                            emission = readColor(geometryElement, "emission");
                            if (emission != null) {
                                geometries.add(new Sphere(center, radius).setEmission(emission));
                            } else
                                geometries.add(new Sphere(center, radius));
                            break;
                        case "triangle":
                            Point p0 = readPoint(geometryElement, "p0");
                            Point p1 = readPoint(geometryElement, "p1");
                            Point p2 = readPoint(geometryElement, "p2");
                            emission = readColor(geometryElement, "emission");
                            if (emission != null) {
                                geometries.add(new Triangle(p0, p1, p2).setEmission(emission));
                            } else
                                geometries.add(new Triangle(p0, p1, p2));
                            break;
                        case "plane":
                            String[] normalComponents =
                                    geometryElement.getAttribute("normal").split(" ");
                            Point p = readPoint(geometryElement, "p");
                            Vector normal = new Vector(
                                    Double.parseDouble(normalComponents[0]),
                                    Double.parseDouble(normalComponents[1]),
                                    Double.parseDouble(normalComponents[2])
                            );
                            emission = readColor(geometryElement, "emission");
                            if (emission != null) {
                                geometries.add(new Plane(p, normal).setEmission(emission));
                            } else
                                geometries.add(new Plane(p, normal));
                            break;
                        case "polygon":
                            emission = readColor(geometryElement, "emission");
                            List<Point> vertices =readVertices(geometryElement, "vertices");
                            if (emission != null) {
                                geometries
                                        .add(new Polygon(vertices.toArray(new Point[0])).setEmission(emission));
                            } else
                                geometries.add(new Polygon(vertices .toArray(new Point[0]))
                                );
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses and retrieves a Color object from the specified attribute of the given XML element.
     * @param element       The XML element from which to read the color attribute.
     * @param attributeName The name of the attribute containing the color components.
     * @return A Color object parsed from the attribute, or null if parsing fails.
     */
    private Color readColor(Element element, String attributeName) {
        String[] colorComponents = element.getAttribute(attributeName).split(" ");
        if (colorComponents.length == 3 && !colorComponents[0].isEmpty()) {
            return new Color(
                    Integer.parseInt(colorComponents[0]),
                    Integer.parseInt(colorComponents[1]),
                    Integer.parseInt(colorComponents[2])
            );
        }
        return null;
    }

    /**
     * Parses and retrieves a list of Point objects representing vertices from the specified attribute
     * of the given XML element.
     * @param element       The XML element from which to read the vertices attribute.
     * @param attributeName The name of the attribute containing the vertices coordinates.
     * @return A list of Point objects representing the vertices of a polygon or similar geometry.
     */
    private List<Point> readVertices(Element element, String attributeName) {
        String[] verticesComponents = element.getAttribute(attributeName).split(" ");
        List<Point> vertices = new LinkedList<>();
        for (int j = 0; j < verticesComponents.length; j += 3) {
            vertices.add(new Point(
                    Double.parseDouble(verticesComponents[j]),
                    Double.parseDouble(verticesComponents[j + 1]),
                    Double.parseDouble(verticesComponents[j + 2])
            ));
        }
        return vertices;
    }

    /**
     * Parses and retrieves a Point object from the specified attribute of the given XML element.
     * @param element       The XML element from which to read the point attribute.
     * @param attributeName The name of the attribute containing the point coordinates.
     * @return A Point object parsed from the attribute.
     */
    private Point readPoint(Element element, String attributeName) {
        String[] pointComponents = element.getAttribute(attributeName).split(" ");
        return new Point(
                Double.parseDouble(pointComponents[0]),
                Double.parseDouble(pointComponents[1]),
                Double.parseDouble(pointComponents[2])
        );
    }
}
