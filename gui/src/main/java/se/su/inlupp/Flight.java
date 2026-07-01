package se.su.inlupp;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Flight extends Pane implements Edge<Airport> {
    private Airport from;
    private Airport to;
    private String name;
    private int weight;

    private Line line;

    public Flight(Airport from, Airport to, String name, int weight) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.weight = weight;

        this.line = new Line();

        getChildren().add(line);

        line.startXProperty().bind(
                from.layoutXProperty().add(from.getRadius()));

        line.startYProperty().bind(
                from.layoutYProperty().add(from.getRadius()));

        line.endXProperty().bind(
                to.layoutXProperty().add(to.getRadius()));

        line.endYProperty().bind(
                to.layoutYProperty().add(to.getRadius()));
    }

    public Line getLine() {
        return line;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    };

    public void setWeight(int weight) {
        this.weight = weight;
    };

    public Airport getDestination() {
        return to;
    };

    public String getName() {
        return name;
    };

    public void setColor(Color color){
        line.setStroke(color);
    }
}
