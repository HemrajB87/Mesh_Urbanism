package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.islandFeatures.Lakes;
import ca.mcmaster.cas.se2aa4.a2.island.islandFeatures.Rivers;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class PlainIsland implements IslandGeneration {

    private final Shape landBoundary;

    private final String altitude;
    private final Structs.Mesh aMesh;
    private final String lakes;
    private final String rivers;


    private final List<Structs.Polygon> polygons;
    private final List<Structs.Vertex> vertices;
    private final List<Structs.Segment> segments;

    public PlainIsland(Shape landBoundary,String newAlt, String lakes, String rivers, Structs.Mesh generatorMesh){
        this.landBoundary = landBoundary;
        this.altitude = newAlt;
        this.lakes =lakes;
        this.rivers = rivers;
        this.aMesh = generatorMesh;
        this.vertices = new ArrayList<>(aMesh.getVerticesList());
        this.segments = new ArrayList<>(aMesh.getSegmentsList());
        this.polygons = new ArrayList<>(aMesh.getPolygonsList());
    }

    @Override
    public Structs.Mesh createIsland(){

        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();

        clone.addAllVertices(vertices);

        Structs.Polygon newTile;
        List<Structs.Polygon> tempPolygonList = new ArrayList<>();

        for(Structs.Polygon poly: polygons){

            // setting elevation values
            int elevation =0;
            if(altitude.equals("high")){
                elevation = (int) (Math.random() * (255 - 100)) + 100;
            } else {
                elevation = (int) (Math.random() * (100 - 1)) + 1;
            }

            //properties to add to polygons to properly create their tile types
            String color,type;

            Structs.Vertex centroid = vertices.get(poly.getCentroidIdx());

            //creates land tiles if it is in the shape
            if (landBoundary.inShape(centroid)) {
                color = 246+","+215+","+176+","+elevation;
                type = "land";
            }

            //creates ocean tiles if it is not in the shape
            else {
                color = 0+","+0+","+255+","+255;
                type = "ocean";
            }

            newTile = createTile(poly,color,type);

            //adds the newTile created into the cloned mesh
            tempPolygonList.add(newTile);
        }
        Lakes createLakes = new Lakes();

        List<Structs.Polygon> islandWithLakes = createLakes.addLakeTiles(tempPolygonList, lakes);

        Rivers createRiver = new Rivers(islandWithLakes);
        List<Structs.Segment> islandWithRivers = createRiver.addRiverSeg( rivers );

        clone.addAllPolygons(createRiver.getRiverPolygonList());
        clone.addAllSegments(islandWithRivers);

        return clone.build();
    }

    private Structs.Polygon createTile(Structs.Polygon poly, String color, String type) {

        String key = "rgb_color";
        String key2 = "elevation";
        String key1 = "type";

        TileSpecification tileProperties = new TileSpecification();

        //temp is a created polygon that has the color we want for this tile
        Structs.Polygon tileColor = tileProperties.tileProperty(key, color, poly);

        //returns a tile that has new color and type properties
        return tileProperties.tileProperty(key1, type, tileColor);
    }



}
