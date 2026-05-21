package se.su.inlupp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Gui extends Application {

  private static final int NODE_RADIUS = 2;

  public void start(Stage stage) {
    Graph<Location> graph = new ListGraph<>();
    OurOwnTestProgram.testMethod(graph);

    GridPane root = new GridPane();
    root.setPrefSize(1000, 1000);
    for (Location location : graph.getNodes()) {
      Label label = new Label(location.getName(), new Circle(location.getAbscissa(), location.getOrdinate(),
          NODE_RADIUS));
      root.add(label, location.getAbscissa(), location.getOrdinate());
    }

    Scene scene = new Scene(root, 1000, 1000);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
