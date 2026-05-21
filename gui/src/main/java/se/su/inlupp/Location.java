package se.su.inlupp;

public class Location implements Node<String>, Comparable<Location> {
    private String name;
    private int abscissa;
    private int ordinate;

    public Location(String name, int abscissa, int ordinate) {
        this.name = name;
        this.abscissa = abscissa;
        this.ordinate = ordinate;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAbscissa() {
        return abscissa;
    }

    public int getOrdinate() {
        return ordinate;
    }

    public void setAbscissa(int abscissa) {
        this.abscissa = abscissa;
    }

    public void setOrdinate(int ordinate) {
        this.ordinate = ordinate;
    }

    @Override
    public int compareTo(Location other) {
        return this.name.compareTo(other.name);
    }

}
