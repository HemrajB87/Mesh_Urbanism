package ca.mcmaster.cas.se2aa4.a2.island.configuration;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

public class tileCreater {
    public Structs.Polygon createTile(Structs.Polygon poly, String color, String type) {

        String key = "rgb_color";
        String key1 = "type";

        TileSpecification tileProperties = new TileSpecification();

        //temp is a created polygon that has the color we want for this tile
        Structs.Polygon tileColor = tileProperties.tileProperty(key, color, poly);

        //returns a tile that has new color and type properties
        return tileProperties.tileProperty(key1, type, tileColor);
    }
}
