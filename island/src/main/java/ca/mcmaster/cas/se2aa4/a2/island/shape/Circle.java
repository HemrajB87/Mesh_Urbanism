package ca.mcmaster.cas.se2aa4.a2.island.shape;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.geom.Point2D;

import static java.lang.Double.*;

public class Circle implements Shape {

    private final Point2D.Double centerPoint;
    private final double radius;


    public Circle(Point2D.Double centerPoint, double radius){
        this.centerPoint = centerPoint;
        this.radius = radius;
    }
    @Override
    public boolean inShape(Structs.Vertex centroid){

        double centroidX = centroid.getX();
        double centroidY = centroid.getY();

        Point2D.Double centroidPoint = new Point2D.Double(centroidX,centroidY);

        double distance = centroidPoint.distance(centerPoint);

        int comparison = compare(distance,radius);

        return comparison <= 0;
    }


}
