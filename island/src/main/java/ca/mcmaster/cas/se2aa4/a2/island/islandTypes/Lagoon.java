package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class Lagoon implements IslandGeneration{

    private Circle innerBoundary;
    private Circle outerBoundary;
    private Structs.Mesh aMesh;

    private List<Structs.Polygon> polygons;
    private List<Structs.Vertex> vertices;
    private List<Structs.Segment> segments;

    public Lagoon(Circle innerCircle, Circle outerCircle, Structs.Mesh generatorMesh){
        this.innerBoundary = innerCircle;
        this.outerBoundary = outerCircle;
        this.aMesh = generatorMesh;
        this.vertices = new ArrayList<>(aMesh.getVerticesList());
        this.segments = new ArrayList<>(aMesh.getSegmentsList());
        this.polygons = new ArrayList<>(aMesh.getPolygonsList());
    }

    //overrides the method of creating an island in the IslandGeneration interface
    @Override
    public Structs.Mesh createIsland(){

        //creates a clone mesh of the one that is passed in
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        Structs.Polygon newTile;

        clone.addAllVertices(vertices);
        clone.addAllSegments(segments);

        for(Structs.Polygon poly: polygons){
            Structs.Vertex centroid = vertices.get(poly.getCentroidIdx());

            //creates a lagoon tile if it is inside the inner circle
            if (innerBoundary.inShape(centroid)) {
                String color = 0+","+150+","+255;
                String type = "lagoon";
                newTile = createTile(poly,color,type);
            }

            //creates ocean tiles if it is outside the outer circle
            else if (!outerBoundary.inShape(centroid)) {
                String color = 0+","+0+","+255;
                String type = "ocean";
                newTile = createTile(poly,color,type);
            }

            //creates land tiles if it is in between the circles
            else {
                String color = 246+","+215+","+176;
                String type = "land";
                newTile = createTile(poly,color,type);
            }

            //adds the newTile created into the cloned mesh
            clone.addPolygons(newTile);

        }

        return clone.build();
    }

    private Structs.Polygon createTile(Structs.Polygon poly, String color, String type) {

        String key = "rgb_color";
        String key1 = "tile";

        //tile specification is just the logic of taking assigning a property to a polygon
        TileSpecification tileProperties = new TileSpecification();

        //temp is a created polygon that has the color we want for this tile
        Structs.Polygon tileColor = tileProperties.tileProperty(key, color, poly);

        return tileProperties.tileProperty(key1, type, tileColor);
    }

}
