package ca.mcmaster.cas.se2aa4.a2.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Point2D;

public class Triangle implements Shape{

    private final double radius;

    private final Point2D.Double centerPoint;

    public Triangle(Point2D.Double centerPoint, double radius){
        this.radius = radius;
        this.centerPoint = centerPoint;
    }

    @Override
    public boolean inShape(Structs.Vertex centroid){

        double centroidX = centroid.getX();
        double centroidY = centroid.getY();

        // Calculate the coordinates of the three vertices of the triangle
        double x1 = centerPoint.getX();
        double y1 = centerPoint.getY() - radius;
        double x2 = centerPoint.getX() - (radius * Math.sin(Math.PI / 3));
        double y2 = centerPoint.getY() + (radius * Math.cos(Math.PI / 3));
        double x3 = centerPoint.getX() + (radius * Math.sin(Math.PI / 3));
        double y3 = y2;

        int[] xPoints = {(int) x1, (int) x2, (int) x3};
        int[] yPoints = { (int) y1, (int) y2, (int) y3};
        Polygon triangle = new Polygon(xPoints, yPoints, 3);

        return triangle.contains(centroidX, centroidY);

    }

}
