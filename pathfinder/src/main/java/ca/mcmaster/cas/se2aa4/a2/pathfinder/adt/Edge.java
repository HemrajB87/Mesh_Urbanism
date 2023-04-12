package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

public class Edge {

    private final Node start;
    private final Node end;
    private final double weight;
    private String cName;

    public Edge(Node start, Node end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public double getWeight() { // adding weight
        return weight;
    }

    public String getCityEdge(){ // provides a edge
        return  cName;
    }

    public void setCityEdge(String x){ // sets an edge
        this.cName = x;
    }

}