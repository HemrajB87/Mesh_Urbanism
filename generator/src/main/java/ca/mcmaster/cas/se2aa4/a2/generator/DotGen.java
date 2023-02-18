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

        //before we used a HashSet

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments= new ArrayList<>();

        // Create all the vertices
        //Giga lost
        for(int y = 0; y < width; y += square_size) {
            for(int x = 0; x < height; x += square_size) {
                System.out.println("Loop iteration" + x + " " + y);

                int topLeftVertex = vertices.size();
                Vertex topLeft = (Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(topLeft);
                System.out.println(topLeft);

                int topRightVertex = vertices.size();
                Vertex topRight = (Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(topRight);
                System.out.println(topRight);

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
                //System.out.println(segments);
            }
        }

        System.out.println(vertices);

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        //Before used a HashSet
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();

        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();

            verticesWithColors.add(colored);
        }

        System.out.println(verticesWithColors);

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
