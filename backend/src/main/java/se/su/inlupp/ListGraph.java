package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {
  private List<T> nodes = new ArrayList<>(); // Store vertices
  private Map<T, List<Edge<T>>> neighbours = new HashMap<>(); // Adjacency Edge lists
  private List<List<Integer>> edges = new ArrayList<>();

  // construct an empty graph without parameters
  public ListGraph() {
  }

  @Override // Add a vertex to the graph
  public void add(T node) {
    if (!hasNode(node)) {
      nodes.add(node);
      List<Edge<T>> edgesFromNode = new ArrayList<Edge<T>>();      
      neighbours.put(node, edgesFromNode);
      //edgesFromNode.add(); Kanske ska tas bort
    }
  }

  @Override // Remove node and edges connected to that node
  public void remove(T node) {
    if (hasNode(node)) {

      //Iterator<Edge<T>> iterator = neighbours.get(node).iterator();
      for (Edge<T> e : getEdgesFrom(node)) {
        T secondNodeToRemoveEdgeFrom = e.getDestination();
        neighbours.get(node).remove(e);
        neighbours.get(secondNodeToRemoveEdgeFrom).remove(getEdgeBetween(secondNodeToRemoveEdgeFrom, node));
      }/* 
      while(iterator.hasNext()){
        neighbours.get(node).remove(iterator.next());
      }*/

      nodes.remove(node);
      neighbours.remove(node);


        /*Collection<Edge<T>> edgesFromNode = getEdgesFrom(node);
        ArrayList<T> destinationsFromEdge = new ArrayList<T>();
        for (Edge<T> edge : edgesFromNode) {
          destinationsFromEdge.add(edge.getDestination());

          //disconnect(node, );

        }*/
      }
      else{
        throw new NoSuchElementException();
      }
  }

  @Override
  public boolean hasNode(T node) {
    return nodes.contains(node);
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) {
    if(!(hasNode(node1) && hasNode(node2))){
      throw new NoSuchElementException("One or both nodes missing");
    }
    if(weight < 0){
      throw new IllegalArgumentException("Weight cannot be negative");
    }
    if(getEdgeBetween(node1, node2) != null){
      throw new IllegalStateException("Edge " + name + " already exists");
    }
    EdgeClass<T> edge1To2 = new EdgeClass<T>(node1, node2, name, weight);
    EdgeClass<T> edge2To1 = new EdgeClass<T>(node2, node1, name, weight);

    neighbours.get(node1).add(edge1To2);
    neighbours.get(node2).add(edge2To1);
  }

  @Override
  public void disconnect(T node1, T node2) {
    if(!(hasNode(node1) && hasNode(node2))){
      throw new NoSuchElementException("One or both nodes missing");
    }
    if(getEdgeBetween(node1, node2) == null){
      throw new IllegalStateException("Edge doesn't exist");
    }

    neighbours.get(node1).remove(getEdgeBetween(node1, node2));
    neighbours.get(node2).remove(getEdgeBetween(node2, node1));
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
    Set<T> nodeSet = new TreeSet<T>();

    for (T n : nodes){
      nodeSet.add(n);
    }

    return Collections.unmodifiableSet(nodeSet);
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) {
    if(!hasNode(node)){
      throw new NoSuchElementException("Node doesn't exist");
    }

    Collection<Edge<T>> copyOfEdges = new ArrayList<Edge<T>>(neighbours.get(node));

    return Collections.unmodifiableCollection(copyOfEdges);
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) {
    //neighbours.get(neighbours.indexOf(node2))
    if (!(hasNode(node1) && hasNode(node2))){
      throw new NoSuchElementException("Node1 or node2 doesn't exist");
    }

    /*if(!(getEdgesFrom(node1).contains(neighbours.get(node2).equals(node1)))) {
      return null;
    } else {*/
      for (Edge<T> e : getEdgesFrom(node1)) {
        if (e.getDestination() == node2) {
          return e;
        }
      }
      return null;
    //}
  }

  @Override
  public Iterator<T> iterator(){
    return new MyIterator();
  }

  private class MyIterator implements Iterator<T> {
    private int index = 0;

    @Override
    public T next()
    {
      if (!hasNext()){
        throw new NoSuchElementException();
      }
      return nodes.get(index++);
    }

    @Override
    public boolean hasNext()
    {
      return index < nodes.size();
    }  
  };

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();

    for(T n: nodes){
      sb.append("Node: " + n + "\n");
      for(Edge<T> e: getEdgesFrom(n)){
        sb.append("Edge - " + e + "\n");
      }
    }

    return sb.toString();
  }
}

