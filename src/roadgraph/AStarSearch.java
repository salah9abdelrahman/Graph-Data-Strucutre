package roadgraph;

import geography.GeographicPoint;

import java.util.*;
import java.util.function.Consumer;

public class AStarSearch implements SearchPath {
    @Override
    public List<GeographicPoint> getPath(HashMap<GeographicPoint, MapNode> vertices, GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        WeightedMapNode startNode = new WeightedMapNode(vertices.get(start));
        WeightedMapNode goalNode = new WeightedMapNode(vertices.get(goal));
        int visitedNum = 0;
        HashMap<GeographicPoint, WeightedMapNode> dijkstraVertices = new HashMap<>();
        PriorityQueue<WeightedMapNode> pq = new PriorityQueue<>();
        HashSet<WeightedMapNode> visited = new HashSet<>();
        HashMap<WeightedMapNode, WeightedMapNode> parentMap = new HashMap<>();
        boolean found = false;
        // init
        for (MapNode mapNode : vertices.values()) {
            dijkstraVertices.put(mapNode.location, new WeightedMapNode(mapNode, Double.MAX_VALUE, Double.MAX_VALUE));
        }
        startNode.setDistance(0);
        startNode.setPredictedDistance(0);
        pq.add(startNode);
        while (!pq.isEmpty()) {
            WeightedMapNode curr = pq.remove();
            visitedNum++;
            if (!visited.contains(curr)) {
                visited.add(curr);
                if (curr.getLocation().equals(goalNode.getLocation())) {
                    found = true;
                    break;
                }
                List<MapEdge> neighbors = curr.getEdges();
                ListIterator<MapEdge> it = neighbors.listIterator(neighbors.size());
                while (it.hasPrevious()) {
                    MapEdge currentEdge = it.previous();
                    WeightedMapNode next = dijkstraVertices.get(currentEdge.getEnd());
                    if ((!visited.contains(next))) {

                        if (next.getDistance() > (currentEdge.getLength() + curr.getDistance())) {
                            parentMap.put(next, curr);
                            next.setDistance(currentEdge.getLength() + curr.getDistance());
                            next.setPredictedDistance(currentEdge.getEnd().distance(goalNode.location));
                            // Hook for visualization. See writeup.
                            nodeSearched.accept(next.getLocation());
                            pq.add(next);

                        }

                    }
                }

            }
        }

        if (!found) {
            return new ArrayList<GeographicPoint>();

        }
        // System.out.println("visited nodes for a star");
        // System.out.println("**********");
        // for (WeightedMapNode weightedMapNode : visited) {
        // System.out.println(weightedMapNode.location);
        // }
        // System.out.println("**********");
        System.out.println("  A star: " + visitedNum);

        return Utils.constructPath(startNode, goalNode, parentMap);
    }
}
