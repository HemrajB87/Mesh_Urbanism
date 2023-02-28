package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;

//import java.awt.*;
//import java.io.IOException;
//
//import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import static ca.mcmaster.cas.se2aa4.a2.generator.DotGen.createSegment;
import static ca.mcmaster.cas.se2aa4.a2.generator.DotGen.createVertex;


public class GridMesh {

    private int width;
    private int height;
    private int square_size;
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();
    private ArrayList<Polygon> polygonsWithNeighbors = new ArrayList<>();


    //need to pass in the width, height, and square size of the grid
    public GridMesh(int width, int height, int square_size) {
        this.width = width;
        this.height = height;
        this.square_size = square_size;
    }

    public void createGridPolygons() {

        for (int y = 0; y < width; y += square_size) {
            for (int x = 0; x < height; x += square_size) {
                //Can Change the vertex creation to random mode by using createRandomVertex method instead, aswell as on the centroid but we prob will have to use a library for that.
                Vertex topLeft = createVertex((double) x, (double) y, vertices);
                Vertex topRight = createVertex((double) x + square_size, (double) y, vertices);
                Vertex bottomLeft = createVertex((double) x, (double) y + square_size, vertices);
                Vertex bottomRight = createVertex((double) x + square_size, (double) y + square_size, vertices);

                //centroid is calculated by finding the average of the x and y coordinates of the top left vertex and bottom right vertex
                Vertex centroid = createVertex(((double) x + x + square_size) / 2, ((double) y + y + square_size) / 2, vertices);

                //this finds the index of the created vertices in the vertices list, this helps in creating line segments
                int topLeftVertexPosition = vertices.indexOf(topLeft);
                int topRightVertexPosition = vertices.indexOf(topRight);
                int bottomLeftVertexPosition = vertices.indexOf(bottomLeft);
                int bottomRightVertexPosition = vertices.indexOf(bottomRight);

                //note the centroid is a vertex created in the middle of the polygon
                int centroidPosition = vertices.indexOf(centroid);

                //draws the top line of the square by joining the top left vertex to the top right one
                Segment topLineSegment = createSegment(topLeftVertexPosition, topRightVertexPosition, segments);

                //draws the right line of the square by joining the top right vertex to the bottom right one
                Segment rightLineSegment = createSegment(topRightVertexPosition, bottomRightVertexPosition, segments);

                //draws the bottom line of the square by joining the bottom left vertex to the bottom right one
                Segment bottomLineSegment = createSegment(bottomLeftVertexPosition, bottomRightVertexPosition, segments);

                //draws the left line of the square by joining the top left vertex to the bottom left one
                Segment leftLineSegment = createSegment(topLeftVertexPosition, bottomLeftVertexPosition, segments);


                int topLinePosition = segments.indexOf(topLineSegment);
                int rightLinePosition = segments.indexOf(rightLineSegment);
                int bottomLinePosition = segments.indexOf(bottomLineSegment);
                int leftLinePosition = segments.indexOf(leftLineSegment);


                Polygon polygonCreated = Polygon.newBuilder().addSegmentIdxs(topLinePosition)
                        .addSegmentIdxs(rightLinePosition)
                        .addSegmentIdxs(bottomLinePosition)
                        .addSegmentIdxs(leftLinePosition)
                        .setCentroidIdx(centroidPosition).
                        build();
                polygons.add(polygonCreated);

            }
        }

        findNeighboringPolygons();
    }

    private void findNeighboringPolygons(){

        //loops through the polygons list once
        for (Polygon p : polygons) {

            //creates an integer list that stores all the indexes of the neighbouring polygons
            ArrayList<Integer> polygonsNeighborPositionList = new ArrayList<>();

            //loops through polygons again in order to check if any polygons in the list are neighbors to the current polygon p
            for (Polygon j : polygons) {

                //this ensures that polygon j != polygon p because if they were equal then it would be comparing to identical polygons, which is useless
                if (j != p) {

                    //sets a temporary integer list that stores the segments of polygon j
                    ArrayList<Integer> temp = new ArrayList<>(j.getSegmentIdxsList());

                    //determines if there are any segments that are shared between polygon p and polygon j
                    temp.retainAll(p.getSegmentIdxsList());

                    //if there is then the temp size should not be 0
                    if (temp.size() != 0) {

                        //if a shared segment is identified then the polygons would be neighbors, so we add the index of the neighboring polygon to the polygonsNeighborPositionList
                        int neighbouringPolygonPosition = polygons.indexOf(j);
                        polygonsNeighborPositionList.add(neighbouringPolygonPosition);
                    }
                }
            }

            //once we are done finding all the neighbors of p we modify our existing polygon to include the neighbors it references
            Polygon newPolygon = Polygon.newBuilder(p).addAllNeighborIdxs(polygonsNeighborPositionList).build();

            //adds the new polygons with neighbors into a new list
            polygonsWithNeighbors.add(newPolygon);
        }

    }

    public ArrayList<Vertex> getVertices(){ return vertices; }
    public ArrayList<Segment> getSegments(){ return segments; }
    public ArrayList<Polygon> getPolygonsWithNeighbors(){ return polygonsWithNeighbors; }

}
