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

public class Gui extends Application {

  private static final int NODE_RADIUS = 2;

  public void start(Stage stage) {
    Graph<Location> graph = new ListGraph<>();
    OurOwnTestProgram.testMethod(graph);

    Pane root = new Pane();
    root.setPrefSize(500, 500);

    Line line = new Line(10, 10, 100, 10);
    line.setStrokeWidth(1);
    root.getChildren().add(line);

    for (Location location : graph.getNodes()) {
      Label label = new Label(location.getName(), new Circle(NODE_RADIUS));
      label.setLayoutX(location.getAbscissa());
      label.setLayoutY(location.getOrdinate());
      root.getChildren().add(label);
      System.out.println(location.getAbscissa() + " " + location.getOrdinate());
      System.out.println(label.getLayoutX() + " " + label.getLayoutY());
    }

    Scene scene = new Scene(root, 500, 500);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
