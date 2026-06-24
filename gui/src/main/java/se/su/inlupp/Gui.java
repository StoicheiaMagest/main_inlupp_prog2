package se.su.inlupp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Gui extends Application {

  private Pane pane;
  private ToggleButton addAirportButton, saveRouteButton, exitButton, connectAirportsButton, removeAirportButton,
      useBFSAlgorithmButton, useDFSAlgorithmButton, showRouteButton, loadRouteButton, loadMapButton;
  private TextField airportNameField;

  private Graph<Airport> graph;

  private boolean removeMode;
  private boolean DFSMode;

  private ToggleGroup tools = new ToggleGroup();

  @Override
  public void start(Stage stage) {
    graph = new ListGraph<>();

    airportNameField = new TextField();
    airportNameField.setPromptText("Enter airport name here");
    airportNameField.setDisable(true);

    addAirportButton = new ToggleButton("Add Airport");

    connectAirportsButton = new ToggleButton("Connect Airports");
    removeAirportButton = new ToggleButton("Remove Airport");
    useBFSAlgorithmButton = new ToggleButton("Use BFS algorithm");
    useDFSAlgorithmButton = new ToggleButton("Use DFS algorithm");
    showRouteButton = new ToggleButton("Show route");
    loadRouteButton = new ToggleButton("Load route");
    saveRouteButton = new ToggleButton("Save route");
    loadMapButton = new ToggleButton("Load Map");
    exitButton = new ToggleButton("Exit");

    addAirportButton.setOnAction(new ButtonHandler());
    removeAirportButton.setOnAction(new ButtonHandler());
    connectAirportsButton.setOnAction(new ButtonHandler());
    useBFSAlgorithmButton.setOnAction(new ButtonHandler());
    useDFSAlgorithmButton.setOnAction(new ButtonHandler());
    showRouteButton.setOnAction(new ButtonHandler());
    loadRouteButton.setOnAction(new ButtonHandler());
    saveRouteButton.setOnAction(new ButtonHandler());
    loadMapButton.setOnAction(new ButtonHandler());
    exitButton.setOnAction(new ButtonHandler());

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

    addAirportButton.setToggleGroup(tools);
    removeAirportButton.setToggleGroup(tools);
    connectAirportsButton.setToggleGroup(tools);
    useBFSAlgorithmButton.setToggleGroup(tools);
    useDFSAlgorithmButton.setToggleGroup(tools);
    showRouteButton.setToggleGroup(tools);
    loadRouteButton.setToggleGroup(tools);
    saveRouteButton.setToggleGroup(tools);
    exitButton.setToggleGroup(tools);

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

      if (source == connectAirportsButton) {
        pane.setOnMouseClicked(new ConnectAirportsHandler());
      }

      if (source == useBFSAlgorithmButton) {
        DFSMode = false;
      }

      if (source == useDFSAlgorithmButton) {
        DFSMode = true;
      }

      if (source == showRouteButton) {
        pane.setOnMouseClicked(new ShowRouteHandler());
      }
      
      if (source == exitButton) {
        Platform.exit();
      }
    }

  }

  private class ShowRouteHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
      if (DFSMode) {
          
        } else {

        }
    }
    
  }

  private class ConnectAirportsHandler implements EventHandler<MouseEvent> {

    private Airport firstAirport;
    private Airport secondAirport;

    @Override
    public void handle(MouseEvent event) {

      javafx.scene.Node target = (javafx.scene.Node) event.getTarget();

      while (target != null && !(target instanceof Airport)) {
        target = target.getParent();
      }

      if (!(target instanceof Airport)) {
        return;
      }

      Airport airport = (Airport) target;

      if (firstAirport == null) {
        firstAirport = airport;
        return;
      }

      secondAirport = airport;

      if (firstAirport == secondAirport) {
        return;
      }

      graph.connect(
          firstAirport,
          secondAirport,
          firstAirport.getName()
              + "-"
              + secondAirport.getName(),
          1);

      EdgeGUI edge = new EdgeGUI(
          firstAirport,
          secondAirport,
          firstAirport.getName()
              + "-"
              + secondAirport.getName(),
          1);

      pane.getChildren().add(0, edge);

      firstAirport = null;

      pane.setOnMouseClicked(null);
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
        airportNameField.setDisable(true);
        pane.setOnMouseClicked(null);
        return;
      }

      Airport airport = new Airport(airportName, x, y, Gui.this);

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

  public void removeAirport(Airport airport) {

    // Ta bort alla EdgeGUI som är kopplade
    pane.getChildren().removeIf(node -> node instanceof EdgeGUI edge &&
        (edge.getFrom() == airport ||
            edge.getTo() == airport));

    // Ta bort airport grafiskt
    pane.getChildren().remove(airport);

    // Ta bort från grafen
    graph.remove(airport);
  }

  private Set<Airport> selectAirports(MouseEvent event) {
    Set<Airport> airports = new LinkedHashSet<Airport>() {

    };
    return List.of(firstAirport, secondAirport);
  }

  public static void main(String[] args) {
    launch(args);
  }
}