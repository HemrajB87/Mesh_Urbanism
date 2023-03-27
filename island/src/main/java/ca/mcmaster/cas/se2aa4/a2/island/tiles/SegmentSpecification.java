package ca.mcmaster.cas.se2aa4.a2.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class SegmentSpecification {
    public Structs.Segment segmentProperty(String key, String value, Structs.Segment oldsegment) {
        Structs.Segment.Builder newSegment = Structs.Segment.newBuilder(oldsegment);

        Structs.Property p = Structs.Property.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
        newSegment.addProperties(p);

        return newSegment.build();
    }

}