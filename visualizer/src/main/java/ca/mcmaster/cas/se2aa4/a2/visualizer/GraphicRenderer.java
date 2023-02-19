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
import java.util.ArrayList;
import java.util.List;

public class GraphicRenderer {

    //thickness before was 3
    //Thickness only affects the size of the circle created around the vertex
    private static final int THICKNESS = 1;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);

        //THIS CODE IS IMPORTANT SINCE IT CHANGES THE THICKNESS OF THE LINES
        Stroke stroke = new BasicStroke(0.25f);

        //THIS TELLS THE CANVAS THAT THE THICKNESS OF THE LINE NEEDS TO BE SET TO THE NEW VALUE WE PASS IN
        canvas.setStroke(stroke);

        for (Vertex v: aMesh.getVerticesList()) {

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
            int vertex1Position = s.getV1Idx();

            //Gets the second vertex from the current segment
            int vertex2Position = s.getV2Idx();

            //determines the vertices vertex1 and vertex2 from the value of vertex1Position and vertex2Position
            Vertex vertex1 = vertexArrayList.get(vertex1Position);
            Vertex vertex2 = vertexArrayList.get(vertex2Position);

            //Sets the color by extracting it from the current segments property list
            canvas.setColor(extractColor(s.getPropertiesList()));

            //creates a Line2D object that draws a line from the first vertex to the second vertex based on their coordinates
            Line2D segment = new Line2D.Double(vertex1.getX(),vertex1.getY(),vertex2.getX(),vertex2.getY());

            canvas.draw(segment);

        }
    }

    //Changed this so that it accounts for the transparency/opacity of the segment or the vertices
    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {

                //Debugging
                //System.out.println(p.getValue());

                val = p.getValue();
            }
        }

        if (val == null)
            return new Color(0,0,0,255);
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int transparency = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, transparency);
    }

}
