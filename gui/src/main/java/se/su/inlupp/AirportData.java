//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import java.io.Serializable;

public class AirportData implements Serializable {

    private final String name;
    private final double x;
    private final double y;

    protected AirportData(String name, double x, double y) {
      this.name = name;
      this.x = x;
      this.y = y;
    }

    protected String getName() {
      return name;
    }

    protected double getX() {
      return x;
    }

    protected double getY() {
      return y;
    }
  }
