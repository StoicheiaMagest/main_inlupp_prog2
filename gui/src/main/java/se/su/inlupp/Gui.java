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
import javafx.scene.control.Label;;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;


public class Gui extends Application {
  
  private static final int NODE_RADIUS = 2;
  Pane pane;
  
  Button addButton = new Button("Add Airport");
  Button saveButton = new Button("Save route");
  Button exitButton = new Button("Exit");
  VBox vBox = new VBox(10, addButton, saveButton, exitButton);
  
  public void start(Stage stage) {
  Graph<Location> graph = new ListGraph<>();
  
  Map<String, Location> locations = new HashMap<>();
  BorderPane root = new BorderPane();
  pane = new Pane();
  OurOwnTestProgram.testMethod(graph);
  
  root.setCenter(pane);
  root.setLeft(vBox);
  pane.setPrefSize(500, 500);
  for (Location node : graph.getNodes()) {
    locations.put(node.getName(), node);
    Circle circle = new Circle(node.getAbscissa(), node.getOrdinate(), NODE_RADIUS);
      Label label = new Label(node.getName());
      label.setLayoutX(node.getAbscissa());
      label.setLayoutY(node.getOrdinate());
      /*Label location = new Label(node.getName(), circle);
      location.setLayoutX(circle.getCenterX());
      location.setLayoutY(circle.getCenterY());*/
      for (Edge<Location> edge : graph.getEdgesFrom(node)) {
        Line road = new Line(label.getLayoutX(), label.getLayoutY(), 
                          edge.getDestination().getAbscissa(), edge.getDestination().getOrdinate());
        pane.getChildren().add(road);
      }
     
      
      pane.getChildren().add(circle);
      pane.getChildren().add(label);
    }
    
    BFSPathFinder<Location> bfs = new BFSPathFinder<>();

    System.out.print("PATH" + " " + bfs.findPath(graph, locations.get("Arlanda"), locations.get("Skavsta")));

    Scene scene = new Scene(root, 500, 500);
    stage.setScene(scene);
    stage.show();
  }

  private class ButtonHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent actionEvent){
      Object source = actionEvent.getSource();
      
      if (source == addButton) {
        pane.setOnMouseClicked(new ClickHandler());

        
      }
    }
  }

  class ClickHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent event) {
      double x = event.getX();
      double y = event.getY();

      
      Location location = new Location(STYLESHEET_CASPIAN, NODE_RADIUS, NODE_RADIUS)
    }
  }
  
  private class AddButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      try {
        
      }
    }
  } 

  public static void main(String[] args) {
    launch(args);
  }
}
