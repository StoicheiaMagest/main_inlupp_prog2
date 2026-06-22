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
import javafx.scene.control.Alert;

public class Gui extends Application {

  private Pane pane;
  private Button addAirportButton, saveRouteButton, exitButton, connectAirportsButton, removeAirportButton,
      useBFSAlgorithmButton, useDFSAlgorithmButton, showRouteButton, loadRouteButton, loadMapButton;
  private TextField airportNameField;

  private Graph<Airport> graph;

  private boolean removeMode;

  @Override
  public void start(Stage stage) {
    graph = new ListGraph<>();

    airportNameField = new TextField();
    airportNameField.setPromptText("Enter airport name here");
    airportNameField.setDisable(true);

    addAirportButton = new Button("Add Airport");
    connectAirportsButton = new Button("Connect Airports");
    removeAirportButton = new Button("Remove Airport");
    useBFSAlgorithmButton = new Button("Use BFS algorithm");
    useDFSAlgorithmButton = new Button("Use DFS algorithm");
    showRouteButton = new Button("Show route");
    loadRouteButton = new Button("Load route");
    saveRouteButton = new Button("Save route");
    loadMapButton = new Button("Load Map");
    exitButton = new Button("Exit");

    addAirportButton.setOnAction(new ButtonHandler());
    removeAirportButton.setOnAction(new ButtonHandler());

    VBox vBox = new VBox(10,
        airportNameField,
        addAirportButton,
        removeAirportButton,
        connectAirportsButton,
        useBFSAlgorithmButton,
        useDFSAlgorithmButton,
        showRouteButton,
        loadRouteButton,
        saveRouteButton,
        loadMapButton,
        exitButton);

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
        showErrorMessage("The textfield can not be empty");
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

  public void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An error occurred");
    alert.setContentText(message);

    alert.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}