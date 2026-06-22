package se.su.inlupp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

    private Pane pane;
    private Button addAirportButton, saveButton, exitButton, connectAirportsButton, removeAirportButton;
    private TextField airportNameField;

    private Graph<Airport> graph;

    private boolean removeMode = false;

    @Override
    public void start(Stage stage) {

        graph = new ListGraph<>();

        airportNameField = new TextField();
        airportNameField.setPromptText("Enter airport name here");
        airportNameField.setDisable(true);

        addAirportButton = new Button("Add Airport");
        saveButton = new Button("Save route");
        exitButton = new Button("Exit");
        connectAirportsButton = new Button("Connect Airports");
        removeAirportButton = new Button("Remove Airport");

        addAirportButton.setOnAction(new ButtonHandler());
        removeAirportButton.setOnAction(new ButtonHandler());

        VBox vBox = new VBox(10,
                airportNameField,
                addAirportButton,
                removeAirportButton,
                connectAirportsButton,
                saveButton,
                exitButton
        );

        pane = new Pane();
        pane.setPrefSize(500, 500);

        BorderPane root = new BorderPane();
        root.setCenter(pane);
        root.setLeft(vBox);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Object source = event.getSource();

            if (source == addAirportButton) {
                airportNameField.setDisable(false);
                pane.setOnMouseClicked(new PutAirportOnMapHandler());
                removeMode = false;
                Airport.setRemoveMode(false);
            }

            if (source == removeAirportButton) {
                removeMode = true;
                Airport.setRemoveMode(true);
            }
        }
    }

    private class PutAirportOnMapHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            double x = event.getX();
            double y = event.getY();

            String airportName = airportNameField.getText();

            if (airportName == null || airportName.strip().isEmpty()) {
                return;
            }

            Airport airport = new Airport(airportName, x, y);

            graph.add(airport);
            pane.getChildren().add(airport);

            airportNameField.clear();
            airportNameField.setDisable(true);

            pane.setOnMouseClicked(null);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}