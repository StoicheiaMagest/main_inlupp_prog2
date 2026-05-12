package se.su.inlupp;

import java.util.*;

public class PathClass<T> implements Path<T>, Iterable<Edge<T>>{
    private List<Edge<T>> pathOfEdges = new ArrayList<>(); 
    private List<T> pathOfNodes = new ArrayList<>();
    
    public T getStart(){
        return pathOfNodes.getFirst();
    }

    public T getEnd(){
        return pathOfNodes.getLast();
    }

    public int getTotalWeight(){
        return pathOfEdges.stream().mapToInt(e -> e.getWeight()).sum();
    }


    public List<Edge<T>> getEdges(){
        List<Edge<T>> copyOfPathOfEdges = new ArrayList<>(pathOfEdges);

        return Collections.unmodifiableList(copyOfPathOfEdges);
    }

    public List<T> getNodes(){
        List<T> copyOfPathOfNodes = new ArrayList<>(pathOfNodes);

        return Collections.unmodifiableList(copyOfPathOfNodes);
    }

    /*@Override
    public Iterator<Edge<T>> iterator(){

    }*/
}
