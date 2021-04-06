package roadgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import geography.GeographicPoint;

public class MapNode {
    protected GeographicPoint location;
    protected List<MapEdge> edges;
    // protected double distance;

    public MapNode() {
        this.edges = new ArrayList<>();
        // this.distance = Integer.MAX_VALUE;
    }

    public MapNode(double distance) {
        this();
        // this.distance = distance;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MapNode)) {
            return false;
        }
        MapNode mapNode = (MapNode) o;
        return location == mapNode.location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }


    @Override
    public String toString() {
        return "{" + " location='" + getLocation() + "'" + "}";
    }

}
