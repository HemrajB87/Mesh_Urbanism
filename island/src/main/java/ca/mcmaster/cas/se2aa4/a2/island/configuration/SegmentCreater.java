package ca.mcmaster.cas.se2aa4.a2.island.configuration;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.segments.SegmentSpecification;

public class SegmentCreater {
    public Structs.Segment createSegment(Structs.Segment segment, String thickness, String type, String color){
        String key = "river_width";
        String key1 = "type";
        String key2 = "color";

        SegmentSpecification segmentProperties = new SegmentSpecification();


        Structs.Segment segmentColor = segmentProperties.segmentProperty(key2, color, segment);
        Structs.Segment segmentThickness  = segmentProperties.segmentProperty(key, thickness, segmentColor);

        return segmentProperties.segmentProperty(key1, type, segmentThickness);
    }
}
