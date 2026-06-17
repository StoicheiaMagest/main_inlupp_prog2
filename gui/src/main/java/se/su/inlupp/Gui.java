package se.su.inlupp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class Gui extends Application {

  private Pane pane;
  private Button addAirportButton, saveButton, exitButton, connectAirportsButton, removeAirportButton;
  private TextField airportNameField;
  private VBox vBox;
  private Graph<Airport> graph;
  private boolean removeMode = false;

  public void start(Stage stage) {
    graph = new ListGraph<>();
    airportNameField = new TextField();
    airportNameField.setPromptText("Enter airport name here");
    airportNameField.setDisable(true);
    addAirportButton = new Button("Add Airport");
    addAirportButton.setOnAction(new ButtonHandler());
    saveButton = new Button("Save route");
    exitButton = new Button("Exit");
    connectAirportsButton = new Button("Connect Airports");
    removeAirportButton = new Button("Remove Airport");
    vBox = new VBox(10, airportNameField, addAirportButton, removeAirportButton, connectAirportsButton, saveButton,
        exitButton);

    Map<String, Airport> airports = new HashMap<>();
    BorderPane root = new BorderPane();
    pane = new Pane();
    OurOwnTestProgram.testMethod(graph);

    root.setCenter(pane);
    root.setLeft(vBox);
    pane.setPrefSize(500, 500);
    for (Airport airport : graph.getNodes()) {

      airport.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

        if (removeMode) {

          Airport clickedAirport = (Airport) e.getSource();

          pane.getChildren().remove(clickedAirport);
          graph.remove(clickedAirport);

          removeMode = false;

          e.consume();
        }
      });

      pane.getChildren().add(airport);
    }

    BFSPathFinder<Airport> bfs = new BFSPathFinder<>();

    //System.out.print("PATH" + " " + bfs.findPath(graph, airports.get("Arlanda"), airports.get("Skavsta")));

    Scene scene = new Scene(root, 500, 500);
    stage.setScene(scene);
    stage.show();
  }

  private class ButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      Object source = actionEvent.getSource();

      if (source == addAirportButton) {
        airportNameField.setDisable(false);
        pane.setOnMouseClicked(new PutAirportOnMapHandler());

      }
      if (source == removeAirportButton) {
        removeMode = true;
      }
      if (source == connectAirportsButton) {

      }
    }
  }

  /*
   * private class RemoveAirportHandler implements EventHandler<MouseEvent> {
   * 
   * @Override
   * public void handle(MouseEvent event) {
   * 
   * Airport airport = (Airport) event.getSource();
   * 
   * pane.getChildren().remove(airport);
   * graph.remove(airport);
   * 
   * event.consume();
   * }
   * }
   */

  private class PutAirportOnMapHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
      double x = event.getX();
      double y = event.getY();

      String airportName = airportNameField.getText();

      if (airportName.strip().isEmpty()) {
        return;
      }

      Airport airport = new Airport(airportName, x, y);

      airport.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

        if (removeMode) {

          Airport clickedAirport = (Airport) e.getSource();

          pane.getChildren().remove(clickedAirport);
          graph.remove(clickedAirport);

          removeMode = false;

          e.consume();
        }
      });

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
