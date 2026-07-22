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

public class Flight extends Pane implements Edge<Airport> {
    private Airport from;
    private Airport to;
    private String name;
    private int weight;
    private Line line;

    protected Flight(Airport from, Airport to, String name, int weight) {
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
        
        line.setOnMousePressed(new MousePressedHandler());
    }

    private class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Gui.setFlightLabelText("  Flight name:" + "\n" 
            + "\n"
            + "  " + from.getName() + "\n" 
            + "  |" + "\n" 
            + "  " + to.getName() + "\n" 
            + "\n"
            + "  Weight:" + "\n" 
            + "  " + weight );
        }
    }

    protected Line getLine() {
        return line;
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
        line.setStroke(color);
    }
}
