package se.su.inlupp;

import java.util.*;

public class PathClass<T> implements Path<T>{
    private List<Edge<T>> edges = new ArrayList<>(); 
    private List<T> nodes = new ArrayList<>();
    
    public PathClass(List<Edge<T>> edges, List<T> nodes){
        this.edges = edges;
        this.nodes = nodes;
    }

    public T getStart(){
        return nodes.getFirst();
    }

    public T getEnd(){
        return nodes.getLast();
    }

    public int getTotalWeight(){
        return edges.stream().mapToInt(e -> e.getWeight()).sum();
    }


    public List<Edge<T>> getEdges(){
        List<Edge<T>> copyOfPathOfEdges = new ArrayList<>(edges);

        return Collections.unmodifiableList(copyOfPathOfEdges);
    }

    public List<T> getNodes(){
        List<T> copyOfPathOfNodes = new ArrayList<>(nodes);

        return Collections.unmodifiableList(copyOfPathOfNodes);
    }

    @Override
    public Iterator<Edge<T>> iterator(){
        return getEdges().iterator();
    }
}
