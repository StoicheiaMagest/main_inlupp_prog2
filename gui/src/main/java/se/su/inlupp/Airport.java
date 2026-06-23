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

    private Gui gui;

    private double dragOffsetX;
    private double dragOffsetY;

    public Airport(String name, double x, double y, Gui gui) {
        this.gui = gui;
        this.name = name;

        Circle circle = new Circle(NODE_RADIUS);
        Label label = new Label(name);

        circle.setCenterX(NODE_RADIUS);
        circle.setCenterY(NODE_RADIUS);

        label.setLayoutX(10);
        label.setLayoutY(-5);

        getChildren().addAll(circle, label);

        setLayoutX(x);
        setLayoutY(y);

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
                if (removeMode) {
                    gui.removeAirport(Airport.this);

                    event.consume();
                    return;
                }
            }

            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
        }
    }

    private class MouseDraggedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            if (removeMode)
                return;

            setLayoutX(getLayoutX() + event.getX() - dragOffsetX);
            setLayoutY(getLayoutY() + event.getY() - dragOffsetY);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public double getX() {
        return getLayoutX();
    }

    public double getY() {
        return getLayoutY();
    }

    @Override
    public int compareTo(Airport other) {
        return name.compareTo(other.name);
    }

    public int getRadius() {
        return NODE_RADIUS;
    }
}