package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class Node {

    int value;
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }

    void addEdge(Edge edge) {
        this.edges.add(edge);
    }


}
