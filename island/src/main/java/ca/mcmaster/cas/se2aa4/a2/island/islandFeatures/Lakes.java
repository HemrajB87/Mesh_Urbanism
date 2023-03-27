package ca.mcmaster.cas.se2aa4.a2.island.islandFeatures;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;

import java.util.ArrayList;
import java.util.List;

public class Lakes {
    tileCreater createTile = new tileCreater();

    public List<Structs.Polygon> addLakeTiles(List<Structs.Polygon> temp, String lakes) {
        List<Structs.Polygon> landList = new ArrayList<>();

        Structs.Polygon newTile;

        //get all land tiles
        for (Structs.Polygon currentPoly : temp) {
            String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

            if (currentPolyTileType.equals("land")) {
                landList.add(currentPoly);
            }
        }

        for (int i = 0; i < Integer.parseInt(lakes); i++) {
            int index = (int) (Math.random() * landList.size());
            Structs.Polygon currPoly = landList.get(index);

            String color = 18 + "," + 221 + "," + 252 + "," + 255;
            String type = "lake";
            newTile = createTile.createTile(currPoly, color, type, altitude);
            temp.add(newTile);

            extendLake(temp, currPoly, 3, 0.25);
        }
        return temp;
    }

    private void extendLake(List<Structs.Polygon> temp, Structs.Polygon currPoly, int depth, double chance) {
        if (depth <= 0) {
            return;
        }

        List<Integer> neighborList = currPoly.getNeighborIdxsList();

        for (Integer z : neighborList) {
            String neighborTileType = new TypeProperty().extract(temp.get(z).getPropertiesList());

            if (neighborTileType.equals("land")) {
                if (Math.random() <= chance) {
                    String color = 18 + "," + 221 + "," + 252 + "," + 255;
                    String type = "lake";
                    Structs.Polygon newTile = createTile.createTile(temp.get(z), color, type, altitude);
                    temp.add(newTile);
                    extendLake(temp, temp.get(z), depth - 1, chance);
                }
            }
        }
    }
}
