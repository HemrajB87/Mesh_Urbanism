package ca.mcmaster.cas.se2aa4.a2.island.islandFeatures;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.SegmentCreater;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.SegmentSpecification;

import java.util.*;

public class Rivers {
//    Go through each polygon that is not ocean or lake at it to curr usable poly

//    if nerighbour poly list all lower elvation stop
    private List<Structs.Polygon> polygons;
    private List<Structs.Polygon> polygonsToBeRemoved = new ArrayList<>();

    //    check if polygon its touching is a lake or ocean or neighbours higher stop else continue
    public Rivers(List<Structs.Polygon> polygonsList){
        this.polygons = polygonsList;
    }

    private List<Structs.Polygon> riverPolygons = new ArrayList<>();
    SegmentCreater createSeg = new SegmentCreater();

    public List<Structs.Segment> addRiverSeg( String rivers) {
        List<Structs.Segment> segmentsList = new ArrayList<>();
        List<Structs.Polygon> landList = new ArrayList<>();

        // Get all land tiles
        for (Structs.Polygon currentPoly : polygons) {
            String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());
            if (currentPolyTileType.equals("land")) {
                landList.add(currentPoly);
            }
        }

        System.out.println(polygons.size());
        // Add rivers
        for (int i = 0; i < Integer.parseInt(rivers); i++) {
            // Select random starting polygon
            int startIdx = (int) (Math.random() * landList.size());
            Structs.Polygon currentPoly = landList.get(startIdx);

            Set<Integer> visited = new HashSet<>();
            visited.add(startIdx);

            // Find starting centroid index
            int currentCentroidIdx = currentPoly.getCentroidIdx();

            // Keep going until the river reaches the lowest polygon or a dead end is reached
            while (true) {
                double lowestElevation = Double.MAX_VALUE;
                Structs.Polygon nextPoly = null;
                int nextCentroidIdx = 0;

                List<Integer> neighborList = currentPoly.getNeighborIdxsList();

                boolean contiunationexist = false;

                int randomAlt = (int) (Math.random() * landList.size());
                for (Integer z : neighborList) {
                    if (!visited.contains(polygons.get(z).getCentroidIdx())) {
                        String neighborTileType = new TypeProperty().extract(polygons.get(z).getPropertiesList());
                        if (neighborTileType.equals("land") && randomAlt < lowestElevation) {
                            lowestElevation = randomAlt;
                            nextPoly = polygons.get(z);
                            nextCentroidIdx = polygons.get(z).getCentroidIdx();
                            contiunationexist = true;
                        }
                    }
                }

                if (!contiunationexist) {
                    break; // Reached the end of the river
                }

                // Create new segment
                Structs.Segment segment = Structs.Segment.newBuilder()
                        .setV1Idx(currentCentroidIdx)
                        .setV2Idx(nextCentroidIdx)
                        .build();
                segmentsList.add(segment);

                //Set properties
                SegmentSpecification setProperty = new SegmentSpecification();
                TileSpecification setRiver = new TileSpecification();

                Structs.Segment temp = setProperty.segmentProperty("thickness", "1", segment);
                setProperty.segmentProperty("rgb_color", "255,0,0,255", temp);

                Structs.Polygon tempPoly = currentPoly;
                riverPolygons.add(setRiver.tileProperty("hasRiver", "True", currentPoly));
                polygonsToBeRemoved.add(tempPoly);

                // Set current polygon and add to visited list
                currentPoly = nextPoly;
                currentCentroidIdx = nextCentroidIdx;
                visited.add(currentCentroidIdx);
            }
        }

        return segmentsList;
    }

    public List<Structs.Polygon> getRiverPolygonList(){
        List<Structs.Polygon> modifiedList = new ArrayList<>(polygons);

        modifiedList.removeAll(polygonsToBeRemoved);
        modifiedList.addAll(riverPolygons);
        return modifiedList;
    }
}

