package ca.mcmaster.cas.se2aa4.a2.pathfinder.path;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.adt.*;

import java.util.*;

public class Path{

    // this algorithm is Dijkstra's algorithm, parts of code is taken from 2C03 class (disclaimer)
    public List<Node> pathfinder(Node start, Node end, List<Node> nodesList) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> unvisited = new HashSet<>();

        for (Node node : nodesList) {
            distances.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }

        distances.put(start, 0);

        while (!unvisited.isEmpty()) {
            Node currentNode = findMinDistanceNode(unvisited, distances);

            if (currentNode == null) {
                break;
            }

            unvisited.remove(currentNode);

            if (currentNode == end) {
                break;
            }

            for (Edge edge : currentNode.edges) {
                Node neighbor = edge.end;
                if (unvisited.contains(neighbor)) {
                    int tentativeDistance = distances.get(currentNode) + 1;
                    if (tentativeDistance < distances.get(neighbor)) {
                        distances.put(neighbor, tentativeDistance);
                        previous.put(neighbor, currentNode);
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

    private Node findMinDistanceNode(Set<Node> unvisited, Map<Node, Integer> distances) {
        Node minNode = null;
        int minValue = Integer.MAX_VALUE;

        for (Node node : unvisited) {
            int distance = distances.get(node);
            if (distance < minValue) {
                minValue = distance;
                minNode = node;
            }
        }

        return minNode;
    }
}

