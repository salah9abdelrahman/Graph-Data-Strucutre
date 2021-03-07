package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
    private GeographicPoint start;
    private GeographicPoint end;
    private String streetName;
    private String roadType;
    private double length;

    public MapEdge(GeographicPoint start, GeographicPoint end, String streetName, String roadType, double length) {
        this.start = start;
        this.end = end;
        this.streetName = streetName;
        this.roadType = roadType;
        this.length = length;
    }


    public GeographicPoint getStart() {
        return this.start;
    }

    public void setStart(GeographicPoint start) {
        this.start = start;
    }

    public GeographicPoint getEnd() {
        return this.end;
    }

    public void setEnd(GeographicPoint end) {
        this.end = end;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getRoadType() {
        return this.roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "{" +
            " start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            // ", streetName='" + getStreetName() + "'" +
            // ", roadType='" + getRoadType() + "'" +
            // ", length='" + getLength() + "'" +
            "}";
    }


}
