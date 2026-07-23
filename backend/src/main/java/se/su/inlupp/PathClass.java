//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import java.util.*;

public class PathClass<T> implements Path<T> {
    private final T startNode; 
    private final List<Edge<T>> edges; 
    
    protected PathClass(List<Edge<T>> edges, T startNode){
        this.edges = Collections.unmodifiableList(new ArrayList<>(edges));
        this.startNode = startNode;
    }

    @Override
    public T getStart(){
        return startNode;
    }

    @Override
    public T getEnd(){
        return edges.getLast().getDestination();
    }

    @Override
    public int getTotalWeight(){
        return edges.stream().mapToInt(e -> e.getWeight()).sum();
    }

    @Override
    public List<Edge<T>> getEdges(){
        return edges;
    }

    @Override
    public List<T> getNodes(){
        List<T> copyOfPathOfNodes = new ArrayList<>();
        copyOfPathOfNodes.add(getStart());

        for (Edge<T> edge: edges) {
            copyOfPathOfNodes.add(edge.getDestination());
        }

        return Collections.unmodifiableList(copyOfPathOfNodes);
    }

    @Override
    public Iterator<Edge<T>> iterator(){
        return getEdges().iterator();
    }
}
