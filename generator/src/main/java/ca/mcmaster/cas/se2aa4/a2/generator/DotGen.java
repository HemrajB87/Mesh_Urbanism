package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;
import java.util.List;
//import java.awt.*;
//import java.io.IOException;
//
//import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    //chagen these back to 500,500,20
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {

        //before we used a HashSet

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments= new ArrayList<>();
        ArrayList<Polygon> addPolygons = new ArrayList<>();
        ArrayList<Vertex> centroids = new ArrayList<>();


        // Create all the vertices
        for(int y = 0; y < width; y += square_size) {
            for(int x = 0; x < height; x += square_size) {

                //makes a 4 by 4 square with 4 vertices
                //Note: need to fix the duplications of segments and vertices
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

//                int centroidVertex = vertices.size();
//                Vertex centre = Vertex.newBuilder().setX((x+()))

                //this connects the vertices together by a line to actually make a square
                segments.add(Segment.newBuilder().setV1Idx(topLeftVertex).setV2Idx(topRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(topRightVertex).setV2Idx(bottomRightVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(bottomRightVertex).setV2Idx(bottomLeftVertex).build());
                segments.add(Segment.newBuilder().setV1Idx(bottomLeftVertex).setV2Idx(topLeftVertex).build());



                double newX= bottomLeft.getX();
                double newY= bottomLeft.getY();

                double newXx= topRight.getX();
                double newYy= topRight.getY();

                double centralPointX=((newX+newXx)/2);
                double centralPointY=((newY+newYy)/2);

                int Central = centroids.size();
                Vertex CentralP = (Vertex.newBuilder().setX(centralPointX).setY(centralPointY).build());
                centroids.add(CentralP);


                Polygon test = Polygon.newBuilder().addSegmentIdxs(segments.size()-4)
                        .addSegmentIdxs(segments.size()-3)
                        .addSegmentIdxs(segments.size()-2)
                        .addSegmentIdxs(segments.size()-1)
                        .setCentroidIdx(centroids.size()).
                        build();
                addPolygons.add(test);

            }
        }

        for(Polygon p: addPolygons){
            System.out.println("Polygon" + p + "has points:");
            for(Integer i: p.getSegmentIdxsList()){
                System.out.println(vertices.get(segments.get(i).getV1Idx()).getX());
                System.out.println(vertices.get(segments.get(i).getV1Idx()).getY());
                System.out.println(vertices.get(segments.get(i).getV2Idx()).getX());
                System.out.println(vertices.get(segments.get(i).getV2Idx()).getY());
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
            int transparency = bag.nextInt(255);

            //Transparency is added in the colorCode
            String colorCode = red + "," + green + "," + blue + "," + transparency;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();

            System.out.println(colored.getPropertiesList());

            verticesWithColors.add(colored);
        }

        ArrayList<Polygon> polygonsWithColors = new ArrayList<>();
        for(Polygon p: addPolygons) {
            Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,255,255").build();
            Polygon bluePolygon = Polygon.newBuilder(p).addProperties(color).build();
            polygonsWithColors.add(bluePolygon);
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
            int segmentTransparency = (vertex1ColorValues[3] + vertex2ColorValues[3])/2;

            String colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue + "," + segmentTransparency;


            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();

            segmentsWithColors.add(colored);

        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).addAllPolygons(polygonsWithColors).build();
    }

    //edited this to include the opacity/transparency of the vertices and segments
    private int[] extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }

        if(val == null){
            return new int[]{0,0,0,255};
        }

        String[] temp = val.split(",");
        int red = Integer.parseInt(temp[0]);
        int green = Integer.parseInt(temp[1]);
        int blue = Integer.parseInt(temp[2]);
        int transparency = Integer.parseInt(temp[3]);

        return new int[]{red, green, blue, transparency};
    }

}
