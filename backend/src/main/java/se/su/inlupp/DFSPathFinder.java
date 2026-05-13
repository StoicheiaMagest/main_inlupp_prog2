package se.su.inlupp;
import java.util.*;

public class DFSPathFinder<T> implements PathFinder<T> {
  
  public DFSPathFinder() {

  }

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {
    Map<T, T> connections = new HashMap<>();
    connect(from, null, connections);
    List<Edge<T>> path = getEdges(); //new LinkedList<>();

    T current = to;
    while (current != null && !current.equals(from)) {
      T next = connections.get(current);
      Edge edge = getEdgeBetween(next, current);
      path.addFirst(edge);
      current = next;
    }

    return path;
    throw new UnsupportedOperationException("Unimplemented method 'findPath'");
  }

  private void connect(T to, T from, Map<T,T> connections) {
    connections.put(to, from);
    for (Edge edge : T.get(to)) {
      T destination = (T) edge.getDestination();
      if (!connections.containsKey(destination)) {
        connect(destination, to, connections);
      }
    }
  }
} 

