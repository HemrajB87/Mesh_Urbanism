package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.io.IOException;
import java.util.List;

public class MeshDump {

    public void dump(String fileName) throws IOException {
        MeshFactory factory = new MeshFactory();
        Mesh aMesh = factory.read(fileName);
        this.dump(aMesh);
    }

    public void dump(Mesh aMesh) {
        List<Vertex> vertices = aMesh.getVerticesList();

        System.out.println("|Vertices| = " + vertices.size());
        for (Vertex v : vertices){
            StringBuffer line = new StringBuffer();
            line.append(String.format("(%.2f,%.2f)",v.getX(), v.getY()));
            line.append(" [");
            for(Property p: v.getPropertiesList()){
                line.append(String.format("%s -> %s, ", p.getKey(), p.getValue()));
            }
            line.append("]");
            System.out.println(line);
        }

        //added print statements to know what vertices are being joined by a segment
        List<Segment> segments = aMesh.getSegmentsList();

        for (Segment s : segments){
            StringBuffer line = new StringBuffer();
            line.append(String.format("Line between (%.2f,%.2f) and (%.2f,%.2f)",vertices.get(s.getV1Idx()).getX(),vertices.get(s.getV1Idx()).getY(),vertices.get(s.getV2Idx()).getX(),vertices.get(s.getV2Idx()).getY()));
            line.append(" [");
            for(Property p: s.getPropertiesList()){
                line.append(String.format("%s -> %s, ", p.getKey(), p.getValue()));
            }
            line.append("]");
            System.out.println(line);
        }

    }
}
