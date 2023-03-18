# Assignment A2: Mesh Generator

  - Alvin Qian [qiana2@mcmaster.ca]
  - Addison Chan [chana110@mcmaster.ca]
  - Hemraj Bhatt [bhatth14@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.
Here are some ways to use the generator:
The user can find the meaning of all the parameters by executing the command: java -jar generator/generator.jar -help
```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/irregular.mesh
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).
Here are some ways to use the visualizer (the parameter -x allows the user to see the mesh in debug mode):

```
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg          
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg   
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x

```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.


## Part 2: Scenario
To demonstrate "Part 2" user can run the project as instructed. If user includes "-X" when running visualizer code to generate mesh, 
the mesh will be generated in which the polygons(and associated segments and vertices) are displayed in black, centroids in red, and neighbourhood
relationships in light grey. If the "-X" is not present then the colors and thickness will be randomized. 

##Island
To change the mesh we have generator from the generator subproject we can execute the island sub project.
Here are some ways to use island:

```
java -jar island/island.jar -i img/test.mesh -o img/island1.mesh -mode lagoon -shape circle
java -jar island/island.jar -i img/test.mesh -o img/island1.mesh -mode lagoon -shape star
java -jar island/island.jar -i img/test.mesh -o img/island1.mesh -mode lagoon -shape triangle

```
## Backlog

### Definition of Done

A feature is considered done if it is supported in both the generator and visualizer. In other words if we can generate and visualize what we generated.

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| 01 | Visualizes 2x2 squares in a 10x10 canvas by drawing segments in between vertices  | Addison | 02/16/2023 | 02/17/2023 | D |
| 02 | Create mesh of polygons   | Hemraj | 02/18/2023 | 02/20/2023 | D |
| 03 | Create mesh of polygons that have their centroid vertex displayed | Hemraj | 02/19/2023 | 02/20/2023 | D |
| 04 | Create mesh of polygons with centroids that make a reference to neighbouring polygons   | Hemraj/Addison | 02/20/23 | 02/22/23 | D |
| 05 |  Adding a transparency property to the vertices, segments and polygons | Addison | 02/18/2023 | 02/19/2023 | D |
| 06 |  Adding a thickness property to the vertices, segments and polygons | Addison | 02/19/2023 | 02/21/2023 | D |
| 07 |  Rendering a mesh with determined properties  | Alvin | 02/21/2023 | 02/21/2023 | D |
| 08 |  Render a "debug" mesh with black polygons, a red centroid, and light gray neighboring sides. | Alvin | 02/19/2023 | 02/22/2023 | D |
| 09 | Create a minimal mesh of vertices, segments, polygons (no duplicates of anything when creating the mesh) | Addison | 02/20/2023 | 02/22/2023 | D |
| 10 | Generating random vertices in DotGen class | Hemraj/Addison/Alvin | 02/23/2023 | 02/24/2023 | D |
| 11 | Creating Voronoi diagrams using generated points | Addison | 02/23/2023 | 02/25/2023 | D |
| 12 | Apply Lloyd relaxation, centroids | Hemraj/Addison | 02/25/2023 | 02/23/2023 | D |
| 13 | Apply Lloyd relaxation, creating smooth points for diagrams  | Hemraj/Addison | 02/25/2023 | 02/25/2023 | D |
| 14 | Apply Lloyd relaxation a random amount of times | Hemraj/Addison | 02/23/2023 | 02/25/2023 | D |
| 15 | Crop mesh diagram to expected size | Addison | 02/23/2023 | 02/25/2023 | D |
| 16 | Compute neighboring polygons using Delaunay’s triangulation | Hemraj | 02/25/2023 | 02/27/2023 | D |
| 17 | Reordering segments using Convex Hull  | Alvin | 02/26/2023 | 02/27/2023 | D |
| 18 | User controlled mesh generation using CLI | Alvin | 02/23/2023 | 02/25/2023 | D |

### Island Generation Backlog
| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| 19 | Generate a circle island in the middle of the canvas that has land tiles and ocean tiles  | Addison | 03/14/2023 | 03/15/2023 | D |
| 20 | Island has an inner and outer circle to differentiate between ocean and lagoon tiles | Addison | 03/15/2023 | 03/16/2023 | D |
| 21 | Island generates beach tiles when a land tile is next to a lagoon or ocean tile | Addison | 03/16/2023 | 03/16/2023 | D |
| 22 | User can pass in a -shape argument to change the shape of the island | Addison | 03/17/2023 | 03/18/2023 | D |

