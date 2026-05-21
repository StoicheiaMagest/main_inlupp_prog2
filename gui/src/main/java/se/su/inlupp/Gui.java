package se.su.inlupp;

import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Gui extends Application {

  private static final int NODE_RADIUS = 10;

  public void start(Stage stage) {
    Graph<String> graph = new ListGraph<String>();
    OurOwnTestProgram.testMethod(graph);
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    VBox root = new VBox(30, label);
    root.setAlignment(Pos.CENTER);
    // Set<String> nodeSet = graph.getNodes();
    NodeGUI node = new NodeGUI("Arlanda", 0, 0);
    int abscissa = node.getAbscissa();
    int ordinate = node.getOrdinate();
    Label arlanda = new Label(node.getName(), new Circle(abscissa, ordinate, NODE_RADIUS));
    root.getChildren().add(arlanda);
    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
