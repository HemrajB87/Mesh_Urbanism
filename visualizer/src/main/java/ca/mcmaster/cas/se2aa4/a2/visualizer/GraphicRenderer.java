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
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        for (Vertex v: aMesh.getVerticesList()) {

            System.out.println(v);

            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);

            System.out.println("Graphic Renderer" + centre_x + "," + centre_y);

            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);

        }

        ArrayList<Vertex> vertexArrayList = new ArrayList<>(aMesh.getVerticesList());

        //idk what the fuck is going on
        for (Segment s: aMesh.getSegmentsList()) {

            int drawSegment1 = s.getV1Idx();
            int drawSegment2 = s.getV2Idx();

            Vertex vertex1 = vertexArrayList.get(drawSegment1);
            Vertex vertex2 = vertexArrayList.get(drawSegment2);

            Line2D segment = new Line2D.Double(vertex1.getX(),vertex1.getY(),vertex2.getX(),vertex2.getY());

            canvas.setColor(Color.BLUE);
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
