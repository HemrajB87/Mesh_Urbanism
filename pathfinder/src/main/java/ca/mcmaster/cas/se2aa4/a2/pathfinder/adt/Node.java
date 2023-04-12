package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

import java.util.List;

public class Node {

    private final String id;
    private final Edge segment;
    private final List<Edge> edges;
    private String cName;

    public Node(String id, Edge seg, List<Edge> edges) {
        this.id = id;
        this.segment = seg;
        this.edges=edges;

    }

    public String getId() {
        return id;
    }

    public Edge getSegment(){
        return segment;
    }

   public List<Edge> getEdges(){
       return edges;
   }

   public void edgeAdd(Node end,int w){
        Edge edge = new Edge(this,end,w); // creates new edge
       edges.add(edge);
   }

    public String getCityNode(){ // provides a edge
        return  cName;
    }

    public void setCityNode(String x){ // sets an edge
        this.cName = x;
    }


}
