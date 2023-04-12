package ca.mcmaster.cas.se2aa4.a2.pathfinder.path;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.adt.*;

import java.util.*;

public class Path{

    // this algorithm is Dijkstra's algorithm, parts of code is taken from 2C03 class (disclaimer)
    public List<Node> pathShortest(Node s, Node e){

        Map<Node, Integer> dist = new HashMap<>();
        Map<Node, Node> priorDist = new HashMap<>();

        PriorityQueue<Node> freshNodes = new PriorityQueue<>(Comparator.comparingDouble(dist::get));


        Set<Node> oldNodes = new HashSet<>();


        dist.put(s,0);
        freshNodes.add(s);


        while (!freshNodes.isEmpty()){

            Node cNode = freshNodes.poll();
            oldNodes.add(cNode);

            if (cNode==e){
                break;
            }

            for(Edge edge:cNode.getEdges()){
                Node neighbor = edge.getStart();
                if(!oldNodes.contains(neighbor)){
                    int nDist = (int) (dist.get(cNode)+edge.getWeight());

                    if(nDist< dist.getOrDefault(neighbor, Integer.MAX_VALUE)){
                        dist.put(neighbor,nDist);

                        priorDist.put(neighbor,cNode);

                        freshNodes.add(neighbor);
                    }
                }
            }

        }
        List<Node> path = new ArrayList<>();
        Node cNode = e;
        while (cNode!=null){
            path.add(0,cNode);
            cNode = priorDist.get(cNode);
        }
        return path;
    }
}
