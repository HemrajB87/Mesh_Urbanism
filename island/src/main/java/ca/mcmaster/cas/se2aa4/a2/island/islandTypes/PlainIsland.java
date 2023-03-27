package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.altitude.Altitude;
import ca.mcmaster.cas.se2aa4.a2.island.aquifers.AquiferGeneration;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.islandFeatures.Lakes;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.seed.FileSaver;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class PlainIsland implements IslandGeneration {

    private final Shape landBoundary;

    private final String altitude;

    public final String seed;
    private  final String mode;
    private final String lakes;
    private final String rivers;
    private final String soil;
    private final String biomes;
    private final String aquifers;
    private final Structs.Mesh aMesh;

    private final List<Structs.Polygon> polygons;
    private final List<Structs.Vertex> vertices;
    private final List<Structs.Segment> segments;

    tileCreater createTile = new tileCreater();


    public PlainIsland(Shape landBoundary, String newMode, String newAlt, String lakes, String rivers, String aquifers, String soil, String biomes, String seed, Structs.Mesh generatorMesh){
        this.landBoundary = landBoundary;
        this.mode=newMode;
        this.altitude = newAlt;
        this.lakes = lakes;
        this.rivers = rivers;
        this.aquifers = aquifers;
        this.soil = soil;
        this.biomes = biomes;
        this.seed = seed;
        this.aMesh = generatorMesh;
        this.vertices = new ArrayList<>(aMesh.getVerticesList());
        this.segments = new ArrayList<>(aMesh.getSegmentsList());
        this.polygons = new ArrayList<>(aMesh.getPolygonsList());
    }




    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public Structs.Mesh createIsland(){

        if (isNumeric(seed)) {
            FileSaver.setSeed(seed);
        } else{
            FileSaver.setSeed("None");
        }

        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();

        //lake obj
        Lakes createLakes = new Lakes();

        clone.addAllVertices(vertices);
        clone.addAllSegments(segments);

        Structs.Polygon newTile;
        List<Structs.Polygon> tempPolygonList = new ArrayList<>();

        for(Structs.Polygon poly: polygons){

            // setting elevation values
            //int elevation =new Altitude(altitude, mode).setAltitude();

            //properties to add to polygons to properly create their tile types
            String color,type;


            Structs.Vertex centroid = vertices.get(poly.getCentroidIdx());

            //creates land tiles if it is in the shape
            if (landBoundary.inShape(centroid)) {
                color = 246+","+215+","+176+","+0;
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
        AquiferGeneration createAquifers = new AquiferGeneration();
        Altitude createAltitude = new Altitude(altitude,mode);



        List<Structs.Polygon> islandWithLakes = createLakes.addLakeTiles(tempPolygonList, lakes);

        List<Structs.Polygon> islandWithAquifers = createAquifers.addAquiferTiles(islandWithLakes, aquifers);

        List<Structs.Polygon> islandWithAltitude = createAltitude.setAltitude(islandWithAquifers);

        clone.addAllPolygons(islandWithAltitude);

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


}
