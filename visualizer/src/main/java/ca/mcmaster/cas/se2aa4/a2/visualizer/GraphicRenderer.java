package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicRenderer {

    //thickness before was 3
    private static final int THICKNESS = 1;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);

        //THIS CODE IS IMPORTANT SINCE IT CHANGES THE THICKNESS OF THE LINES
        Stroke stroke = new BasicStroke(0.25f);

        //THIS TELLS THE CANVAS THAT THE THICKNESS OF THE LINE NEEDS TO BE SET TO THE NEW VALUE WE PASS IN
        canvas.setStroke(stroke);

        for (Vertex v: aMesh.getVerticesList()) {

            System.out.println(v);

            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);

            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));

            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);

        }

        ArrayList<Vertex> vertexArrayList = new ArrayList<>(aMesh.getVerticesList());

        //Loops through all the segments to give them colors and draw them
        for (Segment s: aMesh.getSegmentsList()) {

            //Gets the first vertex from the current segment
            int drawSegment1 = s.getV1Idx();

            //Gets the second vertex from the current segment
            int drawSegment2 = s.getV2Idx();

            //Gets the color of the first and second vertices, to find the average color between both of them
            Vertex vertex1 = vertexArrayList.get(drawSegment1);
            Color vertex1Color = extractColor(vertex1.getPropertiesList());
            Vertex vertex2 = vertexArrayList.get(drawSegment2);
            Color vertex2Color = extractColor(vertex2.getPropertiesList());

            //takes each rgb value from both vertices and finds the average of them
            int averageRed = (vertex1Color.getRed() + vertex2Color.getRed())/2;
            int averageGreen = (vertex1Color.getGreen() + vertex2Color.getGreen())/2;
            int averageBlue = (vertex1Color.getBlue() + vertex2Color.getBlue())/2;
            Color segmentColor = new Color(averageRed,averageGreen,averageBlue);
            System.out.println(segmentColor);

            //creates a Line2D object that draws a line from the first vertex to the second vertex based on their coordinates
            Line2D segment = new Line2D.Double(vertex1.getX(),vertex1.getY(),vertex2.getX(),vertex2.getY());

            canvas.setColor(segmentColor);
            canvas.draw(segment);

        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }

        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
