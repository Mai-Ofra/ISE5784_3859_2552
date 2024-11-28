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
A scene contains: name, background color, ambientLight, geometies and lights. In the bulid function you must send the name but all the other have difult values or 
 you can edit them using set functions.
all the variables are public so you can add to the difult values things (like add more light or shapes)
for example:

<div style="background-color: #2d2d2d; padding: 10px; border-radius: 5px;">
<pre style="color: #ffffff;">
<code class="language-java">
Scene scene = new Scene("ThreeBubbles");
// Add geometries to the scene
scene.geometries.add(bubble1, bubble2, bubble3, mirror, floor);
// add light
scene.lights.add(new SpotLight(
    new Color(130, 130, 130),
    new Point(-3, -12, 30),
    new Vector(1, 0, -3)));
// scene.lights.add(new DirectionalLight(new Color(150,150,150), new Vector(0.7, 0, -1)));
scene.background = new Color(200, 200, 200);
</code>
</pre>
</div>













# Wrapping Up
This project was created by two dedicated students as part of our journey into computer graphics and software engineering principles.
Found a bug or have an idea for improvement? Feel free to contact us at:

📧 maihayo18@gmail.com

📧 ofra1levy@gmail.com

We hope you enjoyed exploring the README and running the project. Thanks for stopping by, see you in the next adventure! 🚀



## Usage
Instructions on how to use the project.
## Features
Key features of this project.
**yv**
#installation

1. Clone the repository:
```bash
   git clone https://github.com/username/MyProject.git
   cd MyProject
  npm install
```

# About the project
**Dgesh**

Follow the steps in the [Installation](#installation) section.













## License
