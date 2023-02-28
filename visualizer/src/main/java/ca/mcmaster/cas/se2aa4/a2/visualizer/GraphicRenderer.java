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
        ArrayList<Polygon> polygonArrayList = new ArrayList<>(aMesh.getPolygonsList());

        canvas.setColor(Color.BLACK);

        //THIS CODE IS IMPORTANT SINCE IT CHANGES THE THICKNESS OF THE LINES
        Stroke stroke = new BasicStroke(0.25f);

        //THIS TELLS THE CANVAS THAT THE THICKNESS OF THE LINE NEEDS TO BE SET TO THE NEW VALUE WE PASS IN
        canvas.setStroke(stroke);


        for (Polygon p : aMesh.getPolygonsList()){
            // get number of segments in polygon
            int count = p.getSegmentIdxsCount();

            //get x and y coord of the centroid
            double centroidX = vertexArrayList.get(p.getCentroidIdx()).getX();
            double centroidY = vertexArrayList.get(p.getCentroidIdx()).getY();

            //Debug Mode
            if(debugMode){
                //draw the centroid in red for debug mode, can change thickness var to what you want
                THICKNESS = 3;
                canvas.setColor(new Color(255, 0, 0, 255));
                Ellipse2D centroid = new Ellipse2D.Double(centroidX - (THICKNESS/2.0d), centroidY - (THICKNESS/2.0d), THICKNESS, THICKNESS);
                canvas.fill(centroid);

                //neighboring polygons relationships drawn
                int count1 = p.getNeighborIdxsCount();
                // iterating through neighboring index count
                for(int i =0; i<count1;i++){
                    int p1 = p.getNeighborIdxs(i); // getting neighboring index
                    int cIndex = polygonArrayList.get(p1).getCentroidIdx(); // get centroid of neighboring polygon
                    double centroid2X = vertexArrayList.get(cIndex).getX();
                    double centroid2Y = vertexArrayList.get(cIndex).getY();
                    canvas.setColor(new Color(108, 108, 108, 255)); //set line color to grey
                    //draw line between centroids of the two polygons
                    canvas.draw(new Line2D.Double(centroidX, centroidY, centroid2X, centroid2Y));
                }


                //polygon
                canvas.setColor(new Color(0, 0, 0, 255));
                Stroke polygonStroke = new BasicStroke(0.5f);
                canvas.setStroke(polygonStroke);

            } else {
                //sets the color of the segments when drawing the polygon
                canvas.setColor(extractColor(p.getPropertiesList()));

                //sets the thickness of the segments when drawing the polygon
                Stroke polygonStroke = new BasicStroke(extractThickness(p.getPropertiesList()));
                canvas.setStroke(polygonStroke);
            }
            // iterating through each segment
            for(int i =0; i<count;i++) {
                // get segment index, in order to get coords
                int segmentIndices = p.getSegmentIdxs(i);

                // Get the vertex indices for the two endpoints of the segment
                int v1Index = segmentArrayList.get(segmentIndices).getV1Idx();
                int v2Index = segmentArrayList.get(segmentIndices).getV2Idx();

                // Get the X and Y coordinates for the two endpoints of the segment
                double x1 = vertexArrayList.get(v1Index).getX();
                double y1 = vertexArrayList.get(v1Index).getY();
                double x2 = vertexArrayList.get(v2Index).getX();
                double y2 = vertexArrayList.get(v2Index).getY();

                // Draw the segment
                canvas.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
            }
        }
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
