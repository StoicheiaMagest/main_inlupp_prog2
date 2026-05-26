package se.su.inlupp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.HashMap;
import java.util.Map;

public class Gui extends Application {

  private static final int NODE_RADIUS = 2;

  public void start(Stage stage) {
    Graph<Location> graph = new ListGraph<>();
    OurOwnTestProgram.testMethod(graph);

    Map<String, Location> locations = new HashMap<>();

    Pane root = new Pane();
    root.setPrefSize(500, 500);

    for (Location node : graph.getNodes()) {
      locations.put(node.getName(), node);
      Circle circle = new Circle(node.getAbscissa(), node.getOrdinate(), NODE_RADIUS);
      Label location = new Label(node.getName(), circle);
      location.setLayoutX(circle.getCenterX());
      location.setLayoutY(circle.getCenterY());
      for (Edge<Location> edge : graph.getEdgesFrom(node)) {
        Line road = new Line(location.getLayoutX(), location.getLayoutY(), 
                          edge.getDestination().getAbscissa(), edge.getDestination().getOrdinate());
        root.getChildren().add(road);
      }
     
      
      root.getChildren().add(location);
    }
    
    BFSPathFinder<Location> bfs = new BFSPathFinder<>();

    System.out.print("PATH" + " " + bfs.findPath(graph, locations.get("Arlanda"), locations.get("Skavsta")));

    Scene scene = new Scene(root, 500, 500);
    stage.setScene(scene);
    stage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}
