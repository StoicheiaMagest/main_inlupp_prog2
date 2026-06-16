package se.su.inlupp;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Location extends Pane implements Node<String>, Comparable<Location> {
    private static final int NODE_RADIUS = 4;
    private String name;
    private double abscissa;
    private double ordinate;

    public Location(String name, double abscissa, double ordinate) {
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
              abscissa = event.getX();
              ordinate = event.getY();
        }
    }

    class DragHandler implements EventHandler<MouseEvent> {

        public void handle(MouseEvent event) {
            double newX = getLayoutX() + event.getX() - abscissa;
            double newY = getLayoutY() + event.getY() - ordinate;
            relocate(newX, newY);
        }
    }

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
    public int compareTo(Location other) {
        return this.name.compareTo(other.name);
    }

}
