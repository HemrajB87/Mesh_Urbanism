package ca.mcmaster.cas.se2aa4.a2.island.aquifers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.properties.ColourProperty;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class AquiferGeneration {

    tileCreater createTile = new tileCreater();

    public List<Structs.Polygon> addAquiferTiles(List<Structs.Polygon> temp, String aquifers) {
        List<Structs.Polygon> landList = new ArrayList<>();

        Structs.Polygon newTile;

        //get all land tiles
        for (Structs.Polygon currentPoly : temp) {
            String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

            if (currentPolyTileType.equals("land")) {
                landList.add(currentPoly);
            }
        }

        for (int i = 0; i < Integer.parseInt(aquifers); i++) {
            int index = (int) (Math.random() * landList.size());
            Structs.Polygon currPoly = landList.get(index);
            String color = new ColourProperty().extract(currPoly.getPropertiesList());
            //String color = 0 + "," + 0 + "," + 0 + "," + 255;
            String type = "aquifer";

            TileSpecification tileProperties = new TileSpecification();
            //tileProperties.tileProperty("aquifers", "aquifers", currPoly);
            temp.add(tileProperties.tileProperty("aquifers", "aquifers", currPoly));

            extendAfquifer(temp, currPoly, 3, 0.25);
        }
        return temp;
    }

    private void extendAfquifer(List<Structs.Polygon> temp, Structs.Polygon currPoly, int depth, double chance) {
        if (depth <= 0) {
            return;
        }

        List<Integer> neighborList = currPoly.getNeighborIdxsList();

        for (Integer z : neighborList) {
            String neighborTileType = new TypeProperty().extract(temp.get(z).getPropertiesList());

            if (neighborTileType.equals("land")) {
                if (Math.random() <= chance) {
                    //String color = 18 + "," + 0 + "," + 0 + "," + 255;
                    //String type = "aquifer";
                    //Structs.Polygon newTile = createTile.createTile(temp.get(z), color, type);
                    TileSpecification tileProperties = new TileSpecification();
                    temp.add(tileProperties.tileProperty("aquifers", "aquifers", currPoly));
                    //temp.add(newTile);
                    extendAfquifer(temp, temp.get(z), depth - 1, chance);
                }
            }
        }
    }

}
