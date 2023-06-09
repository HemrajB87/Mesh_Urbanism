import ca.mcmaster.cas.se2aa4.a2.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.adt.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.path.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    // Declare nodes and graph to be used in the tests
    private Node nodeA;
    private Node nodeB;
    private Node nodeC;
    private Node nodeD;
    private Node nodeE;
    private Graph graph;

    @BeforeEach
    void setUp() {
        // Initialize nodes and graph
        nodeA = new Node(1);
        nodeB = new Node(2);
        nodeC = new Node(3);
        nodeD = new Node(4);
        nodeE = new Node(5);

        graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);

        graph.connectNodes(nodeA, nodeB);
        graph.connectNodes(nodeB, nodeC);
        graph.connectNodes(nodeA, nodeD);
        graph.connectNodes(nodeD, nodeE);
        graph.connectNodes(nodeE, nodeC);
    }


}