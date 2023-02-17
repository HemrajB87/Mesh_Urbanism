package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    //chagen these back to 500,500,20
    private final int width = 10;
    private final int height = 10;
    private final int square_size = 2;

    public Mesh generate() {
        Set<Vertex> vertices = new HashSet<>();
        Set<Segment> segments = new HashSet<>();

        // Create all the vertices
        //Giga lost
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                int topLeftVertex = vertices.size();
                Vertex topLeft = (Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(topLeft);

                int topRightVertex = vertices.size();
                Vertex topRight = (Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(topRight);

                int bottomLeftVertex = vertices.size();
                Vertex bottomLeft = (Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(bottomLeft);

                int bottomRightVertex = vertices.size();
                Vertex bottomRight = (Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                vertices.add(bottomRight);

                segments.add(Segment.newBuilder().setV1Idx(topLeftVertex).setV2Idx(topRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(topLeftVertex).setV2Idx(bottomLeftVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(topRightVertex).setV2Idx(bottomRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(bottomLeftVertex).setV2Idx(bottomRightVertex).build());
                System.out.println(segments);
            }
        }

        System.out.println(vertices);

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        Set<Vertex> verticesWithColors = new HashSet<>();

        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
            System.out.println(v);
        }

//        ArrayList<Vertex> conversion = new ArrayList<>(vertices);
//        ArrayList<Segment> segments = new ArrayList<>();
//        segments.add(Segment.newBuilder().setV1(1).setV2Idx(1).build());

//        for(int i = 0; i<conversion.size(); i++){
//            System.out.println("Conversion list" + conversion.get(i));
//            segments.add(Segment.newBuilder().setV1Idx(i).setV2Idx(12).build());
//        }

        System.out.println(segments);





//


//        Set <Segment> segments = new HashSet<>();
//
//        for (int i = 0; i < bag.nextInt(10,50); i++) {
//            int v1_idx = bag.nextInt(vertices.size());
//            int v2_idx = bag.nextInt(vertices.size());
//            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build();
//            segments.add(s);
//        }
//
//
//        Set<Segment> segments = new HashSet<>();
//        for(int x = 0; x < width; x += square_size) {
//            for(int y = 0; y < height; y += square_size) {
//                segments.add(Segment.newBuilder().build());
//            }
//        }
//
//
//        Set<Segment> segmentsWithColors = new HashSet<>();
//        Random bag1 = new Random();
//        for(Segment s: segments){
//            int red = bag1.nextInt(255);
//            int green = bag1.nextInt(255);
//            int blue = bag1.nextInt(255);
//            String colorCode = red + "," + green + "," + blue;
//            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//            Segment colored = Segment.newBuilder(s).addProperties(color).build();
//            segmentsWithColors.add(colored);
//        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
