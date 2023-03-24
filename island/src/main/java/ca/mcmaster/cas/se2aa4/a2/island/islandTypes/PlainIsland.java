package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.altitude.Altitude;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.shape.MeshCenter;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainIsland implements IslandGeneration {

    private final Shape landBoundary;

    private final String altitude;

    private  final String mode;
    private final Structs.Mesh aMesh;

    private final List<Structs.Polygon> polygons;
    private final List<Structs.Vertex> vertices;
    private final List<Structs.Segment> segments;

    tileCreater createTile = new tileCreater();


    public PlainIsland(Shape landBoundary, String newMode, String newAlt, Structs.Mesh generatorMesh){
        this.landBoundary = landBoundary;
        this.mode=newMode;
        this.altitude = newAlt;
        this.aMesh = generatorMesh;
        this.vertices = new ArrayList<>(aMesh.getVerticesList());
        this.segments = new ArrayList<>(aMesh.getSegmentsList());
        this.polygons = new ArrayList<>(aMesh.getPolygonsList());
    }

    public List<Structs.Polygon> polygonsInBoundary(){
        List<Structs.Polygon> listOfLandPolygons = new ArrayList<>();
        for(Structs.Polygon p: polygons){
            Structs.Vertex centroid = vertices.get(p.getCentroidIdx());
            if(landBoundary.inShape(centroid)){
                listOfLandPolygons.add(p);
            }
        }

        return listOfLandPolygons;
    }

    public List<Structs.Polygon> polygonsOnBoundary(){
        List<Structs.Polygon> listOfBoundryPolygons = new ArrayList<>();
        for(Structs.Polygon p: polygons){
            List<Integer> neighborList = p.getNeighborIdxsList();

            for (Integer i : neighborList) {

                //extracts the tile type value of the current polygon we are looking at
                String currentPolyTileType = new TypeProperty().extract(p.getPropertiesList());

                //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                String neighborTileType = new TypeProperty().extract(polygons.get(i).getPropertiesList());

                //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "lagoon" or "ocean" we want it to be a beach tile
                Structs.Vertex centroid = vertices.get(p.getCentroidIdx());
                if(landBoundary.inShape(centroid) && currentPolyTileType.equals("land") && neighborTileType.equals("ocean") ){
                    listOfBoundryPolygons.add(p);
                }
            }
        }
        return listOfBoundryPolygons;
    }




    @Override
    public Structs.Mesh createIsland(){

        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();


        clone.addAllVertices(vertices);
        clone.addAllSegments(segments);

        Structs.Polygon newTile;
        List<Structs.Polygon> tempPolygonList = new ArrayList<>();




        for(Structs.Polygon poly: polygons){


            // setting elevation values
            int elevation =new Altitude(altitude, mode).setAltitude();

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

        List<Structs.Polygon> island = addAltTiles(tempPolygonList);
        if(altitude.equalsIgnoreCase("On")) {
            int numIterations = 12; // Change this value to the number of times you want to call addAltTiles2 // hard code
            int evl = 10;
            for (int i = 0; i < numIterations; i++) {
                evl += 15;
                island = addAltTiles2(island, evl);
            }
        }
        clone.addAllPolygons(island);
        return clone.build();
//        List<Structs.Polygon> island = addAltTiles(tempPolygonList);
//
//        List<Structs.Polygon> island2 = addAltTiles2(island);
//        clone.addAllPolygons(island2);
//
//        //clone.addAllPolygons(island2);
//
//        return clone.build();
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


    private List<Structs.Polygon> addAltTiles(List<Structs.Polygon> temp) {

        List<Structs.Polygon> updatedTileList = new ArrayList<>();
        Structs.Polygon newTile;

        for (Structs.Polygon currentPoly : temp) {

            boolean isBeach = false;

            List<Integer> neighborList = currentPoly.getNeighborIdxsList();

            for (Integer i : neighborList) {

                //extracts the tile type value of the current polygon we are looking at
                String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

                //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                String neighborTileType = new TypeProperty().extract(temp.get(i).getPropertiesList());

                //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "lagoon" or "ocean" we want it to be a beach tile
                if (currentPolyTileType.equals("land") && neighborTileType.equals("ocean")) {
                    isBeach = true;
                    break;
                }
            }

            // calling Altitude class for elevation values
            //int elevation = new Altitude(altitude,mode).setAltitude();

            //changes land tile to beach tile if the beach requirements are met
            if (isBeach) {
                String color = 255 + "," + 140 + "," + 0 + "," + 255;
                String type = "beach";
                newTile = createTile.createTile(currentPoly, color, type);
                updatedTileList.add(newTile);
            } else {
                updatedTileList.add(currentPoly);
            }
        }
        return updatedTileList;
    }

    private List<Structs.Polygon> addAltTiles2(List<Structs.Polygon> temp , int x) {

        List<Structs.Polygon> updatedTileList = new ArrayList<>();
        Structs.Polygon newTile;

        for (Structs.Polygon currentPoly : temp) {

            boolean isBeach = false;

            List<Integer> neighborList = currentPoly.getNeighborIdxsList();

            for (Integer i : neighborList) {

                //extracts the tile type value of the current polygon we are looking at
                String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

                //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                String neighborTileType = new TypeProperty().extract(temp.get(i).getPropertiesList());

                //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "lagoon" or "ocean" we want it to be a beach tile
                if (currentPolyTileType.equals("land") && (neighborTileType.equals("beach") )) {
                    isBeach = true;
                    break;
                }
            }

            // calling Altitude class for elevation values
            //int elevation = new Altitude(altitude,mode).setAltitude();

            //changes land tile to beach tile if the beach requirements are met
            if (isBeach) {
                String color = 255 + "," + 140 + "," + 0 + "," + x;
                String type = "beach";
                newTile = createTile.createTile(currentPoly, color, type);
                updatedTileList.add(newTile);
            } else {
                updatedTileList.add(currentPoly);
            }
        }
        return updatedTileList;
    }


}
