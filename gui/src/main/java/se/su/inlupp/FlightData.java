//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import java.io.Serializable;

public class FlightData implements Serializable {

    private final String name;
    private final String from;
    private final String to;
    private final int weight;

    protected FlightData(String name, String from, String to, int weight) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    protected String getName() {
        return name;
    }

    protected String getFrom() {
        return from;
    }

    protected String getDestination() {
        return to;
    }

    protected int getWeight() {
        return weight;
    }
}
