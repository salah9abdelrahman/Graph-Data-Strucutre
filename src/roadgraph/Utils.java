package roadgraph;

import geography.GeographicPoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    public static  <T extends MapNode> List<GeographicPoint> constructPath(T startNode, T goalNode, HashMap<T, T> parentMap) {
        LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
        T curr = goalNode;
        while (startNode != curr) {
            path.addFirst(curr.getLocation());
            curr = parentMap.get(curr);
        }
        path.addFirst(startNode.getLocation());
        return path;
    }
}
