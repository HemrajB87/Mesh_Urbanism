package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicRenderer {

    //thickness before was 3
    //Thickness only affects the size of the circle created around the vertex

    private static int THICKNESS = 3;

    public void render(Mesh aMesh, Graphics2D canvas, boolean debugMode) {

        ArrayList<Vertex> vertexArrayList = new ArrayList<>(aMesh.getVerticesList());
        ArrayList<Segment> segmentArrayList = new ArrayList<>(aMesh.getSegmentsList());

        canvas.setColor(Color.BLACK);

        //THIS CODE IS IMPORTANT SINCE IT CHANGES THE THICKNESS OF THE LINES
        Stroke stroke = new BasicStroke(0.25f);

        //THIS TELLS THE CANVAS THAT THE THICKNESS OF THE LINE NEEDS TO BE SET TO THE NEW VALUE WE PASS IN
        canvas.setStroke(stroke);

        for (Vertex v: aMesh.getVerticesList()) {

            THICKNESS = (int) extractThickness(v.getPropertiesList());
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);

            Color old = canvas.getColor();

            //debug mode
            if(debugMode){
                canvas.setColor(new Color(0, 0, 0, 255));
                THICKNESS = 3; // Setting point thickness to fixed value for ease of viewing when testing
                centre_x = v.getX() - (THICKNESS/2.0d);
                centre_y = v.getY() - (THICKNESS/2.0d);

            } else {
                canvas.setColor(extractColor(v.getPropertiesList()));

            }

            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        //Loops through all the segments to give them colors and draw them
        for (Segment s: aMesh.getSegmentsList()) {

            //Gets the first vertex from the current segment
            int vertex1Position = s.getV1Idx();

            //Gets the second vertex from the current segment
            int vertex2Position = s.getV2Idx();

            //determines the vertices vertex1 and vertex2 from the value of vertex1Position and vertex2Position
            Vertex vertex1 = vertexArrayList.get(vertex1Position);
            Vertex vertex2 = vertexArrayList.get(vertex2Position);

            //debug mode
            if(debugMode){
                canvas.setColor(new Color(96, 96, 96, 255));
                Stroke segmentStroke = new BasicStroke(0.5f);
                canvas.setStroke(segmentStroke);

            } else {
                //Sets the color by extracting it from the current segments property list
                canvas.setColor(extractColor(s.getPropertiesList()));

                //Sets the thickness of the segments by extracting it from the property list
                Stroke segmentStroke = new BasicStroke(extractThickness(s.getPropertiesList()));
                canvas.setStroke(segmentStroke);
            }

            //creates a Line2D object that draws a line from the first vertex to the second vertex based on their coordinates
            Line2D segment = new Line2D.Double(vertex1.getX(),vertex1.getY(),vertex2.getX(),vertex2.getY());
            canvas.draw(segment);

        }

        //HAD TO COMMENT THIS OUT BECAUSE THIS IS ONLY CATERED TO RENDERING A RECTANGLE AND CAUSED ISSUES FOR THE VORONOI DIAGRAM
//        for (Polygon p : aMesh.getPolygonsList()){
//            //gets the x and y values of the topLeftVertex of the rectangle/square
//            double rectangleX = vertexArrayList.get(segmentArrayList.get(p.getSegmentIdxs(0)).getV1Idx()).getX();
//            double rectangleY = vertexArrayList.get(segmentArrayList.get(p.getSegmentIdxs(0)).getV1Idx()).getY();
//
//            //get x and y coord of the centroid
//            double centroidX = vertexArrayList.get(p.getCentroidIdx()).getX();
//            double centroidY = vertexArrayList.get(p.getCentroidIdx()).getY();
//
//            //Debug Mode
//            if(debugMode){
//                //draw the centroid in red for debug mode, can change thickness var to what you want
//                THICKNESS = 3;
//                canvas.setColor(new Color(255, 0, 0, 255));
//                Ellipse2D centroid = new Ellipse2D.Double(centroidX - (THICKNESS/2.0d), centroidY - (THICKNESS/2.0d), THICKNESS, THICKNESS);
//                canvas.fill(centroid);
//
//                //polygon
//                canvas.setColor(new Color(96, 96, 96, 255));
//                Stroke polygonStroke = new BasicStroke(0.5f);
//                canvas.setStroke(polygonStroke);
//
//            } else {
//                //sets the color of the segments when drawing the polygon
//                canvas.setColor(extractColor(p.getPropertiesList()));
//
//                //sets the thickness of the segments when drawing the polygon
//                Stroke polygonStroke = new BasicStroke(extractThickness(p.getPropertiesList()));
//                canvas.setStroke(polygonStroke);
//            }
//
//            //the rectangle is drawn starting form the passed in x and y coordinate, and the size of it is 20x20
//            //note: right now the size of the rectangle is hard coded
//            canvas.draw(new Rectangle2D.Double(rectangleX, rectangleY,20,20));
//        }
    }

    //Changed this so that it accounts for the transparency/opacity of the segment or the vertices
    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }

        if (val == null)
            return new Color(0, 0, 0,255);
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int transparency = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, transparency);
    }

    //Works similar to the extractColor method, but it just checks if there is a thickness property
    private float extractThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 1.0f;

        return Float.parseFloat(val);
    }

}
