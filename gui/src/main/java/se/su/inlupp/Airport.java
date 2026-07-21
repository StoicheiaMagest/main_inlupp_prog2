//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Airport extends Pane implements Node<String>, Comparable<Airport> {

    private static final int NODE_RADIUS = 4;

    private static boolean removeMode = false;

    private String name;

    private Gui gui;

    private Circle circle;
    private Label label;

    private double dragOffsetX;
    private double dragOffsetY;

    protected Airport(String name, double x, double y, Gui gui) {
        this.gui = gui;
        this.name = name;

        circle = new Circle(NODE_RADIUS);
        label = new Label(name);

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

    protected static void setRemoveMode(boolean mode) {
        removeMode = mode;
    }

    private class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            if (removeMode) {
                gui.removeAirport(Airport.this);
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

            if (removeMode)
                return;

            setLayoutX(getLayoutX() + event.getX() - dragOffsetX);
            setLayoutY(getLayoutY() + event.getY() - dragOffsetY);
            gui.setChanged(true);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    protected double getX() {
        return getLayoutX();
    }

    protected double getY() {
        return getLayoutY();
    }

    @Override
    public int compareTo(Airport other) {
        return name.compareTo(other.name);
    }

    protected int getRadius() {
        return NODE_RADIUS;
    }

    protected void setColor(Color color) {
        circle.setFill(color);
    }
}