package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
    private GeographicPoint location;
    private List<MapEdge> edges;

    public MapNode() {
        this.edges = new ArrayList<>();
    }

    public MapNode(GeographicPoint location) {
        this.location = location;
        this.edges = new ArrayList<>();
    }

    public GeographicPoint getLocation() {
        return this.location;
    }

    public void setLocation(GeographicPoint location) {
        this.location = location;
    }

    public List<MapEdge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<MapEdge> edges) {
        this.edges = edges;
    }

    public void addEdge(GeographicPoint start, GeographicPoint end, String streetName, String roadType, double length) {
        this.edges.add(new MapEdge(start, end, streetName, roadType, length));
    }

    @Override
    public String toString() {
        return "{" + " location='" + getLocation() + "'" + "}";
    }

}
