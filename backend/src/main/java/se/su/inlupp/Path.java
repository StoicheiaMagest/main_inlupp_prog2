//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import java.util.List;

public interface Path<T> extends Iterable<Edge<T>> {

  T getStart();

  T getEnd();

  int getTotalWeight();

  List<Edge<T>> getEdges();

  List<T> getNodes();
}

