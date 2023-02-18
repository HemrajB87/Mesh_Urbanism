package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

                //makes a 4 by 4 square with 4 vertices
                //Note: some vertices are doubled, this is to account for the overlapping of squares in the mesh
                int topLeftVertex = vertices.size();
                Vertex topLeft = (Vertex.newBuilder().setX(x).setY(y).build());
                vertices.add(topLeft);

                int topRightVertex = vertices.size();
                Vertex topRight = (Vertex.newBuilder().setX((double) x+square_size).setY(y).build());
                vertices.add(topRight);

                int bottomLeftVertex = vertices.size();
                Vertex bottomLeft = (Vertex.newBuilder().setX(x).setY((double) y+square_size).build());
                vertices.add(bottomLeft);

                int bottomRightVertex = vertices.size();
                Vertex bottomRight = (Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                vertices.add(bottomRight);

                //this connects the vertices together by a line to actually make a square
                segments.add(Segment.newBuilder().setV1Idx(topLeftVertex).setV2Idx(topRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(topLeftVertex).setV2Idx(bottomLeftVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(topRightVertex).setV2Idx(bottomRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(bottomLeftVertex).setV2Idx(bottomRightVertex).build());
            }
        }

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

        ArrayList<Segment> segmentsWithColors = new ArrayList<>();
        for(Segment s: segments) {
            Vertex vertex1 = verticesWithColors.get(s.getV1Idx());
            Vertex vertex2 = verticesWithColors.get(s.getV2Idx());
            List<Property> vertex1Properties = vertex1.getPropertiesList();
            List<Property> vertex2Properties = vertex2.getPropertiesList();

            int[] vertex1ColorValues = extractColor(vertex1Properties);
            int[] vertex2ColorValues = extractColor(vertex2Properties);

            int segmentRed = (vertex1ColorValues[0] + vertex2ColorValues[0])/2;
            int segmentGreen = (vertex1ColorValues[1] + vertex2ColorValues[1])/2;
            int segmentBlue = (vertex1ColorValues[2] + vertex2ColorValues[2])/2;

            //Debug statements
//            System.out.println("Red average of "+ vertex1ColorValues[0] + " " + vertex2ColorValues[0] + "= "+ segmentRed);
//            System.out.println("Green average of "+ vertex1ColorValues[1] + " " + vertex2ColorValues[1] + "= "+ segmentGreen);
//            System.out.println("Blue average of "+ vertex1ColorValues[2] + " " + vertex2ColorValues[2] + "= "+ segmentBlue);

            String colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();

            segmentsWithColors.add(colored);

        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();
    }

    private int[] extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {

                //Debug statement
                //System.out.println(p.getValue());

                val = p.getValue();
            }
        }

        if(val == null){
            return new int[]{0,0,0};
        }

        String[] temp = val.split(",");
        int red = Integer.parseInt(temp[0]);
        int green = Integer.parseInt(temp[1]);
        int blue = Integer.parseInt(temp[2]);

        return new int[]{red, green, blue};
    }

}
