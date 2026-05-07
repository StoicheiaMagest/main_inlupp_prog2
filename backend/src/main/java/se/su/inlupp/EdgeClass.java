package se.su.inlupp;

public class EdgeClass<T> implements Edge<T> {    
    private T node1, node2;
    private String name;
    private int weight;

    EdgeClass(T node1, T node2, String name, int weight){
        this.node1 = node1;
        this.node2 = node2;
        this.name = name;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public T getDestination() {
        return node2;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return "From node: " + node1 + ", To node: " + node2  + ", Name: " + name + ", Weight: " + weight;
    }
}
