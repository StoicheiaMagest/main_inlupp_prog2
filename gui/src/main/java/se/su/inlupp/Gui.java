package se.su.inlupp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Gui extends Application {

  private Pane pane;
  private ToggleButton addAirportButton, saveFlightsButton, exitButton, connectAirportsButton, removeAirportButton,
      removeColorButton,
      useBFSAlgorithmButton, useDFSAlgorithmButton, showRouteButton, loadFlightsButton, loadMapButton;
  private TextField textField;
  private Label weightLabel;

  private Graph<Airport> graph;
  private List<Flight> flights;
  private DFSPathFinder<Airport> dfsPathFinder = new DFSPathFinder<>();
  private BFSPathFinder<Airport> bfsPathFinder = new BFSPathFinder<>();

  private boolean removeMode;
  private boolean DFSMode;

  private Airport firstAirport;
  private Airport secondAirport;

  private Color color;

  private ToggleGroup tools = new ToggleGroup();

  private Image image = new Image("https://m.media-amazon.com/images/I/71Z115aCqqL._AC_SL1500_.jpg");

  private BorderPane root;

  private ImageView imageView = new ImageView(image);

  private FileChooser fileChooser = new FileChooser();

  private Stage stage;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    graph = new ListGraph<>();
    flights = new ArrayList<>();

    textField = new TextField();
    textField.setPromptText("Press button and enter text");
    textField.setDisable(true);

    weightLabel = new Label();

    addAirportButton = new ToggleButton("Add Airport");
    connectAirportsButton = new ToggleButton("Connect Airports");
    removeAirportButton = new ToggleButton("Remove Airport");
    removeColorButton = new ToggleButton("Remove Color");
    useBFSAlgorithmButton = new ToggleButton("Use BFS algorithm [RED]");
    useDFSAlgorithmButton = new ToggleButton("Use DFS algorithm [BLUE]");
    showRouteButton = new ToggleButton("Show route");
    loadFlightsButton = new ToggleButton("Load flights");
    saveFlightsButton = new ToggleButton("Save flights");
    loadMapButton = new ToggleButton("Load Map");
    exitButton = new ToggleButton("Exit");

    addAirportButton.setOnAction(new ButtonHandler());
    removeAirportButton.setOnAction(new ButtonHandler());
    removeColorButton.setOnAction(new ButtonHandler());
    connectAirportsButton.setOnAction(new ButtonHandler());
    useBFSAlgorithmButton.setOnAction(new ButtonHandler());
    useDFSAlgorithmButton.setOnAction(new ButtonHandler());
    showRouteButton.setOnAction(new ButtonHandler());
    loadFlightsButton.setOnAction(new ButtonHandler());
    saveFlightsButton.setOnAction(new ButtonHandler());
    loadMapButton.setOnAction(new ButtonHandler());
    exitButton.setOnAction(new ButtonHandler());

    VBox vBox = new VBox(10,
        textField,
        addAirportButton,
        removeAirportButton,
        removeColorButton,
        connectAirportsButton,
        useBFSAlgorithmButton,
        useDFSAlgorithmButton,
        showRouteButton,
        loadFlightsButton,
        saveFlightsButton,
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
    loadFlightsButton.setToggleGroup(tools);
    saveFlightsButton.setToggleGroup(tools);
    loadMapButton.setToggleGroup(tools);
    exitButton.setToggleGroup(tools);

    pane = new Pane();
    pane.setPrefSize(500, 500);
    pane.getChildren().add(imageView);

    root = new BorderPane();
    root.setCenter(pane);
    root.setLeft(vBox);

    Scene scene = new Scene(root, 800, 800);
    stage.setScene(scene);
    resizeImage(image);

    stage.show();
  }

  private class ButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

      Object source = event.getSource();

      removeMode = false;
      Airport.setRemoveMode(false);

      if (source == addAirportButton) {
        textField.setPromptText("Enter airport name here");
        textField.setDisable(false);
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
        textField.setPromptText("URL for background image");
        textField.setDisable(false);
        textField.setOnKeyPressed(new LoadMapHandler());
      }

      if (source == saveFlightsButton) {
        new SaveHandler().handle(event);
      }

      if (source == loadFlightsButton) {
        for (Airport airport : graph.getNodes()){
          graph.remove(airport);
        }
        pane.getChildren().clear();
        flights.clear();
        new LoadHandler().handle(event);
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

        for (Flight flight : flights) {

          if (flight.getName().equals(flightInPath.getName())) {
            flight.setColor(color);
          }
        }
      }

      String algorithm = DFSMode ? "DFS" : "BFS";

      weightLabel
          .setText("Total weight for path using " + algorithm + ": " + path.getTotalWeight());

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

      Flight flight = new Flight(
          firstAirport,
          secondAirport,
          firstAirport.getName()
              + "-"
              + secondAirport.getName(),
          1);

      flight.setMouseTransparent(true);

      flights.add(flight);
      pane.getChildren().add(flight);

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

      String airportName = textField.getText();

      if (airportName == null || airportName.strip().isEmpty()) {
        showErrorMessage("The textfield can not be empty");
        textField.setDisable(true);
        pane.setOnMouseClicked(null);
        return;
      }

      Airport airport = new Airport(airportName, x, y, Gui.this);

      graph.add(airport);
      pane.getChildren().add(airport);

      textField.clear();
      textField.setDisable(true);

      pane.setOnMouseClicked(null);
    }
  }

  private class LoadMapHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        Image newImage = new Image(textField.getText());

        resizeImage(newImage);

        textField.clear();
        textField.setDisable(true);
      }
    }
  }

  private class SaveHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      File file = fileChooser.showSaveDialog(stage);
      if (file != null) {
        save(file.getAbsolutePath());
      }
    }
  }

  private class LoadHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      File file = fileChooser.showOpenDialog(stage);
      if (file != null) {
        load(file.getAbsolutePath());
      }
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

    // Ta bort alla Flight som är kopplade
    pane.getChildren().removeIf(node -> node instanceof Flight flight &&
        (flight.getFrom() == airport ||
            flight.getTo() == airport));

    // Tar bort alla flights som är kopplade
    flights.removeIf(node -> node instanceof Flight flight &&
        (flight.getFrom() == airport ||
            flight.getTo() == airport));

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
    for (Flight flight : flights) {
      flight.setColor(Color.BLACK);
    }
  }

  private void save(String fileName) {

    try (FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file)) {

      List<AirportData> airportsData = new ArrayList<>();

      for (Airport airport : graph.getNodes()) {
        airportsData.add(
            new AirportData(
                airport.getName(),
                airport.getX(),
                airport.getY()));
      }

      List<FlightData> flightsData = new ArrayList<>();

      for (Flight flight : flights) {
        flightsData.add(
            new FlightData(
                flight.getName(),
                flight.getFrom().getName(),
                flight.getTo().getName(),
                flight.getWeight()));
      }

      for (AirportData airportData : airportsData) {
        out.writeObject(airportData);
      }
      for (FlightData flightData : flightsData) {
        out.writeObject(flightData);
      }
       
      out.writeObject(image.getUrl());

      out.close();
      file.close();

    } catch (FileNotFoundException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Can't open file!");
      alert.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();

      Alert alert = new Alert(Alert.AlertType.ERROR,
          "IO-error " + e.getMessage());

      alert.showAndWait();
    }
  }

  private void resizeImage(Image image) {

    Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    double maxWidth = screen.getWidth() * 0.9;
    double maxHeight = screen.getHeight() * 0.9;

    imageView.setImage(image);

    if (image.getWidth() > maxWidth || image.getHeight() > maxHeight) {
      imageView.setFitWidth(maxWidth);
      imageView.setFitHeight(maxHeight);
    } else {
      imageView.setFitWidth(image.getWidth());
      imageView.setFitHeight(image.getHeight());
    }

    imageView.setPreserveRatio(true);

    stage.sizeToScene();
  }

  public void load(String fileName) {
    pane.getChildren().add(imageView);
    try {
      FileInputStream file = new FileInputStream(fileName);
      ObjectInputStream in = new ObjectInputStream(file);

      
      while (true) {
        try {
          Object dataObject = in.readObject();
          
          if ((dataObject instanceof AirportData airportData)) {
            Airport airport = new Airport(airportData.getName(), airportData.getX(), airportData.getY(), Gui.this);
            graph.add(airport);
            pane.getChildren().add(airport);
          } else if (dataObject instanceof FlightData flightData) {
            Airport airportFrom = null;
            Airport airportTo = null;
            for (Airport airport : graph.getNodes()) {
              if (flightData.getFrom().equals(airport.getName())) {
                airportFrom = airport;
              } else if (flightData.getTo().equals(airport.getName())) {
                airportTo = airport;
              }
            }
            graph.connect(airportFrom, airportTo, flightData.getName(), flightData.getWeight());
            Flight flight = new Flight(airportFrom, airportTo, flightData.getName(), flightData.getWeight());
            flight.setMouseTransparent(true);
            flights.add(flight);
            pane.getChildren().add(flight);
          } else if (dataObject instanceof String url) {
            Image newImage = new Image(url);
            resizeImage(newImage); 
          }
        } catch (EOFException e) {
          in.close();
          file.close();
          break;
        }
      }

    } catch (FileNotFoundException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Can't open file " + fileName + "!");
      alert.showAndWait();
    } catch (IOException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "IO-error " + e.getMessage());
      alert.showAndWait();
    } catch (ClassNotFoundException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Can't find class " + e.getMessage());
      alert.showAndWait();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}