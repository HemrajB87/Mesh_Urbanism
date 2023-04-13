# Assignment A4: Pathfinder

  - Hemraj Bhatt [bhatth14@mcmaster.ca]

## How to run the product


### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A4`

To install the different tooling on your computer, simply run:

```
mvn compile
mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, a file named `visualizer.jar` in the `visualizer` one, and a file named `pathfinder.jar` in the `pathfinder` one

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.
Here are some ways to use the generator:
The user can find the meaning of all the parameters by executing the command: java -jar generator/generator.jar -help
```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o img/irregular.mesh
```

### Island
To change the mesh we have generator from the generator subproject we can execute the island sub project.
Here are some ways to use island:
#### If this step not create a .mesh file, please fix IDE, run mvn clean install and mvn compile (technical debt) (could not figue out why code worked only certain times)

```
java -jar island/island.jar -i x -o x -mode x -shape x -altitude x -lakes x -aquifers x -seed x

x == Place holder
-i: Input mesh path (ex: img/irregular.mesh)
-o: Desiserd output mesh path (ex: img/output.mesh)
-mode: Type of island (ex: lagoon, plain, ect)
-shape: Type of shape (ex: circle,triangle and star)
-altitide: Elevation of land (ex: high, low, ect) (transparency detrmines elevation, higher value = higher elevation)  
-lakes: Number of lakes present on island
-aquifers: Number of aquifers present on island
-seed: provides meshs generated prior 
-city: Number of cities
(ex: -seed "any word", will create a sepearte output file to save mesh gerantion. -seed "#", will provide an seed.svg of the inputted numbers corresponding output)

Example command line:
(No seed)
java -jar island/island.jar -i img/irregular.mesh -o img/output.mesh -mode lagoon -shape circle -altitude low -lakes 3 -aquifers 2 -seed NO -city 10
(seed)
java -jar island/island.jar -i img/irregular.mesh -o img/output.mesh -mode lagoon -shape circle -altitude low -lakes 3 -aquifers 2 -seed 1 -city 10
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



## Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * A feature is done when its intended function is complete and performs accurately


| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| 01 | Create a graph adt (Node,Edge,Graph) (pathfinder sub-project) | Hemraj | 2023/04/01 | 2023/04/04 | D |
| 02 | Implement a Node class in adt (pathfinder sub-project) | Hemraj | 2023/04/01 | 2023/04/04 | D |
| 03 | Implement a Edge class in adt (pathfinder sub-project) | Hemraj | 2023/04/01 | 2023/04/04 | D |
| 04 | Implement a Graph class in adt (pathfinder sub-project) | Hemraj | 2023/04/05  | 2023/04/07  | D |
| 05 | Create a pathfinding algorithm (pathfinder sub-project) | Hemraj | 2023/04/05 | 2023/04/07 | D |
| 06 | Implement a class to create cities on the mesh (island sub-project) | Hemraj | 2023/04/07  | 2023/04/10 | D |
| 07 | Implement a class to convert vertices into nodes (island sub-project)  | Hemraj | 2023/04/10 | 2023/04/12 | D |
| 08 | Implement a class to create roads between cities (island sub-project)  | Hemraj | 2023/04/10 | 2023/04/12 | D |
| 09 | User can pass in a -city argument to change the # of cities (island sub-project) | Hemraj | 2023/04/11 | 2023/04/12  | D |

| ... | ... | ... |



