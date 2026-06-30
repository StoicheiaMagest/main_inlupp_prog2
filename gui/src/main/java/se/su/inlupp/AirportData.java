package se.su.inlupp;

import java.io.Serializable;

public class AirportData implements Serializable {

    private final String name;
    private final double x;
    private final double y;

    public AirportData(String name, double x, double y) {
      this.name = name;
      this.x = x;
      this.y = y;
    }

    public String getName() {
      return name;
    }

    public double getX() {
      return x;
    }

    public double getY() {
      return y;
    }

  }
