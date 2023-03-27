package ca.mcmaster.cas.se2aa4.a2.island.islandFeatures;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;
import ca.mcmaster.cas.se2aa4.a2.island.properties.HumidityProperty;


import java.util.ArrayList;
import java.util.List;

public class biome {
    public List<Structs.Polygon> addBiome(List<Structs.Polygon> temp, String biome) {
        List<Structs.Polygon> returnList = new ArrayList<>();
        List<Structs.Polygon> landList = new ArrayList<>();

        // Get all land tiles
        for (Structs.Polygon currentPoly : temp) {
            String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());
            if (currentPolyTileType.equals("land")) {
                landList.add(currentPoly);
            }
        }

        for(Structs.Polygon currentPoly: landList){
            String humidity = new HumidityProperty().extract(currentPoly.getPropertiesList());
            TileSpecification setTile = new TileSpecification();

            if (Integer.parseInt(humidity)<=3){
                returnList.add(setTile.tileProperty("rgb_color", "238,210,120,255", currentPoly));
            } else if (Integer.parseInt(humidity)>3 && Integer.parseInt(humidity)<=6) {
                returnList.add(setTile.tileProperty("rgb_color", "143,193,114,255", currentPoly));
            } else{
                returnList.add(setTile.tileProperty("rgb_color", "15,110,46,255", currentPoly));
            }
        }
        return returnList;
    }
}
