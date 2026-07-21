//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

public interface Edge<T> {

  int getWeight();

  void setWeight(int weight);

  T getDestination();

  String getName();
}
