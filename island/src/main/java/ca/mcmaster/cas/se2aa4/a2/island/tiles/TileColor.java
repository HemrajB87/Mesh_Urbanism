package ca.mcmaster.cas.se2aa4.a2.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class TileColor {

    //create some kind of circle thing
    //determine if the centroids are in the radius
    //if they are then give them a color of beige
    //if they are not then give them a color of black

    private List<Structs.Polygon> polygons;
    private List<Structs.Vertex> vertices;
    private Circle landBoundary;
    private Structs.Mesh aMesh;


    public TileColor(Structs.Mesh passedInMesh, Circle landBoundary){
        this.aMesh = passedInMesh;
        this.vertices = new ArrayList<>(aMesh.getVerticesList());
        this.polygons = new ArrayList<>(aMesh.getPolygonsList());
        this.landBoundary = landBoundary;
    }

    public Structs.Mesh assignColor(){

        //creates a clone mesh by adding the existing vertices and segments, we are adding the polygons later since we want to assign it a specific color
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(aMesh.getVerticesList());
        clone.addAllSegments(aMesh.getSegmentsList());

        //loops through all the polygons in the polygon list
        for(Structs.Polygon poly: polygons) {

            //creates a new polygon
            Structs.Polygon.Builder newPoly = Structs.Polygon.newBuilder(poly);

            //we are getting the centroid of the new polygon
            Structs.Vertex centroid = vertices.get(newPoly.getCentroidIdx());

            //if the method inCircle returns true then we make it a sand tile (give it a beige color)
            if(landBoundary.inCircle(centroid)) {

                String color = 246+","+215+","+176;
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(color)
                        .build();
                newPoly.addProperties(p);
            }

            //if it is outside the circle then it will be a water tile "ocean"
            else{
                String color = 0+","+150+","+255;
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(color)
                        .build();
                newPoly.addProperties(p);
            }

            //add the polygon to the clone mesh
            clone.addPolygons(newPoly);
        }

        //after it is done going through all the polygons we return the clone mesh
        return clone.build();

    }

}
