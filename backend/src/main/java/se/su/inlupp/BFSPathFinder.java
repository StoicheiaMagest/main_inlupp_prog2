//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;
import java.util.*;

public class BFSPathFinder<T> implements PathFinder<T> {

  protected BFSPathFinder() {
  }

  @Override
  public Path<T> findPath(Graph<T> graph, T from, T to) {
    Map<T, T> searchOrder = new HashMap<>();
    searchOrder.put(from, null);

    LinkedList<T> queue = new LinkedList<>();

    queue.add(from);

    while (!queue.isEmpty()) {
      T current = queue.poll();
      for (Edge<T> edge : graph.getEdgesFrom(current)) {
        T next = edge.getDestination();
        if (!searchOrder.containsKey(next)) {
          searchOrder.put(next, current);
          queue.add(next);
        } 
      }      
    }

    LinkedList<Edge<T>> path = new LinkedList<>();

    T current = to;
    while (current != null && !current.equals(from)) {
      T next = searchOrder.get(current);
      if (next == null) {
        return null;
      }
      Edge<T> edge = graph.getEdgeBetween(next, current);
      path.addFirst(edge);
      current = next;
    }

    return new PathClass<T>(path, from);    
  }
}

