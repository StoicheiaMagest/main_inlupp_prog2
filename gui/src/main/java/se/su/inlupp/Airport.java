package se.su.inlupp;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Airport extends Pane implements Node<String>, Comparable<Airport> {

    private static final int NODE_RADIUS = 4;

    private static boolean removeMode = false;

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

        setOnMousePressed(new MousePressedHandler());
        setOnMouseDragged(new MouseDraggedHandler());
    }

    public static void setRemoveMode(boolean mode) {
        removeMode = mode;
    }

    private class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            if (removeMode) {
                Pane parent = (Pane) getParent();
                parent.getChildren().remove(Airport.this);

                event.consume();
                return;
            }

            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
        }
    }

    private class MouseDraggedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            if (removeMode) return;

            double newX = getLayoutX() + event.getX() - dragOffsetX;
            double newY = getLayoutY() + event.getY() - dragOffsetY;

            relocate(newX, newY);

            abscissa = newX;
            ordinate = newY;
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

    @Override
    public int compareTo(Airport other) {
        return this.name.compareTo(other.name);
    }
}