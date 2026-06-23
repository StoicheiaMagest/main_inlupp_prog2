package se.su.inlupp;

import javafx.scene.shape.Line;

public class EdgeGUI {
    private Airport from;
    private Airport to;
    private String name;
    private int weight;

    private Line line;

    public EdgeGUI(Airport from, Airport to, String name, int weight) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.weight = weight;

        this.line = new Line();

        line.startXProperty().bind(from.centerXProperty());
        line.startYProperty().bind(from.centerYProperty());
        line.endXProperty().bind(to.centerXProperty());
        line.endYProperty().bind(to.centerYProperty());
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
}
