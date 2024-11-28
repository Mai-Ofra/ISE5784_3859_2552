<img src="https://github.com/user-attachments/assets/0b046f60-3e42-45ef-b4db-e916fcb00326" alt="Alt text">

[![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-install-c0c0c0?labelColor=7209b7&style=flat&link=https://www.jetbrains.com/idea/download/?source=google&medium=cpc&campaign=EMEA_en_MIDDLE_IDEA_Branded&term=intellij&content=693444343133&gad_source=1&gclid=Cj0KCQiAi_G5BhDXARIsAN5SX7pDfCBJ2zYadohx9IZGgjZgGPRsIDJcxCtEa0yQdI2mI6K11lrYYaoaAmk7EALw_wcB&section=windows)](https://www.jetbrains.com/idea/download/?source=google&medium=cpc&campaign=EMEA_en_MIDDLE_IDEA_Branded&term=intellij&content=693444343133&gad_source=1&gclid=Cj0KCQiAi_G5BhDXARIsAN5SX7pDfCBJ2zYadohx9IZGgjZgGPRsIDJcxCtEa0yQdI2mI6K11lrYYaoaAmk7EALw_wcB&section=windows)

### An academic mini-project showcasing software engineering principles through computer graphics and object-oriented design.
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#installation-guide">Installation guide</a></li>
    <li><a href="#view-our-images">View our images</a></li>
    <li><a href="#now-its-your-turn">Now it's your turn!</a></li>
    <li><a href="#to-sum-up">To sum up</a></li>
  </ol>
</details>

# About The Project
This project was developed during the second year of our Software Engineering degree. Its primary aim was to strengthen our understanding of programming principles and design patterns. The project focused on equipping students with the ability to identify and eliminate code smells and write cleaner and more maintainable code.

The project resulted in a computer graphics framework where you can create shapes, add lighting effects, and apply different material textures to objects. We used ray casting to see how rays interact with objects, added anti-aliasing for smoother images, and implemented threads and adaptive ray casting to speed up performance.  
# Installation guide
To get started with the project, follow these steps:

- #### Download IntelliJ IDEA
  Download and install IntelliJ IDEA from [here](https://www.jetbrains.com/idea/).
- ### Install JDK and JUnit 5.8.1
  Make sure you have JDK (Java Development Kit) installed on your machine. You can download it from [here](https://adoptopenjdk.net/).
  Additionally, ensure that JUnit 5.8.1 is set up for testing. The framework will prompt you to download the relevant JUnit version required for the project when you open it.
# View our images

Before you start creating a new scene, we recommend checking out the [images folder](./images). It contains pictures we've created, which can serve as inspiration for your work.

Here’s an example:  
<div align="center">
  <img src="https://github.com/user-attachments/assets/f9d1fcf8-5ab7-4d0d-84d1-6c6935d1413b" alt="Alt text" height="450" width="450">
</div>

# Now it's your turn!
### How to Use:
To create a scene, you need three basic components: a camera, a light source, and geometric shapes. These elements come together to form a complete scene. Let’s walk through each step.

The project structure is organized for easy navigation. Each folder is named according to its contents. for example the folder [geometries](./src/geometries) contains all the geomeries and the folder [lighting](./src/lighting) contains lights, etc.
### So let's start creating a scene:
A scene contains several components: the name, background color, ambient light, geometries, and lights.

In the `build()` function, you must specify the name of the scene, but all other components have default values. These values can be changed using setter functions.

Since all the variables are public, you can modify the default values directly—such as adding more lights or geometries.
For example:
```java
        // Add geometries to the scene
        scene.geometries.add(bubble1, bubble2, bubble3, mirror, floor);
        //add light
        scene.lights.add(new SpotLight(
                new Color(130, 130, 130),
                new Point(-3, -12, 30),
                new Vector(1, 0, -3)));
        // scene.lights.add(new DirectionalLight(new Color(150,150,150), new Vector(0.7, 0, -1)));
        scene.background = new Color(200, 200, 200);
```
Now that we got the secne we can set the geometries.
### Creating geometries:
The available shapes are triangle, sphere, polygon, and plane. We recommend using tools like [GeoGebra](https://www.geogebra.org/3d) to help with proportions and directions when creating your scene. Each shape has its own builder based on mathematical principles. For example, to create a sphere, you provide a center point and radius, while a plane requires either three points or one point with a vector (a vector is defined by three values, like in math). A polygon is defined by multiple points on the same plane, and for triangle you simply need three points.

You can set various properties for each geometry, including color, material type, transparency (Kt), shininess, and reflection (Kr). 

For example:
```java
//How to create a pink, semi-transparent sphere with a balance of diffuse and specular reflections.
Geometry bubble1 = new Sphere(new Point(4, -10, 3.8), 0.47)
        .setEmission(new Color(137, 21, 59)) // Sets the color (pink)
        .setMaterial(new Material()
            .setKs(0.2)  // Specular reflection coefficient (controls the sharpness of highlights)
            .setKd(0.7)  // Diffuse reflection coefficient (controls how the surface scatters light)
            .setShininess(30)  // Determines how shiny the surface appears
            .setKt(0.5));  // Transparency coefficient (controls how much light passes through)
```
Kd determines how much light is scattered, giving a matte look, and Ks controls how sharp the highlights are for a shinier effect.
### Lights:
The code defines three types of lights: DirectionalLight, PointLight, and SpotLight.

DirectionalLight shines in one direction with no fading over distance. It needs a Color for brightness and a Vector for direction.
PointLight shines from one spot in all directions and gets weaker with distance. It needs a Color and a Point for its position. You can also adjust how fast the light fades (kc, kl, kq).
SpotLight is like PointLight but shines in one direction like a flashlight. It needs a Color, a Point, and a Vector for the direction and also lets you adjust fading.
For example:
```java
        scene.lights.add(new SpotLight(
                new Color(130, 130, 130),
                new Point(-3, -12, 30),
                new Vector(1, 0, -3)));
        // scene.lights.add(new DirectionalLight(new Color(150,150,150), new Vector(0.7, 0, -1)));
```
### Camera:

The Camera class creates a 3D image by shooting rays through pixels on a view plane. To use it, first, create a camera with the Builder and set its position, direction, view size, and distance. Next, connect it to an ImageWriter to save the image and a RayTracer to calculate the colors. Once ready, call renderImage() to process the scene and writeToImage() to save the final result.
For example:
```java
        // Set up the camera
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(4.4, -16, 4.6))
                .setDirection(new Vector(0, 1, -0.2), new Vector(0, 0.2, 1))
                .setViewPlaneDistance(1000)
                .setViewPlaneSize(500, 500)
                .setThreadsCount(4)
                .setImageWriter(new ImageWriter("ThreeBubbles", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene));

        // Render the image
        camera.build()
                .renderImage()
                .writeToImage();
```
### Anti-aliasing 
To improve the visibility and smoothness of images, you can use anti-aliasing. It reduces jagged edges and minimizes pixelation, especially at the boundaries between different objects or colors, making rendered images look more polished and lifelike.

We can see the diffrence in these photos:

<table align="center">
  <tr>
    <td align="center">
      <strong>Before</strong><br>
      <img src="https://github.com/user-attachments/assets/f4b5c0f6-4f9e-4a75-813b-35fe16bf458a" alt="Before Image">
    </td>
    <td align="center">
      <strong>After</strong><br>
      <img src="https://github.com/user-attachments/assets/0b73920b-a17c-42a8-9301-d663119921a7" alt="After Image">
    </td>
  </tr>
</table>
To add anti-aliasing, simply call setAntiAliasing(9) in the camera builder. The number "9" (which in the picture above is 17) represents the number of rays used to smooth the image. A higher value improves quality but increases rendering time. Here's how it's done:

```java
       Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(4.4, -16, 4.6))
                .setDirection(new Vector(0, 1, -0.2), new Vector(0, 0.2, 1))
                ...
                .setAntiAliasing(9)
```
### Runtime improvements
Sometimes the scene has a lot of shapes and lights and you may want to use the picture improvements. all that make the run time to be long.
in order to make it faster you can add two Runtime improvements.
#### Theareds (high improvement)
This feature enables your computer to run multiple threads simultaneously, calculating several pixels at once. You can set it in the camera builder as shown:

```java
Copy code
Camera.Builder camera = Camera.getBuilder()
        .setLocation(new Point(4, -14, 3))
        ...
        .setThreadsCount(4);
```

The number 4 represents how many threads will run concurrently. Avoid setting it too high, as it can slow down performance. We recommend using three or four threads for optimal efficiency.
#### Adaptive rendering
Adaptive rendering adjusts the number of rays cast in each pixel based on the complexity of the scene. It focuses more on detailed areas like edges and less on flat regions, improving efficiency and reducing rendering time.
```java
Copy code
Camera.Builder camera = Camera.getBuilder()
        .setLocation(new Point(4, -14, 3))
        ...
        .setAdaptive(true);
```
# Wrapping Up
We've introduced only the basic functionality of this project, but there's much more to explore! You can also implement functions for additional geometric shapes, such as cylinders and tubes, to make them renderable in the scene.

This project was built by two dedicated students on a journey into computer graphics and software engineering. Found a bug or have an idea for improvement? Feel free to contact us at:

📧 maihayo18@gmail.com
📧 ofra1levy@gmail.com

Thank you for exploring the README and running the project. We hope you enjoyed it—see you on the next adventure! 🚀
