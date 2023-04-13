package ca.mcmaster.cas.se2aa4.a2.island.road;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.graphConversion.Conversion;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.adt.*;

import java.util.ArrayList;
import java.util.List;

public class Road {

    // star network , incomplete

    public List<Node> convertVerticesToNodes(List<Structs.Vertex> verticesList) {
        List<Node> nodesList = new ArrayList<>();

        for (Structs.Vertex v : verticesList) {
            Node node = new Node(v.hashCode());
            nodesList.add(node);
        }

        return nodesList;
    }


}
