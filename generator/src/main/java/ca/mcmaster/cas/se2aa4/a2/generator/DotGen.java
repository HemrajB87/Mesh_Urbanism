package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;
import java.util.List;
import java.util.Random;
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

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();

    private ArrayList<Polygon> polygons = new ArrayList<>();

    //createVertex creates a vertex at the x and y coordinates given
    private Vertex createVertex(double x, double y){
        Vertex vertex = Vertex.newBuilder().setX(x).setY(y).build();

        //checks if the created vertex is not in the private vertices list, if it is not then it adds it to the vertices list
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
        }
        return vertex;
    }

    //createVertex creates a vertex at the x and y coordinates given with a RANDOM offset value
    private Vertex createRandomVertex(double x, double y){
        Random rand = new Random();
        //creates a random bound from 5 to 20. CAN BE CHANGED TO WHATEVER
        double randomPointIncrementX = rand.nextDouble(15) + 5;
        double randomPointIncrementY = rand.nextDouble(15) + 5;

        Vertex vertex = Vertex.newBuilder().setX(x+randomPointIncrementX).setY(y+randomPointIncrementY).build();

        //checks if the created vertex is not in the private vertices list, if it is not then it adds it to the vertices list
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
        }
        return vertex;
    }

    //createSegment creates a segment joining the two vertices given
    //Note: it draws from the first vertex passed in to the second one
    private Segment createSegment(int vertex1Idx, int vertex2Idx){
        Segment segment = Segment.newBuilder().setV1Idx(vertex1Idx).setV2Idx(vertex2Idx).build();

        //checks if the created segment is not in the private vertices list, if it is not then it adds it to the segment list
        if(!segments.contains(segment)){
            segments.add(segment);
        }
        return segment;
    }

    public Mesh generate() {
        // Create all the vertices
        for(int y = 0; y < width; y += square_size) {
            for(int x = 0; x < height; x += square_size) {
                //Can Change the vertex creation to random mode by using createRandomVertex method instead, aswell as on the centroid but we prob will have to use a library for that.
                Vertex topLeft = createVertex((double)x, (double) y);
                Vertex topRight = createVertex((double) x+square_size, (double) y);
                Vertex bottomLeft = createVertex((double)x, (double) y+square_size);
                Vertex bottomRight = createVertex((double)x+square_size, (double) y+square_size);

                //centroid is calculated by finding the average of the x and y coordinates of the top left vertex and bottom right vertex
                Vertex centroid = createVertex(((double)x+x+square_size)/2, ((double)y+y+square_size)/2);

                //this finds the index of the created vertices in the vertices list, this helps in creating line segments
                int topLeftVertexPosition = vertices.indexOf(topLeft);
                int topRightVertexPosition = vertices.indexOf(topRight);
                int bottomLeftVertexPosition = vertices.indexOf(bottomLeft);
                int bottomRightVertexPosition = vertices.indexOf(bottomRight);

                //note the centroid is a vertex created in the middle of the polygon
                int centroidPosition = vertices.indexOf(centroid);

                //draws the top line of the square by joining the top left vertex to the top right one
                Segment topLineSegment = createSegment(topLeftVertexPosition, topRightVertexPosition);

                //draws the right line of the square by joining the top right vertex to the bottom right one
                Segment rightLineSegment = createSegment(topRightVertexPosition, bottomRightVertexPosition);

                //draws the bottom line of the square by joining the bottom left vertex to the bottom right one
                Segment bottomLineSegment = createSegment(bottomLeftVertexPosition, bottomRightVertexPosition);

                //draws the left line of the square by joining the top left vertex to the bottom left one
                Segment leftLineSegment = createSegment(topLeftVertexPosition, bottomLeftVertexPosition);


                int topLinePosition = segments.indexOf(topLineSegment);
                int rightLinePosition = segments.indexOf(rightLineSegment);
                int bottomLinePosition = segments.indexOf(bottomLineSegment);
                int leftLinePosition = segments.indexOf(leftLineSegment);


                Polygon polygonCreated = Polygon.newBuilder().addSegmentIdxs(topLinePosition)
                        .addSegmentIdxs(rightLinePosition)
                        .addSegmentIdxs(bottomLinePosition)
                        .addSegmentIdxs(leftLinePosition)
                        .setCentroidIdx(centroidPosition).
                        build();
                polygons.add(polygonCreated);

            }
        }

        ArrayList<Polygon> polygonsWithNeighbors = new ArrayList<>();

        //loops through the polygons list once
        for(Polygon p: polygons){

            //creates an integer list that stores all the indexes of the neighbouring polygons
            ArrayList<Integer> polygonsNeighborPositionList = new ArrayList<>();

            //loops through polygons again in order to check if any polygons in the list are neighbors to the current polygon p
            for(Polygon j: polygons){

                //this ensures that polygon j != polygon p because if they were equal then it would be comparing to identical polygons, which is useless
                if(j!=p){

                    //sets a temporary integer list that stores the segments of polygon j
                    ArrayList<Integer> temp = new ArrayList<>(j.getSegmentIdxsList());

                    //determines if there are any segments that are shared between polygon p and polygon j
                    temp.retainAll(p.getSegmentIdxsList());

                    //if there is then the temp size should not be 0
                    if(temp.size() != 0){

                        //if a shared segment is identified then the polygons would be neighbors, so we add the index of the neighboring polygon to the polygonsNeighborPositionList
                        int neighbouringPolygonPosition = polygons.indexOf(j);
                        polygonsNeighborPositionList.add(neighbouringPolygonPosition);
                    }
                }
            }

            //once we are done finding all the neighbors of p we modify our existing polygon to include the neighbors it references
            Polygon newPolygon = Polygon.newBuilder(p).addAllNeighborIdxs(polygonsNeighborPositionList).build();

            //adds the new polygons with neighbors into a new list
            polygonsWithNeighbors.add(newPolygon);
        }

        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int transparency = bag.nextInt(255);

            //stores a thickness property that alters the size of the circle created at the vertex's coordinate
            float thickness = bag.nextFloat(5) + 1;

            //Transparency is added in the colorCode
            String colorCode = red + "," + green + "," + blue + "," + transparency;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property vertexThickness = Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build();

            Vertex colored = Vertex.newBuilder(v).addProperties(color).addProperties(vertexThickness).build();

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
            int segmentTransparency = (vertex1ColorValues[3] + vertex2ColorValues[3])/2;

            String colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue + "," + segmentTransparency;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

            float thickness = bag.nextFloat(3) + 1;
            Property segmentThickness = Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).addProperties(segmentThickness).build();

            segmentsWithColors.add(colored);

        }

        ArrayList<Polygon> polygonsWithColors = new ArrayList<>();
        for(Polygon p: polygonsWithNeighbors) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int polygonTransparency = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue + "," + polygonTransparency;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

            int thickness = bag.nextInt(3) + 1;
            Property polygonThickness = Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build();
            Polygon colored = Polygon.newBuilder(p).addProperties(color).addProperties(polygonThickness).build();
            polygonsWithColors.add(colored);
        }

        System.out.println(verticesWithColors.size());
        System.out.println(segmentsWithColors.size());
        System.out.println(polygonsWithColors);

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
