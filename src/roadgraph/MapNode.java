package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {
    private GeographicPoint location;
    private List<MapEdge> edges;
    private double distance;

    public MapNode() {
        this.edges = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
    }

    public MapNode(double distance) {
        this();
        this.distance = distance;
    }

    public MapNode(GeographicPoint location) {
        this();
        this.location = location;
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

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(MapNode mapNode) {
        if (this.distance > mapNode.distance) {
            return 1;
        } else if (this.distance < mapNode.distance) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{" + " location='" + getDistance() + "'" + "}";
    }

}
