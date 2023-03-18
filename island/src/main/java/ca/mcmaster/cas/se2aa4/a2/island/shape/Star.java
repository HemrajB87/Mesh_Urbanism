package ca.mcmaster.cas.se2aa4.a2.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Point2D;

public class Star implements Shape {

    private final double radius;
    private final Point2D.Double centerPoint;
    public Star(Point2D.Double centerPoint, double radius){
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public boolean inShape(Structs.Vertex centroid){

        double centroidX = centroid.getX();
        double centroidY = centroid.getY();

        int[] xPoints = new int[10];
        int[] yPoints = new int[10];

        for (int i = 0; i < 10; i++) {
            double angle = i * Math.PI / 5;
            if (i % 2 == 0) {
                xPoints[i] = (int) (centerPoint.getX() + (radius * Math.cos(angle) / 2));
                yPoints[i] = (int) (centerPoint.getY() + (int) (radius * Math.sin(angle) / 2));
            } else {
                xPoints[i] = (int) (centerPoint.getX() + (radius * Math.cos(angle)));
                yPoints[i] = (int) (centerPoint.getY() + (radius * Math.sin(angle)));
            }
        }

        Polygon star = new Polygon(xPoints, yPoints, 10);

        return star.contains(centroidX,centroidY);

    }

}
