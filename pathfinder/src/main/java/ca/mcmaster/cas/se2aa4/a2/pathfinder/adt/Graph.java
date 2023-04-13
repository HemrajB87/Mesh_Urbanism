package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    List<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void connectNodes(Node start, Node end) {
        Edge edge = new Edge(start, end);
        start.addEdge(edge);
    }

}
