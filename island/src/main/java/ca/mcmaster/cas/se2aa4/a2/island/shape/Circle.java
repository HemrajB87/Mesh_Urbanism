package ca.mcmaster.cas.se2aa4.a2.island.shape;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.geom.Point2D;
import java.util.Random;

import static java.lang.Double.*;

public class Circle {

    private double circleCenterX;
    private double circleCenterY;
    private Random random = new Random();

    //radius is chosen randomly by selecting a value from a range of the circleCenter and half of it
    private double radius;


    public Circle(double circleCenterX, double circleCenterY){
        this.circleCenterX = circleCenterX;
        this.circleCenterY = circleCenterY;
        this.radius = circleCenterX/2 + (circleCenterX - circleCenterX/2) * random.nextDouble();
    }

    public boolean inCircle(Structs.Vertex centroid){

        //gets the x and y coordinates of the centroid
        double centroidX = centroid.getX();
        double centroidY = centroid.getY();

        //creates Point2D objects to simplify the code needed to calculate the distance
        Point2D.Double centroidPoint = new Point2D.Double(centroidX,centroidY);
        Point2D.Double circleCenter = new Point2D.Double(circleCenterX,circleCenterY);

        //calculates the distance in between the centroid and the circle center (currently hard coded to have a value of 20)
        double distance = centroidPoint.distance(circleCenter);


        //compare the value of the radius with the distance
        int comparison = compare(distance,radius);

        //if the distance is less than the radius that means it is within the circle so we return true
        return comparison <= 0;
    }


}
