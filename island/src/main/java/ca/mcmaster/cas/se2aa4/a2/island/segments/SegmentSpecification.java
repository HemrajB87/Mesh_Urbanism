package ca.mcmaster.cas.se2aa4.a2.island.segments;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class SegmentSpecification implements Segment{
    @Override
    public Structs.Segment segmentProperty(String key, String value, Structs.Segment oldSegment) {
        Structs.Segment.Builder newSegment = Structs.Segment.newBuilder(oldSegment);

        Structs.Property p = Structs.Property.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
        newSegment.addProperties(p);

        return newSegment.build();
    }
}
