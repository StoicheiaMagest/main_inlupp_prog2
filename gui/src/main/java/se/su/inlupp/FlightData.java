package se.su.inlupp;

import java.io.Serializable;

public class FlightData implements Serializable {

    private final String name;
    private final String from;
    private final String to;
    private final int weight;

    public FlightData(String name, String from, String to, int weight) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}
