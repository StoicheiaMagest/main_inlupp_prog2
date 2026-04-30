package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {
  private List<T> vertices = new ArrayList<>(); // Store vertices
  private List<List<Edge<T>>> neighbours = new ArrayList<>(); // Adjacency Edge lists

  // construct an empty graph without parameters
  public ListGraph() {
  }

  private class Node {
    T value;
    String name;

    Node(T value){
      this.value  = value;
    }
  }

  @Override // Add a vertex to the graph
  public void add(T vertex) {
    if (!vertices.contains(vertex)) {
      vertices.add(vertex);
      neighbours.add(new ArrayList<Edge<T>>());
    }
  }

  @Override // Remove node and edges connected to that node
  public void remove(T node) {
      if (!vertices.remove(node)) {
        Collection<Edge<T>> edgesFromNode = getEdgesFrom(node);
        ArrayList<T> destinationsFromEdge = new ArrayList<T>();
        for (Edge<T> edge : edgesFromNode) {
          destinationsFromEdge.add(edge.getDestination());

          disconnect(node, );

        }
        throw new NoSuchElementException();
      }
  }

  @Override
  public boolean hasNode(T node) {
    throw new UnsupportedOperationException("Unimplemented method 'hasNode'");
  }

  @Override
  public void connect(T node1, T node2, String name, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'connect'");
  }

  @Override
  public void disconnect(T node1, T node2) {
    throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
  }

  @Override
  public void setConnectionWeight(T node1, T node2, int weight) {
    throw new UnsupportedOperationException("Unimplemented method 'setConnectionWeight'");
  }

  @Override
  public Set<T> getNodes() {
    List<Integer> result = new ArrayList<>();
    for  (Edge<T> e: neighbours.get(index)) {
      result.add(e.v);
    }
    return result;
    throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
  }

  @Override
  public Collection<Edge<T>> getEdgesFrom(T node) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgesFrom'");
  }

  @Override
  public Edge<T> getEdgeBetween(T node1, T node2) {
    throw new UnsupportedOperationException("Unimplemented method 'getEdgeBetween'");
  }

  @Override
  public Iterator<T> iterator() {
    throw new UnsupportedOperationException("Unimplemented method 'iterator'");
  }

}

