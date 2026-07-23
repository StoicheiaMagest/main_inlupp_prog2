//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

public class EdgeClass<T> implements Edge<T> {
    private final T node;
    private final String name;
    private int weight;

    protected EdgeClass(T node, String name, int weight) {
        this.node = node;
        this.name = name;
        setWeight(weight);
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must not be negative");
        }
        this.weight = weight;
    }

    @Override
    public T getDestination() {
        return node;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "till " + node + " med " + name + " tar " + weight;
    }
}
