package ca.mcmaster.cas.se2aa4.a2.island.islandTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Star;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Triangle;

import java.awt.geom.Point2D;

public class IslandSpecification {

    private final String mode;

    private final String altitude;
    private final String shape;
    private final Structs.Mesh aMesh;
    private final Point2D.Double centerPoint;
    private final String lakes;
    private final String rivers;
    public final String aquifers;
    public final String soil;
    public final String biomes;
    public final String seed;
    private final String city;

    public IslandSpecification(String mode, String shape, String altitude, String lakes, String rivers, String aquifers, String soil, String biomes, String seed, String city, Structs.Mesh passedMash, Point2D.Double centerPoint) {
        this.mode = mode;
        this.altitude= altitude; // added altitude
        this.shape = shape;
        this.aMesh = passedMash;
        this.centerPoint = centerPoint;
        this.lakes = lakes;
        this.rivers = rivers;
        this.aquifers = aquifers;
        this.soil = soil;
        this.biomes = biomes;
        this.seed = seed;
        this.city =city;
    }

    public Structs.Mesh islandGenerated(){

        Structs.Mesh island = aMesh;

        if (mode == null){
            //if mode == null then nothing will happen and the mesh passed in is just returned
            System.out.println("type of island was not specified");
        }

        else if (mode.equals("plain")) {
            Shape boundary;
            switch (shape) {
                case "circle" -> {
                    boundary = new Circle(centerPoint, centerPoint.getX() / 1.5);
                }
                case "triangle" -> {
                    boundary = new Triangle(centerPoint, centerPoint.getX() / 1.25);
                }
                case "star" -> {
                    boundary = new Star(centerPoint, centerPoint.getX() / 1.25);
                }
                default -> {
                    boundary = new Circle(centerPoint, centerPoint.getX() / 1.25);
                }
            }

            PlainIsland regular = new PlainIsland(boundary,mode,altitude, lakes, rivers, aquifers, soil, biomes, seed,city, aMesh);
            island = regular.createIsland();

        }

        else if(mode.equals("lagoon")){

            Shape innerBound;
            Shape outerBound;
            switch (shape) {
                case "circle" -> {
                    innerBound = new Circle(centerPoint, centerPoint.getX() / 5);
                    outerBound = new Circle(centerPoint, centerPoint.getX() / 1.5);
                }
                case "triangle" -> {
                    innerBound = new Triangle(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Triangle(centerPoint, centerPoint.getX() / 1.25);
                }
                case "star" -> {
                    innerBound = new Star(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Star(centerPoint, centerPoint.getX() / 1.25);
                }
                default -> {
                    System.out.println("Shape was not specified for lagoon island so a default circle island was given");
                    innerBound = new Circle(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Circle(centerPoint, centerPoint.getX() / 1.25);
                }
            }

            LagoonIsland lagoon = new LagoonIsland(innerBound, outerBound,mode,altitude, lakes, rivers, aquifers, soil, biomes, seed, city, aMesh);
            island = lagoon.createIsland();

        } else if (mode.equals("land")){

            Shape innerBound;
            Shape outerBound;
            switch (shape) {
                case "circle" -> {
                    innerBound = new Circle(centerPoint, centerPoint.getX() / 5);
                    outerBound = new Circle(centerPoint, centerPoint.getX() / 1.5);
                }
                case "triangle" -> {
                    innerBound = new Triangle(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Triangle(centerPoint, centerPoint.getX() / 1.25);
                }
                case "star" -> {
                    innerBound = new Star(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Star(centerPoint, centerPoint.getX() / 1.25);
                }
                default -> {
                    System.out.println("Shape was not specified for lagoon island so a default circle island was given");
                    innerBound = new Circle(centerPoint, centerPoint.getX() / 3);
                    outerBound = new Circle(centerPoint, centerPoint.getX() / 1.25);
                }
            }

            LagoonIsland lagoon = new LagoonIsland(innerBound, outerBound,mode,altitude, lakes, rivers, aquifers, soil, biomes, seed,city, aMesh);
            island = lagoon.createIsland();

        }

        else {
            //could add a default island if mode is some random input
            System.out.println("random stuff was passed into mode");

        }

        return island;

    }

}
