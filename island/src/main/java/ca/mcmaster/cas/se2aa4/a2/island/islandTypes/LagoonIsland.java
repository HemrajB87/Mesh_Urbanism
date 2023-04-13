package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.altitude.Altitude;
import ca.mcmaster.cas.se2aa4.a2.island.aquifers.AquiferGeneration;
import ca.mcmaster.cas.se2aa4.a2.island.cityTypes.MajorCity;
import ca.mcmaster.cas.se2aa4.a2.island.cityTypes.MinorCity;
import ca.mcmaster.cas.se2aa4.a2.island.seed.FileSaver;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.islandFeatures.Lakes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LagoonIsland implements IslandGeneration {

    private final Shape innerShape;
    private final Shape outerShape;

    private final String mode;
    private  final String altitude;
    private final String lakes;
    private final String rivers;
    public final String aquifers;
    public final String soil;
    public final String biomes;
    public final String seed;
    private final Structs.Mesh aMesh;

    private final List<Structs.Polygon> polygons;
    private final List<Structs.Vertex> vertices;
    private final List<Structs.Segment> segments;
    private final String city;

    //Moved createTile function to its own class so other class can use it
    tileCreater createTile = new tileCreater();

    public LagoonIsland(Shape innerShape, Shape outerShape,String newMode, String newAlt, String lakes, String rivers, String aquifers, String soil, String biomes, String seed,String city, Structs.Mesh generatorMesh) {
        this.innerShape = innerShape;
        this.outerShape = outerShape;
        this.mode=newMode;
        this.altitude = newAlt;
        this.lakes = lakes;
        this.rivers = rivers;
        this.aquifers = aquifers;
        this.soil = soil;
        this.biomes = biomes;
        this.seed = seed;
        this.city=city;
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

    //overrides the method of creating an island in the IslandGeneration interface
    @Override
    public Structs.Mesh createIsland() {

        if (isNumeric(seed)) {
            FileSaver.setSeed(seed);
        }else{
            FileSaver.setSeed("None");
        }

        //creates a clone mesh of the one that is passed in
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();

        //lake obj
        Lakes createLakes = new Lakes();

        //aquifer
        //AquiferGeneration createAquifers = new AquiferGeneration();

        clone.addAllVertices(vertices);
        clone.addAllSegments(segments);

        Structs.Polygon newTile;
        List<Structs.Polygon> tempPolygonList = new ArrayList<>();

        for (Structs.Polygon poly : polygons) {

            // calling Altitude class for elevation values
            //int elevation = new Altitude(altitude,mode).setAltitude();

            //properties to add to polygons to properly create their tile types
            String color, type;

            Structs.Vertex centroid = vertices.get(poly.getCentroidIdx());

            //creates a lagoon tile if it is inside the inner circle
            if (innerShape.inShape(centroid)) {
                color = 0 + "," + 150 + "," + 255 + "," + 255;
                type = "ocean"; // i.e lagoon, kept ocean to help with altitude
            }

            //creates ocean tiles if it is outside the outer circle
            else if (!outerShape.inShape(centroid)) {
                color = 0 + "," + 0 + "," + 255 + "," + 255;
                type = "ocean";
            }

            //creates land tiles if it is in between the circles
            else {
                color = 246 + "," + 215 + "," + 176 + "," + 0;
                type = "land";
            }

            //AquiferGeneration createAquifers = new AquiferGeneration();

            newTile = createTile.createTile(poly, color, type);

            //adds the newTile created into the cloned mesh
            tempPolygonList.add(newTile);
        }
        AquiferGeneration createAquifers = new AquiferGeneration();
        Altitude createAltitude = new Altitude(altitude,mode);

        int cityValue = Integer.parseInt(city);

        List<Structs.Polygon> islandWithLakes = createLakes.addLakeTiles(tempPolygonList, lakes);

        List<Structs.Polygon> islandWithAquifers = createAquifers.addAquiferTiles(islandWithLakes, aquifers);

        List<Structs.Polygon> islandWithAltitude = createAltitude.setAltitude(islandWithAquifers);


        MinorCity newcities = new MinorCity(cityValue, tempPolygonList, vertices);
        List<Structs.Vertex> addCities = newcities.minorCityVertex();

        clone.addAllVertices(addCities);

        MajorCity majorcity = new MajorCity();
        List<Structs.Vertex> addMajorCities = Collections.singletonList(majorcity.majorCities(vertices, islandWithAltitude));

        clone.addAllVertices(addMajorCities);

        clone.addAllPolygons(islandWithAltitude);

        for (Structs.Polygon p : islandWithAquifers){
            System.out.println(p.getPropertiesList());
        }
        return clone.build();
    }


}

