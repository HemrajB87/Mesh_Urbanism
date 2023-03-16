package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class LagoonIsland implements IslandGeneration{

    private Circle innerBoundary;
    private Circle outerBoundary;
    private Structs.Mesh aMesh;

    private List<Structs.Polygon> polygons;
    private List<Structs.Vertex> vertices;
    private List<Structs.Segment> segments;

    public LagoonIsland(Circle innerCircle, Circle outerCircle, Structs.Mesh generatorMesh){
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

        clone.addAllVertices(vertices);
        clone.addAllSegments(segments);

        Structs.Polygon newTile;
        List<Structs.Polygon> tempPolygonList = new ArrayList<>();

        for(Structs.Polygon poly: polygons){

            //properties to add to polygons to properly create their tile types
            String color,type;

            Structs.Vertex centroid = vertices.get(poly.getCentroidIdx());

            //creates a lagoon tile if it is inside the inner circle
            if (innerBoundary.inShape(centroid)) {
                color = 0+","+150+","+255;
                type = "lagoon";
            }

            //creates ocean tiles if it is outside the outer circle
            else if (!outerBoundary.inShape(centroid)) {
                color = 0+","+0+","+255;
                type = "ocean";
            }

            //creates land tiles if it is in between the circles
            else {
                color = 246+","+215+","+176;
                type = "land";
            }

            newTile = createTile(poly,color,type);

            //adds the newTile created into the cloned mesh
            tempPolygonList.add(newTile);
        }

        List<Structs.Polygon> islandWithBeachTiles = addBeachTiles(tempPolygonList);

        clone.addAllPolygons(islandWithBeachTiles);

        return clone.build();
    }

    private Structs.Polygon createTile(Structs.Polygon poly, String color, String type) {

        String key = "rgb_color";
        String key1 = "type";

        //tile specification is just the logic of taking assigning a property to a polygon
        TileSpecification tileProperties = new TileSpecification();

        //temp is a created polygon that has the color we want for this tile
        Structs.Polygon tileColor = tileProperties.tileProperty(key, color, poly);

        return tileProperties.tileProperty(key1, type, tileColor);
    }

    private List<Structs.Polygon> addBeachTiles(List<Structs.Polygon> temp){

        List<Structs.Polygon> updatedTileList = new ArrayList<>();
        Structs.Polygon newTile;

        for(Structs.Polygon currentPoly: temp){

            boolean isBeach = false;

            List<Integer> neighborList = currentPoly.getNeighborIdxsList();

            for(Integer i: neighborList){

                //extracts the tile type value of the current polygon we are looking at
                String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

                //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                String neighborTileType = new TypeProperty().extract(temp.get(i).getPropertiesList());

                //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "lagoon" or "ocean" we want it to be a beach tile
                if(currentPolyTileType.equals("land") && (neighborTileType.equals("lagoon") || neighborTileType.equals("ocean"))){

                    //so isBeach is set to true if this is the case
                    isBeach = true;

                    //break out of the loop since there is no need to check further if the current tile is a beach tile
                    break;
                }
            }

            //if the current polygon is a beach tile then we give it properties to become one
            if(isBeach){
                String color = 255+","+140+","+0;
                String type = "beach";
                newTile = createTile(currentPoly,color,type);

                //we add it to the new updated polygon tile list
                updatedTileList.add(newTile);

            } else {

                //if the current tile is not a beach one then we just add its previous value to the updated polygon list
                updatedTileList.add(currentPoly);
            }
        }
        return updatedTileList;

    }

}
