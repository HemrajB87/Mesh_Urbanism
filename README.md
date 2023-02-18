# Assignment A2: Mesh Generator

  - Alvin Qian [qiana2@mcmaster.ca]
  - Addison Chan #2 [chana110@mcmaster.ca]
  - Hemraj Bhatt [bhatth14@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar test1.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/test1.mesh test1.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

A feature is considered done if it is supported in both the generator and visualizer. In other words if we can generate and visualize what we generated.

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| 01 | Visualizes 2x2 squares in a 10x10 canvas by drawing segments in between vertices  | Addison | 02/16/2023 | 02/17/2023 | D |
| 02 | Create mesh of polygons   | Hemraj |  |  |  |
| 03 | Create mesh of polygons that have their centroid vertex displayed | Hemraj |  |  |  |
| 04 | Create mesh of polygons with centroids that make a reference to neighbouring polygons   | Hemraj |  |  |  |
| 05 |  Adding a transparency property to the vertices, segments and polygons | Addison | 02/18/2023 | 02/19/2023 | D |
| 06 |  Adding a thickness property to the vertices, segments and polygons | Addison | 02/19/2023 |  |  |
| 07 |  Rendering a mesh with determined properties  | Alvin |  |  |  |
| 08 |  Render a "debug" mesh with black polygons, a red centroid, and light gray neighboring sides. | Alvin |  |  |  |
