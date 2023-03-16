package ca.mcmaster.cas.se2aa4.a2.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface Tile {
    //Tile interface method

    //we can use this interface to specify the properties of a tile
    Structs.Polygon tileProperty(String key, String value, Structs.Polygon oldPolygon);

}
