package ca.mcmaster.cas.se2aa4.a2.island.segments;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface Segment {
    Structs.Segment segmentProperty(String key, String value, Structs.Segment oldSegment);

}
