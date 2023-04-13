package ca.mcmaster.cas.se2aa4.a2.pathfinder.adt;

import java.util.*;

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

    public List<Node> shortestPath(Node start, Node end) {
        Map<Node, Node> previous = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode == end) {
                break;
            }

            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                for (Edge edge : currentNode.edges) {
                    Node neighbor = edge.end;
                    if (!visited.contains(neighbor)) {
                        previous.put(neighbor, currentNode);
                        queue.add(neighbor);
                    }
                }
            }
        }

        if (previous.get(end) == null) {
            return null; // No path found
        }

        List<Node> path = new ArrayList<>();
        Node currentNode = end;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = previous.get(currentNode);
        }
        Collections.reverse(path);
        return path;
    }

}
