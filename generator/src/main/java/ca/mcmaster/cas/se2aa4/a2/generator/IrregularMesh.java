package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import org.locationtech.jts.algorithm.Centroid;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.a2.generator.DotGen.createVertex;
import static ca.mcmaster.cas.se2aa4.a2.generator.DotGen.createSegment;

//FINISH THIS SHITE TOMORROW

public class IrregularMesh {

    private int width;
    private int height;
    private int numPolygons;
    private int relaxationLevel;

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Structs.Polygon> polygons = new ArrayList<>();

    private ArrayList<Structs.Polygon> polygonsWithNeighbors = new ArrayList<>();

    public IrregularMesh(int width, int height, int numPolygons, int relaxationLevel) {
        this.width = width;
        this.height = height;
        this.numPolygons = numPolygons;
        this.relaxationLevel = relaxationLevel;
    }


    //GENERATES VORONOI DIAGRAM
    public void createVoronoi() {
// Create a precision model with 2 decimal places
        PrecisionModel precisionModel = new PrecisionModel(10);

        // Create a geometry factory with the precision model
        GeometryFactory geometryFactory = new GeometryFactory(precisionModel);
        Random rand = new Random();

        //create random coordinates within the canvas width and height parameters passed in
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < numPolygons; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            Coordinate c = new Coordinate(x,y);
            coordinates.add(c);
        }

        //THIS IS THE ADDED CODE FOR RELAXATION....
        // loops based on the relaxationLevel provided in the DotGen class

        for (int iteration = 0; iteration < relaxationLevel; iteration++) {

            // We are going to constantly make a new voronoi diagram to make the mesh less pointy and more smooth
            VoronoiDiagramBuilder tempVoronoi = new VoronoiDiagramBuilder();
            tempVoronoi.setSites(coordinates);

            Geometry tempVoronoiDiagram = tempVoronoi.getDiagram(geometryFactory);

            //bounds the diagram within the canvas
            Envelope tempEnvelope = new Envelope(0, width, 0, height);
            Geometry tempCanvas = geometryFactory.toGeometry(tempEnvelope);

            // Crop the Voronoi diagram to be contained inside the canvas, since during the initial generation its points can be located outside the boundaries
            Geometry tempCroppedDiagram = tempVoronoiDiagram.intersection(tempCanvas);

            // Updates the points to be the centroids of the polygons in the Voronoi diagram
            List<Coordinate> newPoints = new ArrayList<>();
            for (int i = 0; i < tempCroppedDiagram.getNumGeometries(); i++) {

                // Get the current polygon
                org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) tempCroppedDiagram.getGeometryN(i);

                // Get the centroid of the current polygon
                Point centroidPoint = polygon.getCentroid();

                // Convert the Point object to a Coordinate and add it to the new list of points
                Coordinate centroidCoordinate = centroidPoint.getCoordinate();

                //add the centroid to the newPoints array list
                newPoints.add(centroidCoordinate);
            }

            //sets the coordinates arraylist to the newPoints arraylist, so we can repeatedly apply lloyd's relaxation on new a set of coordinates
            coordinates = newPoints;

        }
        //END OF RELAXATION

        // Compute the FINAL Voronoi diagram
        VoronoiDiagramBuilder updatedVoronoi = new VoronoiDiagramBuilder();

        //coordinates should be the final version of the voronoi diagram we want to display
        updatedVoronoi.setSites(coordinates);

        Geometry updatedVoronoiDiagram = updatedVoronoi.getDiagram(geometryFactory);

        //Sets a boundary that forces the points to be contained in
        Envelope envelope = new Envelope(0, width, 0, height);
        Geometry canvas = geometryFactory.toGeometry(envelope);

        //crops the voronoi Diagram to be contained inside the canvas, since during the initial generation its points can be located outside the boundaries
        Geometry updatedCroppedDiagram = updatedVoronoiDiagram.intersection(canvas);

        ArrayList<Coordinate> centroidList = new ArrayList<>();

        //this is used to convert the JTS objects back to the Structs objects so we can use them in the Mesh.newBuilder().build call
        for (int i = 0; i < updatedCroppedDiagram.getNumGeometries(); i++) {

            //we are specifying that we want to use the Polygon class from JTS and not from the Structs.java class
            org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) updatedCroppedDiagram.getGeometryN(i);

            //gets coordinates of the current polygon
            Coordinate[] updatedPolygonCoordinates = polygon.getCoordinates();

            // Compute the vertices and segments for the current polygon
            ArrayList<Integer> currentPolygonSegments = new ArrayList<>();
            for (int j = 0; j < updatedPolygonCoordinates.length - 1; j++) {
                Coordinate p1 = updatedPolygonCoordinates[j];
                Coordinate p2 = updatedPolygonCoordinates[j + 1];

                Vertex vertex1 = createVertex(p1.x,p1.y,vertices);
                Vertex vertex2 = createVertex(p2.x,p2.y,vertices);

                int vertex1Position = vertices.indexOf(vertex1);
                int vertex2Position = vertices.indexOf(vertex2);

                Segment line = createSegment(vertex1Position,vertex2Position, segments);

                int segmentPosition = segments.indexOf(line);
                currentPolygonSegments.add(segmentPosition);
            }

            //gets the centroid of the current polygon created by the voronoiDiagramBuilder
            Point centroidPoint = polygon.getCentroid();

            centroidList.add(new Coordinate(centroidPoint.getX(), centroidPoint.getY()));

            //converts the Point object to a Vertex Object, so we can use it in our Polygon.newBuilder()
            Vertex centroid = createVertex(centroidPoint.getX(),centroidPoint.getY(),vertices);

            int centroidPosition = vertices.indexOf(centroid);

            Structs.Polygon createdPolygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(currentPolygonSegments).setCentroidIdx(centroidPosition).build();
            polygons.add(createdPolygon);

        }

        findNeighbors(centroidList,updatedCroppedDiagram);
    }

    public void findNeighbors(ArrayList<Coordinate> centroidList, Geometry updatedCroppedDiagram ){

        DelaunayTriangulationBuilder delaunayTriangulation = new DelaunayTriangulationBuilder();
        delaunayTriangulation.setSites(centroidList);
        delaunayTriangulation.getTriangles(updatedCroppedDiagram.getFactory());

        Geometry triangleSegments = delaunayTriangulation.getEdges(updatedCroppedDiagram.getFactory());

        for(Structs.Polygon p: polygons){

            //this list is used to track which polygons are neighbors, so when we create a polygon we can add all the neighboring polygons indexes
            List<Integer> neighboringPolygonPosition = new ArrayList<>();

            Vertex targetCentroid = vertices.get(p.getCentroidIdx());

            for(int i = 0; i<triangleSegments.getNumGeometries(); i++){
                LineString currentSegment = (LineString) triangleSegments.getGeometryN(i);

                //System.out.println(currentSegment);

                //since we are looking at a line we will have a starting point and an ending point, so we can set them to different variables
                Coordinate start = currentSegment.getCoordinateN(0);
                Vertex startCentroid = Vertex.newBuilder().setX(start.x).setY(start.y).build();

                Coordinate end = currentSegment.getCoordinateN(1);
                Vertex endCentroid = Vertex.newBuilder().setX(end.x).setY(end.y).build();


                //checks if the centroid coordinates we are currently looking at is equal to one of the coordinates in the currentSegment value

                //if the current centroid coordinates are equal to the start coordinates then we want to find the polygon that has the end coordinates
                //this is so that we can make the polygon with the centroid we are currently looping through a neighbor of the one we found
                if(targetCentroid.equals(startCentroid)){

                    //gets the position of the centroid in the vertices list
                    int endCentroidPosition = vertices.indexOf(endCentroid);

                    //loops through all the polygons again and if polygon j's index is the same as the endCentroidPosition then it will be a neighbor of polygon p
                    //so we need to find the index of j and add it to the neighboringPolygonPosition list
                    for(Structs.Polygon j: polygons) {
                        if(j.getCentroidIdx() == endCentroidPosition){
                            neighboringPolygonPosition.add(polygons.indexOf(j));
                        }
                    }
                }

                //does the same thing as the if statement just that we want to check if c is equal to the end coordinate and find the start coordinate's polygon instead
                else if (targetCentroid.equals(endCentroid)) {

                    //gets the position of the centroid in the vertices list
                    int startCentroidPosition = vertices.indexOf(startCentroid);

                    //loops through all the polygons again and if polygon j's index is the same as the startCentroidPosition then it will be a neighbor of polygon p
                    //so we need to find the index of j and add it to the neighboringPolygonPosition list
                    for(Structs.Polygon j: polygons) {
                        if(j.getCentroidIdx() == startCentroidPosition){
                            neighboringPolygonPosition.add(polygons.indexOf(j));
                        }
                    }
                }

            }

            Structs.Polygon polygonWithNeighbor = Structs.Polygon.newBuilder(p).addAllNeighborIdxs(neighboringPolygonPosition).build();
            polygonsWithNeighbors.add(polygonWithNeighbor);
        }
    }

    public ArrayList<Vertex> getVertices(){ return vertices; }
    public ArrayList<Segment> getSegments(){ return segments; }
    public ArrayList<Structs.Polygon> getPolygonsWithNeighbors() { return polygonsWithNeighbors; }

}

