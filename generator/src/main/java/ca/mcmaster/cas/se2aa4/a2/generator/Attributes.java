package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attributes {

    private ArrayList<Structs.Vertex> verticesNoColors;

    private ArrayList<Structs.Segment> segmentsNoColors;
    private ArrayList<Structs.Polygon> polygonsNoColors;

    private ArrayList<Structs.Vertex> verticesWithColors = new ArrayList<>();

    private ArrayList<Structs.Segment> segmentsWithColors = new ArrayList<>();

    private ArrayList<Structs.Polygon> polygonsWithColors = new ArrayList<>();

    private Random random = new Random();

    //constructor of the Attributes class
    public Attributes(ArrayList<Structs.Vertex> verticesNoColors, ArrayList<Structs.Segment> segmentsNoColors, ArrayList<Structs.Polygon> polygonsNoColors) {
        this.verticesNoColors = verticesNoColors;
        this.segmentsNoColors = segmentsNoColors;
        this.polygonsNoColors = polygonsNoColors;
    }

    //creates a random color code
    private String getRandomColorCode(){
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        int transparency = random.nextInt(255);
        return red + "," + green + "," + blue + "," + transparency;
    }

    //creates a random thickness float value from 1-6
    private String getRandomThickness(){
        float thicknessValue = random.nextFloat(5) + 1;
        return String.valueOf(thicknessValue);
    }

    //gets the colors of a vertex by randomly generating the RGB and transparency values from getRandomColorCode
    //gets the thickness of the vertex by randomly generating a float value from 1-6
    public void setVertexProperties(){
        for(Vertex v: verticesNoColors){
            String colorCode = getRandomColorCode();
            String thickness = getRandomThickness();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property vertexThickness = Property.newBuilder().setKey("thickness").setValue(thickness).build();

            Vertex colored = Vertex.newBuilder(v).addProperties(color).addProperties(vertexThickness).build();

            verticesWithColors.add(colored);
        }
    }

    //calculates the color of the segments by taking the average RGB values of the two vertices it connects
    //gets the thickness of the segment by getting a random float value from 1-6
    public void setSegmentProperties(){
        for(Segment s: segmentsNoColors){
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
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

            String thickness = getRandomThickness();

            Structs.Property segmentThickness = Structs.Property.newBuilder().setKey("thickness").setValue(thickness).build();
            Structs.Segment colored = Structs.Segment.newBuilder(s).addProperties(color).addProperties(segmentThickness).build();

            segmentsWithColors.add(colored);
        }
    }

    //gets the colors of a vertex by randomly generating the RGB and transparency values from getRandomColorCode
    //gets the thickness of the vertex by randomly generating a float value from 1-6
    public void setPolygonProperties(){
        for(Polygon p: polygonsNoColors){
            String colorCode = getRandomColorCode();
            String thickness = getRandomThickness();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property vertexThickness = Property.newBuilder().setKey("thickness").setValue(thickness).build();

            Polygon colored = Polygon.newBuilder(p).addProperties(color).addProperties(vertexThickness).build();

            polygonsWithColors.add(colored);
        }
    }

    //returns an int array with the rbg and transparency values in them to aid in the color setting of the segments
    private int[] extractColor(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
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

    //returns the generated vertices, segments and polygons that now have colors and thickness properties attached to them
    public ArrayList<Vertex> getVerticeswithColors(){ return verticesWithColors; }
    public ArrayList<Segment> getSegmentsWithColors(){ return segmentsWithColors; }
    public ArrayList<Polygon> getPolygonsWithColors(){ return polygonsWithColors; }

}
