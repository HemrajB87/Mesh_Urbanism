package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

public class Edge {

    Node start;
    public Node end;

    Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

}