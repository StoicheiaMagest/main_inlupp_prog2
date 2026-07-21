//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;
import java.util.*;

public class DFSPathFinder<T> implements PathFinder<T> {
  
  protected DFSPathFinder() {

  }

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {

    Map<T, T> connections = new HashMap<>();
    connect(from, null, connections, graph);

    if (!connections.containsKey(to)) {
      return null;
    }

    List<Edge<T>> path = new LinkedList<>();

    T current = to;
    while (current != null && !current.equals(from)) {
      T next = connections.get(current);
      Edge<T> edge = graph.getEdgeBetween(next, current);
      path.addFirst(edge);
      current = next;
    }

    return new PathClass<T>(path, from);  
  }

  private void connect(T to, T from, Map<T,T> connections, Graph<T> graph) {
    connections.put(to, from);
    for (Edge<T> edge : graph.getEdgesFrom(to)) {
      T destination = edge.getDestination();
      if (!connections.containsKey(destination)) {
        connect(destination, to, connections, graph);
      }
    }
  }
} 

