package roadgraph;

import java.util.Objects;

public class WeightedMapNode extends MapNode implements Comparable<WeightedMapNode> {
    private double distance;
    private double predictedDistance;

    public WeightedMapNode(MapNode mapNode) {
        super();
        edges = mapNode.edges;
        location = mapNode.location;
    }

    public WeightedMapNode(MapNode mapNode, double distance) {
        this(mapNode);
        this.distance = distance;
        this.predictedDistance = 0;
    }

    public WeightedMapNode(MapNode mapNode, double distance, double predictedDistance) {
        this(mapNode, distance);
        this.predictedDistance = predictedDistance;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public double getPredictedDistance() {
        return this.predictedDistance;
    }

    public void setPredictedDistance(double predictedDistance) {
        this.predictedDistance = predictedDistance;
    }


    @Override
    public int compareTo(WeightedMapNode mapNode) {
        if ((this.distance + this.predictedDistance) > (mapNode.distance + mapNode.predictedDistance)) {
            return 1;
        } else if ((this.distance + this.predictedDistance) < (mapNode.distance
                + mapNode.predictedDistance)) {
            return -1;
        }
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WeightedMapNode)) {
            return false;
        }
        WeightedMapNode dijkstraMapNode = (WeightedMapNode) o;
        return this.location == dijkstraMapNode.location;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
    

}
