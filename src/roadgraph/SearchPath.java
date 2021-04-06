package roadgraph;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import geography.GeographicPoint;

public interface SearchPath {
    List<GeographicPoint>  getPath(HashMap<GeographicPoint, MapNode> vertices, GeographicPoint start, GeographicPoint goal,
                                   Consumer<GeographicPoint> nodeSearched);
    
}
