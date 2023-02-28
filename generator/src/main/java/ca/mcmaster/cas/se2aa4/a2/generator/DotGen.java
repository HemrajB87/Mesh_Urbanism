package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;
import java.util.List;
import java.util.Random;

//import java.awt.*;
//import java.io.IOException;
//
//import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;


public class DotGen{

    private int width = 500;
    private int height = 500;
    private int square_size = 20;

    private ArrayList<Vertex> verticesMeshList = new ArrayList<>();
    private ArrayList<Segment> segmentsMeshList = new ArrayList<>();
    private ArrayList<Polygon> polygonsMeshList = new ArrayList<>();

    private static String meshType = "";
    private static int numPolygons = 0;
    private static int relaxationLevel = 0;

    //set the vars from Main class command line
    public void setVar(String meshtype, int numpolygons, int relaxationlevel){
        meshType = meshtype;
        numPolygons = numpolygons;
        relaxationLevel = relaxationlevel;
    }

    //createVertex creates a vertex at the x and y coordinates given and then adds them to the Vertex list passed in
    public static Vertex createVertex(double x, double y, List<Vertex> vertices){
        Vertex vertex = Vertex.newBuilder().setX(x).setY(y).build();

        //checks if the created vertex is not in the private vertices list, if it is not then it adds it to the vertices list
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
        }
        return vertex;
    }

    //createSegment creates a segment joining the two vertices given and adds it to the Segment list passed in
//    //Note: it draws from the first vertex passed in to the second one
    public static Segment createSegment(int vertex1Idx, int vertex2Idx, List<Segment> segments){
        Segment segment = Segment.newBuilder().setV1Idx(vertex1Idx).setV2Idx(vertex2Idx).build();

        //checks if the created segment is not in the private vertices list, if it is not then it adds it to the segment list
        if(!segments.contains(segment)){
            segments.add(segment);
        }
        return segment;
    }

    public Mesh generate() {
        //right now it just randomly chooses which mesh to create

        if(meshType.equals("grid")){
            //makes a new GridMesh object that handles the creation of the polygons in a regular mesh
            GridMesh regularMesh = new GridMesh(width, height, square_size);

            //calls the createGridPolygons() method to perform the logic of creating a grid
            regularMesh.createGridPolygons();

            //set the verticesMeshList to the one created in the GridMesh object
            verticesMeshList = regularMesh.getVertices();

            //set the segmentsMeshList to the one created in the GridMesh object
            segmentsMeshList = regularMesh.getSegments();

            //set the polygonsMeshList to the one created in the GridMesh object
            polygonsMeshList = regularMesh.getPolygonsWithNeighbors();


        } else if (meshType.equals("irregular")) {

            //creates a new IrregularMesh object that handles the creation of the polygons in the irregular mesh
            IrregularMesh voronoiDiagram = new IrregularMesh(width, height, numPolygons, relaxationLevel);

            //calls the method that handles the generation of the voronoi diagram
            voronoiDiagram.createVoronoi();

            //gets the vertices, segments and polygons list created from the voronoi diagram creation
            verticesMeshList = voronoiDiagram.getVertices();
            segmentsMeshList = voronoiDiagram.getSegments();
            polygonsMeshList = voronoiDiagram.getPolygonsWithNeighbors();
        }

        //creates a new attribute class that sets the color and thickness of the vertices, segments and polygons
        Attributes attributes = new Attributes(verticesMeshList, segmentsMeshList, polygonsMeshList);

        //these calls handles allocating color and thickness properties to the vertices, segments and polygons
        attributes.setVertexProperties();
        attributes.setSegmentProperties();
        attributes.setPolygonProperties();

        //sets the vertices,segments and polygons mesh list to the ones created in the Attributes class since they contain colors and thickness
        verticesMeshList = attributes.getVerticeswithColors();
        segmentsMeshList = attributes.getSegmentsWithColors();
        polygonsMeshList = attributes.getPolygonsWithColors();

        //returns a mesh with different colored and thickness vertices, segments and polygons
        return Mesh.newBuilder().addAllVertices(verticesMeshList).addAllSegments(segmentsMeshList).addAllPolygons(polygonsMeshList).build();
    }
}
