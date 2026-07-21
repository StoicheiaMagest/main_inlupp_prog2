//PROG2 VT2026, Inlämningsuppgift, del 1
//Grupp 198
//Oliver Hellström Eriksson olhe2589
//Stina Nilsén Börlin stni8969
//Stoicheia Magest riro7563 

package se.su.inlupp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gui extends Application {

  private Pane pane;
  private BorderPane root;
  private VBox vBox;

  private Image image = new Image("https://m.media-amazon.com/images/I/71Z115aCqqL._AC_SL1500_.jpg");
  private ImageView imageView = new ImageView(image);

  private ToggleGroup tools = new ToggleGroup();
  private ToggleButton addAirportButton, connectAirportsButton, removeAirportButton,
      removeColorButton, useBFSAlgorithmButton, useDFSAlgorithmButton,
      showRouteButton, loadMapButton;
  private Label weightLabel;

  private MenuBar menuBar;
  private Menu archiveMenu;
  private MenuItem loadFlightsItem, saveFlightsItem, exitItem;

  private Graph<Airport> graph;
  private List<Flight> flights;
  private DFSPathFinder<Airport> dfsPathFinder = new DFSPathFinder<>();
  private BFSPathFinder<Airport> bfsPathFinder = new BFSPathFinder<>();

  private Airport firstAirport, secondAirport;

  private Color color;

  private boolean removeMode, DFSMode, changed;

  private FileChooser fileChooser = new FileChooser();

  private Stage stage;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    graph = new ListGraph<>();
    flights = new ArrayList<>();

    weightLabel = new Label();

    createButtons();
    activateButtons();

    menuBar = new MenuBar();

    createVBox();

    createMenu();

    setToggleGroupForButtons();

    pane = new Pane();
    pane.setPrefSize(500, 500);
    pane.getChildren().add(imageView);

    root = new BorderPane();
    root.setCenter(pane);
    root.setLeft(vBox);

    Scene scene = new Scene(root, 800, 800);
    stage.setScene(scene);
    resizeImage(image);
    stage.setOnCloseRequest(new ExitHandler());
    stage.show();
  }

  private class ButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

      Object source = event.getSource();

      removeMode = false;
      Airport.setRemoveMode(false);

      if (source == addAirportButton) {
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
        new LoadMapHandler().handle(event);
      }

      if (source == saveFlightsItem) {
        new SaveHandler().handle(event);
      }

      if (source == loadFlightsItem) {
        for (Airport airport : graph) {
          graph.remove(airport);
        }
        pane.getChildren().clear();
        flights.clear();
        new LoadHandler().handle(event);
      }

      if (source == exitItem) {
        new ExitItemHandler().handle(event);
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
        showErrorMessage("The airports in the selected route are not connected");
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

      String nameAndWeight = new ConnectAirportsDialog()
          .showAndWait()
          .orElse(null);

      if (nameAndWeight == null) {
        return;
      }

      String[] array = nameAndWeight.split(" ");
      String name = array[0];
      int weight = Integer.parseInt(array[1]);

      graph.connect(
          firstAirport,
          secondAirport,
          name,
          weight);

      Flight flight = new Flight(
          firstAirport,
          secondAirport,
          name,
          weight);

      flight.setMouseTransparent(true);

      flights.add(flight);
      pane.getChildren().add(flight);
      setChanged(true);

      firstAirport = null;
      secondAirport = null;

      pane.setOnMouseClicked(null);
    }
  }

  private class PutAirportOnMapHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      String airportName = new AirportDialog()
          .showAndWait()
          .orElse(null);

      if (airportName == null || airportName.isBlank()) {
        return;
      }

      double x = event.getX();
      double y = event.getY();

      if (airportName == null || airportName.strip().isEmpty()) {
        showErrorMessage("The textfield can not be empty");
        pane.setOnMouseClicked(null);
        return;
      }

      Airport airport = new Airport(airportName, x, y, Gui.this);

      graph.add(airport);
      pane.getChildren().add(airport);
      setChanged(true);

      pane.setOnMouseClicked(null);
    }
  }

  private class LoadMapHandler implements EventHandler<ActionEvent>{

    @Override
    public void handle(ActionEvent arg0) {
      
      String url = new MapDialog()
            .showAndWait()
            .orElse(null);

      if (url == null || url.isBlank()) {
        return;
      }

      Image newImage = new Image(url);
      resizeImage(newImage);
      setChanged(true);
    }
  }

  private class SaveHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      File file = fileChooser.showSaveDialog(stage);
      if (file != null) {
        save(file.getAbsolutePath());
        setChanged(false);
      }
    }
  }

  private class LoadHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      File file = fileChooser.showOpenDialog(stage);
      if (file != null) {
        load(file.getAbsolutePath());
        setChanged(false);
      }
    }
  }

  private class ExitItemHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
  } 

  private class ExitHandler implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent event) {
      if (changed) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Unsaved changes, do you want to exit anyway?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.CANCEL)) {
          event.consume();
        }
      }
    }  
  }

  private class AirportDialog extends Dialog<String> {
    private TextField nameField = new TextField();

    private AirportDialog() {
      setTitle("New airport");
      setHeaderText(null);

      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(5);

      grid.addRow(0, new Label("Airport"), nameField);

      getDialogPane().setContent(grid);
      getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      setResultConverter(buttonType -> {
        if (buttonType == ButtonType.OK) {

            String name = nameField.getText();

            if (name.isBlank()) {
                showErrorMessage("Name-field cannot be empty");
                return null;
            }

            return name;
        }
        return null;
      });
      
      nameField.setPromptText("Enter airport name here");
      nameField.setDisable(false);
    }
  }

  private class ConnectAirportsDialog extends Dialog<String> {
    private TextField nameField = new TextField();
    private TextField weightField = new TextField();

    private ConnectAirportsDialog() {
      setTitle("New flight");
      setHeaderText(null);
 
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(5);
      
      grid.addRow(0, new Label("Flight"), nameField);
      grid.addRow(1, new Label("Weight"), weightField);
      
      getDialogPane().setContent(grid);
      getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
      
      setResultConverter(buttonType -> {
        if (buttonType == ButtonType.OK) {

            String name = nameField.getText();
            String weightText = weightField.getText();
            int weight = 1;

            try{
              weight = Integer.parseInt(weightText);
            } catch (NumberFormatException e){
              weightText = "1";
            }
            
            if (weight < 0){
              showErrorMessage("Weight cannot be negative");
              return null;
            }
            
            if (name.isBlank()) {
              name = firstAirport.getName() + "-" + secondAirport.getName();
            }
            
            return name + " " + weightText;
          }
          return null;
        });
        
      nameField.setPromptText(firstAirport.getName() + "-" + secondAirport.getName());
      weightField.setPromptText("1");
    }
  }

  private class MapDialog extends Dialog<String> {
    private TextField urlField = new TextField();

    private MapDialog() {
      setTitle("New Map");
      setHeaderText(null);

      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(5);

      grid.addRow(0, new Label("URL"), urlField);

      getDialogPane().setContent(grid);
      getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      setResultConverter(buttonType -> {
        if (buttonType == ButtonType.OK) {

            String url = urlField.getText();

            if (url.isBlank()) {
                showErrorMessage("URL-field cannot be empty");
                return null;
            }

            return url;
        }

        return null;
      });
      
      urlField.setPromptText("Enter URL here");
      urlField.setDisable(false);
    }
  }

  private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An error occurred");
    alert.setContentText(message);

    alert.showAndWait();
  }

  protected void removeAirport(Airport airport) {

    // Ta bort alla Flight som är kopplade
    pane.getChildren().removeIf(node -> node instanceof Flight flight &&
        (flight.getFrom() == airport ||
            flight.getDestination() == airport));

    // Tar bort alla flights som är kopplade
    flights.removeIf(node -> node instanceof Flight flight &&
        (flight.getFrom() == airport ||
            flight.getDestination() == airport));

    // Ta bort airport grafiskt
    pane.getChildren().remove(airport);

    // Ta bort från grafen
    graph.remove(airport);
    setChanged(true);
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

      for (Airport airport : graph) {
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
                flight.getDestination().getName(),
                flight.getWeight()));
      }

      for (AirportData airportData : airportsData) {
        out.writeObject(airportData);
      }
      for (FlightData flightData : flightsData) {
        out.writeObject(flightData);
      }

      out.writeObject(imageView.getImage().getUrl());

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

    // stage.sizeToScene();
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
            for (Airport airport : graph) {
              if (flightData.getFrom().equals(airport.getName())) {
                airportFrom = airport;
              } else if (flightData.getDestination().equals(airport.getName())) {
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

  private void createButtons() {
    addAirportButton = new ToggleButton("Add Airport");
    connectAirportsButton = new ToggleButton("Connect Airports");
    removeAirportButton = new ToggleButton("Remove Airport");
    removeColorButton = new ToggleButton("Remove Color");
    useBFSAlgorithmButton = new ToggleButton("Use BFS algorithm [RED]");
    useDFSAlgorithmButton = new ToggleButton("Use DFS algorithm [BLUE]");
    showRouteButton = new ToggleButton("Show route");
    loadMapButton = new ToggleButton("Load Map");
  }

  private void activateButtons() {
    addAirportButton.setOnAction(new ButtonHandler());
    removeAirportButton.setOnAction(new ButtonHandler());
    removeColorButton.setOnAction(new ButtonHandler());
    connectAirportsButton.setOnAction(new ButtonHandler());
    useBFSAlgorithmButton.setOnAction(new ButtonHandler());
    useDFSAlgorithmButton.setOnAction(new ButtonHandler());
    showRouteButton.setOnAction(new ButtonHandler());
    loadMapButton.setOnAction(new ButtonHandler());
  }

  private void createVBox() {
    vBox = new VBox(10,
        menuBar,
        addAirportButton,
        removeAirportButton,
        removeColorButton,
        connectAirportsButton,
        useBFSAlgorithmButton,
        useDFSAlgorithmButton,
        showRouteButton,
        loadMapButton,
        weightLabel);
  }

  private void setToggleGroupForButtons() {
    addAirportButton.setToggleGroup(tools);
    removeAirportButton.setToggleGroup(tools);
    removeColorButton.setToggleGroup(tools);
    connectAirportsButton.setToggleGroup(tools);
    useBFSAlgorithmButton.setToggleGroup(tools);
    useDFSAlgorithmButton.setToggleGroup(tools);
    showRouteButton.setToggleGroup(tools);
    loadMapButton.setToggleGroup(tools);
  }

  private void createMenu() {
    archiveMenu = new Menu("Archive");
    menuBar.getMenus().add(archiveMenu);

    loadFlightsItem = new MenuItem("Load flights");
    saveFlightsItem = new MenuItem("Save flights");
    exitItem = new MenuItem("Exit");

    archiveMenu.getItems().add(loadFlightsItem);
    archiveMenu.getItems().add(saveFlightsItem);
    archiveMenu.getItems().add(exitItem);

    loadFlightsItem.setOnAction((new ButtonHandler()));
    saveFlightsItem.setOnAction((new ButtonHandler()));
    exitItem.setOnAction((new ButtonHandler()));
  }

  public void setChanged(boolean changed) {
    this.changed = changed;
  }

  public static void main(String[] args) {
    launch(args);
  }
}