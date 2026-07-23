//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Flight extends Line implements Edge<Airport> {
    private Airport from;
    private Airport to;
    private String name;
    private int weight;

    protected Flight(Airport from, Airport to, String name, int weight) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.weight = weight;

        startXProperty().bind(
                from.layoutXProperty().add(from.getRadius()));

        startYProperty().bind(
                from.layoutYProperty().add(from.getRadius()));

        endXProperty().bind(
                to.layoutXProperty().add(to.getRadius()));

        endYProperty().bind(
                to.layoutYProperty().add(to.getRadius()));
        
        setOnMousePressed(event -> {
            Gui.setFlightLabelText("Flight name:\n  " + name + "\n\nWeight:\n" + weight);
        });
    }

    protected Airport getFrom() {
        return from;
    }

    @Override
    public int getWeight() {
        return weight;
    };

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    };

    @Override
    public Airport getDestination() {
        return to;
    };

    @Override
    public String getName() {
        return name;
    };

    protected void setColor(Color color){
        setStroke(color);
    }
}
