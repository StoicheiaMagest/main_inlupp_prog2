package se.su.inlupp;

public class EdgeClass<T> implements Edge<T> {
    private T node;
    private String name;
    private int weight;

    EdgeClass(T node, String name, int weight) {
        this.node = node;
        this.name = name;
        setWeight(weight);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must not be negative");
        }
        this.weight = weight;
    }

    public T getDestination() {
        return node;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "till " + node + " med " + name + " tar " + weight;
    }
}
