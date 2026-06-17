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
  
  private static final int NODE_RADIUS = 2;
  private Pane pane;
  private Button addAirportButton, saveButton, exitButton, connectAirportsButton, removeAirportButton;
  private TextField airportNameField;
  private VBox vBox;
  private Graph<Airport> graph;

  
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
    vBox = new VBox(10, airportNameField, addAirportButton, removeAirportButton,connectAirportsButton, saveButton, exitButton);

    
    Map<String, Airport> airports = new HashMap<>();
    BorderPane root = new BorderPane();
    pane = new Pane();
    OurOwnTestProgram.testMethod(graph);
    
    root.setCenter(pane);
    root.setLeft(vBox);
    pane.setPrefSize(500, 500);
    for (Airport node : graph.getNodes()) {
      airports.put(node.getName(), node);
      Circle circle = new Circle(node.getAbscissa(), node.getOrdinate(), NODE_RADIUS);
        Label label = new Label(node.getName());
        label.setLayoutX(node.getAbscissa());
        label.setLayoutY(node.getOrdinate());
        /*Label location = new Label(node.getName(), circle);
        location.setLayoutX(circle.getCenterX());
        location.setLayoutY(circle.getCenterY());*/
        for (Edge<Airport> edge : graph.getEdgesFrom(node)) {
          Line road = new Line(label.getLayoutX(), label.getLayoutY(), 
                            edge.getDestination().getAbscissa(), edge.getDestination().getOrdinate());
          pane.getChildren().add(road);
        }
        
        pane.getChildren().add(circle);
        pane.getChildren().add(label);
      }
      
      BFSPathFinder<Airport> bfs = new BFSPathFinder<>();

      System.out.print("PATH" + " " + bfs.findPath(graph, airports.get("Arlanda"), airports.get("Skavsta")));

      Scene scene = new Scene(root, 500, 500);
      stage.setScene(scene);
      stage.show();
  }

  private class ButtonHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent actionEvent){
      Object source = actionEvent.getSource();
      
      if (source == addAirportButton) {
        airportNameField.setDisable(false);
        pane.setOnMouseClicked(new PutAirportOnMapHandler());
        
      }
      if (source == removeAirportButton) {
        pane.setOnMouseClicked(new RemoveAirportOnMapHandler());
      }
      if (source == connectAirportsButton) {

      }
    }
  }

  class RemoveAirportOnMapHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
      double x = event.getX();
      double y = event.getY();

      Airport airportToRemove = findAirportAt(x, y);

      if (airportToRemove != null) {
          pane.getChildren().remove(airportToRemove);
          graph.remove(airportToRemove);

          // remove attached labels/lines too
      }
      
      /*public void removeAirport(Airport airport(x, y)) {
        Graph.remove(airport(x, y));
      }*/

      pane.setOnMouseClicked(null);
    }
  }
  //pane.getOnMouseClicked().handle(new RemoveAirportOnMapHandler().remove(airport(x, y)));


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
      
      graph.add(airport);
      pane.getChildren().add(airport);

      airportNameField.clear();
      airportNameField.setDisable(true);
      pane.setOnMouseClicked(null);
    }
  }

  private Airport findAirportAt(double x, double y){
    
    for (Airport airport: graph.getNodes()){
      double dx = airport.getAbscissa() - x;
      double dy = airport.getOrdinate() - y;

      if (Math.sqrt(dx * dx + dy * dy) <= NODE_RADIUS) {
          return airport;
      }
    }

    return null;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
