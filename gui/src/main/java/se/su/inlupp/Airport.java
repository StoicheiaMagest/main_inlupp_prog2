package se.su.inlupp;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Airport extends Pane implements Node<String>, Comparable<Airport> {
    private static final int NODE_RADIUS = 4;
    private String name;
    private double abscissa;
    private double ordinate;

    private double dragOffsetX;
    private double dragOffsetY;

    public Airport(String name, double abscissa, double ordinate) {
        this.name = name;
        this.abscissa = abscissa;
        this.ordinate = ordinate;

        Circle circle = new Circle(NODE_RADIUS);

        Label label = new Label(name);

        circle.setCenterX(NODE_RADIUS);
        circle.setCenterY(NODE_RADIUS);

        label.setLayoutX(10);
        label.setLayoutY(-5);

        getChildren().addAll(circle, label);

        relocate(abscissa, ordinate);

        setOnMousePressed(new StartDragHandler());
        setOnMouseDragged(new DragHandler());

    }

    class StartDragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
              dragOffsetX = event.getX();
              dragOffsetY = event.getY();
        }
    }

    class DragHandler implements EventHandler<MouseEvent> {

        public void handle(MouseEvent event) {
            double newX = getLayoutX() + event.getX() - dragOffsetX;
            double newY = getLayoutY() + event.getY() - dragOffsetY;

            relocate(newX, newY);

            abscissa = newX;
            ordinate = newY;
        }
    }

    /*class DeleteHandler implements EventHandler<MouseEvent> {

        public void handle(MouseEvent event) {
            new RemoveAirportOnMapHandler();
        }
    }*/

    @Override
    public String getName() {
        return name;
    }

    public double getAbscissa() {
        return abscissa;
    }

    public double getOrdinate() {
        return ordinate;
    }

    public void setAbscissa(int abscissa) {
        this.abscissa = abscissa;
    }

    public void setOrdinate(int ordinate) {
        this.ordinate = ordinate;
    }

    @Override
    public int compareTo(Airport other) {
        return this.name.compareTo(other.name);
    }

}
