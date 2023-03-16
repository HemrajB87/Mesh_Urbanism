package ca.mcmaster.cas.se2aa4.a2.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class TileSpecification implements Tile{

    @Override
    public Structs.Polygon tileProperty(String key, String value, Structs.Polygon oldPolygon) {
        Structs.Polygon.Builder newPolygon = Structs.Polygon.newBuilder(oldPolygon);

        Structs.Property p = Structs.Property.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
        newPolygon.addProperties(p);

        return newPolygon.build();

    }

}
