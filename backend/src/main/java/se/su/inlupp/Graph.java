//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import java.util.Collection;
import java.util.Set;
// git remote set-url  https://github.com/StoicheiaMagest/main_inlupp_prog2.git

public interface Graph<T> extends Iterable<T> {

  void add(T node);

  void remove(T node);

  boolean hasNode(T node);

  void connect(T node1, T node2, String name, int weight);

  void disconnect(T node1, T node2);

  void setConnectionWeight(T node1, T node2, int weight);

  Set<T> getNodes();

  Collection<Edge<T>> getEdgesFrom(T node);

  Edge<T> getEdgeBetween(T node1, T node2);
}

