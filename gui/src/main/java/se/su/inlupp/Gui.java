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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Gui extends Application {

  private Pane pane;
  private ToggleButton addAirportButton, saveRouteButton, exitButton, connectAirportsButton, removeAirportButton,
      removeColorButton,
      useBFSAlgorithmButton, useDFSAlgorithmButton, showRouteButton, loadRouteButton, loadMapButton;
  private TextField airportNameField;
  private Label weightLabel;

  private Graph<Airport> graph;
  private List<EdgeGUI> flights;
  private DFSPathFinder<Airport> dfsPathFinder = new DFSPathFinder<>();
  private BFSPathFinder<Airport> bfsPathFinder = new BFSPathFinder<>();

  private boolean removeMode;
  private boolean DFSMode;

  private Airport firstAirport;
  private Airport secondAirport;

  private Color color;

  private ToggleGroup tools = new ToggleGroup();

  @Override
  public void start(Stage stage) {
    graph = new ListGraph<>();
    flights = new ArrayList<>();

    airportNameField = new TextField();
    airportNameField.setPromptText("Enter airport name here");
    airportNameField.setDisable(true);

    weightLabel = new Label();

    addAirportButton = new ToggleButton("Add Airport");
    connectAirportsButton = new ToggleButton("Connect Airports");
    removeAirportButton = new ToggleButton("Remove Airport");
    removeColorButton = new ToggleButton("Remove Color");
    useBFSAlgorithmButton = new ToggleButton("Use BFS algorithm [RED]");
    useDFSAlgorithmButton = new ToggleButton("Use DFS algorithm [BLUE]");
    showRouteButton = new ToggleButton("Show route");
    loadRouteButton = new ToggleButton("Load route");
    saveRouteButton = new ToggleButton("Save route");
    loadMapButton = new ToggleButton("Load Map");
    exitButton = new ToggleButton("Exit");

    addAirportButton.setOnAction(new ButtonHandler());
    removeAirportButton.setOnAction(new ButtonHandler());
    removeColorButton.setOnAction(new ButtonHandler());
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
        removeColorButton,
        connectAirportsButton,
        useBFSAlgorithmButton,
        useDFSAlgorithmButton,
        showRouteButton,
        loadRouteButton,
        saveRouteButton,
        loadMapButton,
        weightLabel,
        exitButton);

    addAirportButton.setToggleGroup(tools);
    removeAirportButton.setToggleGroup(tools);
    removeColorButton.setToggleGroup(tools);
    connectAirportsButton.setToggleGroup(tools);
    useBFSAlgorithmButton.setToggleGroup(tools);
    useDFSAlgorithmButton.setToggleGroup(tools);
    showRouteButton.setToggleGroup(tools);
    loadRouteButton.setToggleGroup(tools);
    saveRouteButton.setToggleGroup(tools);
    loadMapButton.setToggleGroup(tools);
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

      removeMode = false;
      Airport.setRemoveMode(false);

      if (source == addAirportButton) {
        airportNameField.setDisable(false);
        pane.setOnMouseClicked(new PutAirportOnMapHandler());
      }

      if (source == removeAirportButton) {
        removeMode = true;
        Airport.setRemoveMode(true);
      }

      if (source == removeColorButton) {
        removeColor();
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

      if (source == loadMapButton) {

      }

      if (source == saveRouteButton) {
        
      }

      if (source == loadMapButton) {

      }

      if (source == exitButton) {
        Platform.exit();
      }
    }

  }

  private class ShowRouteHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

      if (!selectAirports(event)) {
        return;
      }

      Path<Airport> path;

      if (DFSMode) {
        path = dfsPathFinder.findPath(graph, firstAirport, secondAirport);
        color = Color.BLUE;

      } else {
        path = bfsPathFinder.findPath(graph, firstAirport, secondAirport);
        color = Color.RED;
      }
      if (path == null) {
        firstAirport = null;
        secondAirport = null;
        return;
      }

      List<Airport> airports = path.getNodes();
      List<Edge<Airport>> flightsInPath = path.getEdges();

      for (Airport airport : airports) {
        airport.setColor(color);
      }

      for (Edge<Airport> flightInPath : flightsInPath) {

        for (EdgeGUI flight : flights) {

          if (flight.getName().equals(flightInPath.getName())) {
            flight.setColor(color);
          }
          /*
           * if (flight.getFrom() == () && flight.getTo() ==
           * flightInPath.getDestination()) {
           * flight.setColor(color);
           * }
           */
        }
      }

      String algorithm = DFSMode ? "DFS" : "BFS";

      weightLabel
          .setText("Total weight for path using " + algorithm + ": " + path.getTotalWeight() + "\n" + path.getEdges());

      firstAirport = null;
      secondAirport = null;
    }
  }

  private class ConnectAirportsHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

      if (!selectAirports(event)) {
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

      flights.add(edge);
      pane.getChildren().add(0, edge);

      firstAirport = null;
      secondAirport = null;

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

    // Tar bort alla flights som är kopplade
    flights.removeIf(node -> node instanceof EdgeGUI edge &&
        (edge.getFrom() == airport ||
            edge.getTo() == airport));

    // Ta bort airport grafiskt
    pane.getChildren().remove(airport);

    // Ta bort från grafen
    graph.remove(airport);
    // pane.setOnMouseClicked(null);
  }

  private boolean selectAirports(MouseEvent event) {

    javafx.scene.Node target = (javafx.scene.Node) event.getTarget();

    while (target != null && !(target instanceof Airport)) {
      target = target.getParent();
    }

    if (!(target instanceof Airport)) {
      return false;
    }

    Airport airport = (Airport) target;

    if (firstAirport == null) {
      firstAirport = airport;
      return false;
    }

    secondAirport = airport;

    if (firstAirport == secondAirport) {
      secondAirport = null;
      return false;
    }

    return true;
  }

  private void removeColor() {
    for (Airport airport : graph) {
      airport.setColor(Color.BLACK);
    }
    for (EdgeGUI flight : flights) {
      flight.setColor(Color.BLACK);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}