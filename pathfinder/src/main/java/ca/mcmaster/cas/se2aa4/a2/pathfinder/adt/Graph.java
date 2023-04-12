package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private final List<Node> nodesList;
    private final List<Edge> edgesList;

    public Graph(){
        this.edgesList=new ArrayList<>();
        this.nodesList=new ArrayList<>();
    }
    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public List<Node> getNodesList() {
        return nodesList;
    }

    public void nodeAdd(Node n){
        nodesList.add(n);
    }

    public void edgesAdd(){ // similar to nodesAdd, instead edges are added to list
        for(Node n: nodesList){
            edgesList.addAll(n.getEdges());
        }
    }



}
