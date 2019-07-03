package application;
import renderer.Camera;
import renderer.Screen;
import renderer.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import gameworld.Interaction;
import gameworld.Level;
import gameworld.MainGameWorld;
import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import mapeditor.MapEditor;
import persistence.Gamestate;
import gameworld.Timer;


/**
 * Contributors: Tom Clark, Shiv Gandhi.
 * THE APPLICATION.
 * @author David
 *
 */
public class Main extends Application {

  private SetupUtil setupUtil = new SetupUtil();
  private MenuUtil menuUtil = new MenuUtil();
  protected EventUtil eventUtil = new EventUtil();

  protected Stage ps;
  MainGameWorld currentGame;
  Gamestate gameState = new Gamestate();
  protected static Canvas rendererCanvas;
  protected int size = 600;
  protected int mapWidth = 20;
  protected int mapHeight = 20;
  protected Camera camera;
  protected Screen screen;
  protected WritableImage image;
  protected ImageView imgv;

  protected Timer timer = new Timer(); // Timer
  private Timer teleTimer = new Timer();

  Media media;// Media
  MediaPlayer player;

  protected ArrayList<Image> itemArray;

  protected int[] pixels;
  protected int[] imgPixels;
  protected ArrayList<Texture> textures;
  protected int width = size;
  protected int height = size;


  ArrayList<MainMenuParticle> menuParts = new ArrayList<MainMenuParticle>();


  VBox menuVb = new VBox();
  VBox pauseVb = new VBox();
  HBox invHb = new HBox();

  //add and remove these images from the invHB when keys are picked up
  protected List<ImageView> invObjects = new ArrayList<ImageView>();

  String gameTime = timer.getTime();
  Label onScreenTimer = new Label(gameTime);

  Level currentLvl = Level.LEVEL_ONE;

  boolean showHowTo = false;
  Label howTo = new Label(howToString());


  Random random = new Random();
  //for use in deciding what to render.
  protected int runstate = 0;
  protected final int mainmenu = 1;
  protected final int gamerunning = 2;
  protected final int gamepaused = 3;

  //Fields for save game and load game UI elements.
  String lastSaveGameName = "";
  String savePath = "saves";
  AnimationTimer animate;
  FadeTransition saveFade;
  FadeTransition loadFade;
  Label saveSuccLbl = new Label("Save Successful!");
  Label loadSuccLbl = new Label("Load Successful!");
  StackPane wrapper;
  ComboBox<?> saveGameList = new ComboBox<>(listFiles(savePath));



  //this map is here to make sure it's preloaded before a new game is initilised during runtime.
  public int[][] map = {};

  @Override
  public void start(Stage primaryStage) throws Exception {

    ps = primaryStage;// create reference for use outside of start().
    primaryStage.setTitle("Team 58 Mystery Project"); //setting a title to the stage (window header)


    // Create all panes needed
    BorderPane root = new BorderPane();
    final BorderPane hud = new BorderPane();
    wrapper = new StackPane();//wrapper pane for canvas to be displayed in
    root.setCenter(wrapper); //set the wrapper to center of root
    setupCanvas(wrapper);//create and apply canvas.
    //root.setTop(menuUtil.buildMenuBar(this));
    //create Menu Bar
    wrapper.getChildren().add(hud);
    setupUtil.setupInv(this, hud);
    hud.setTop(onScreenTimer);
    currentGame = new MainGameWorld(currentLvl);
    primaryStage.setScene(new Scene(root,width,height));
    primaryStage.setResizable(false);
    //window resizing messes it up

    //TODO put this in a Util
    ps.getScene().getStylesheets().add("css/timer.css");
    onScreenTimer.setMinSize(60, 60);
    onScreenTimer.textAlignmentProperty().set(TextAlignment.CENTER);
    BorderPane.setAlignment(onScreenTimer, Pos.CENTER);
    onScreenTimer.setId("timer");
    onScreenTimer.setVisible(false);

    //create all needed objects for rendering to canvas,
    //MUST be done after setting scene to primaryStage
    setupUtil.constructRenderObjects(this);
    setupKeyEvents();//add key events to the scene.value
    setupUtil.setupGameMessages(this);//prep messages to display correctly when needed.
    menuUtil.setupGameMenus(this);
    startAnimation();
    howTo.setVisible(false);
    primaryStage.show();//display the stage

  }

  /**
   * Starts the game loop.
   * @author David
   */
  private void startAnimation() {
    animate = new GameTimer();
    runstate = mainmenu;
    animate.start();
  }

  /**
   * Runs the game loop, Everything in Handle() is performed each frame.
   * @author David
   */
  private class GameTimer extends AnimationTimer {
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0;//60 times per second
    double delta = 0;

    @Override
    public void handle(long now) {
      switch (runstate) {
        case mainmenu:
          drawMainMenu();
          invHb.setVisible(false); //
          break;
        case gamerunning:
          menuVb.setVisible(false);
          onScreenTimer.setVisible(true);
          invHb.setVisible(true);
          delta = delta + ((now - lastTime) / ns);
          lastTime = now;
          while (delta >= 1) { //should restrict frames to no more than 60fps
            //handles all of the logic of updating pixels on screen from camera position.
            screen.update(camera, pixels);
            camera.update(map);
            render();
            gameLogic();
            delta--;
          }
          break;
        case gamepaused:
          drawPauseMenu();
          break;
        default:
          break;
      }
    }
  }

  /**
   * Handles all game logic during the game loop.
   * @author David
   */
  public void gameLogic() {
    currentGame.getPlayer().setPositionXY(camera.xPos,camera.yPos);
    // Tom - updates all of cam so i can save direction aswell
    currentGame.getPlayer().setCamera(camera);
    if (teleTimer.getTimeInt() == 1) {
      currentGame.checkTeleporter();
      teleTimer.initialize();
    }

    gameTime = timer.getTime();

    onScreenTimer.textProperty().set(gameTime);
  }

  /**
   * Draws the game to the canvas from the image file.
   * @author David
   */
  protected void render() {
    //Draws the background, gets drawn before the image so the frame gets reset per frame.
    rendererCanvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
    rendererCanvas.getGraphicsContext2D().fillRect(0, 0, width, height);;

    //draw the current version of the image to the display canvas.
    PixelWriter pw = image.getPixelWriter(); //get the pixel writer object to write to
    //set each pixel with the current ARGB value stored in the int array of pixels
    pw.setPixels(0, 0, (int)image.getWidth(),(int) image.getHeight(),
        PixelFormat.getIntArgbInstance(), pixels, 0, (int)image.getWidth());
    rendererCanvas.getGraphicsContext2D().drawImage(image, 0, 0, image.getWidth(),
        image.getHeight()); //draw the new image to the canvas.

  }

  /**
   * displays main menu, continuously draws failling particles in background.
   * @author David
   */
  private void drawMainMenu() {
    //toggle correct menu showing
    pauseVb.setVisible(false);
    menuVb.setVisible(true);
    //redraw the background to clear the image each frame
    rendererCanvas.getGraphicsContext2D().setFill(Color.BLACK);
    rendererCanvas.getGraphicsContext2D().fillRect(0, 0, size, size);
    //draw particles whilse particles exist
    int i = menuParts.size() - 1;
    while (i >= 0) {
      MainMenuParticle part = menuParts.get(i);
      //rendererCanvas.getGraphicsContext2D().setFill(Color.WHITE);
      rendererCanvas.getGraphicsContext2D().setFill(part.getColour());
      rendererCanvas.getGraphicsContext2D().fillRect(part.getX(),
          part.getY(), part.getW(), part.getH());
      part.update();
      //check if particle can be removed (off screen) and remove if true.
      if (part.canRemove(size)) {
        menuParts.remove(i);
      }
      i--;
    }
    //create a new particle every frame
    menuParts.add(new MainMenuParticle(size));

  }

  /**
   * displays the pause menu.
   * @author David
   */
  private void drawPauseMenu() {
    rendererCanvas.getGraphicsContext2D().applyEffect(new GaussianBlur());
    pauseVb.setVisible(true);
    animate.stop();
  }

  /**
   * Retrieves the interaction performed and does any nessicary changes to screen objects.
   * @param interaction an Interaction from the enum.
   */
  private void sortInteraction(Interaction interaction) {
    switch (interaction) {
      case PICKUP_KEY:
        invHb.getChildren().remove(invObjects.get(0));
        //add the key image from first index in invObject array
        invHb.getChildren().add(invObjects.get(0));
        break;
      case PICKUP_HIDDEN_KEY:
        invHb.getChildren().remove(invObjects.get(1));
        invHb.getChildren().add(invObjects.get(1));
        break;
      case OPEN_FINAL_DOOR:
        eventUtil.switchLevel(this);
        newGame();
        break;
      default:
        break;
    }
  }

  /**
   * Create a new game.
   * @author David
   */
  protected void newGame() {
    //create a new game
    currentGame = new MainGameWorld(currentLvl);
    //update the map reference to the set map from the new game.
    map = currentGame.returnMap();
    currentGame.resetMap();
    // get the DEFAULT x and y values from LEVEL;
    int x = currentGame.getBoard().getLvl().getPosX();
    System.out.println(currentLvl);
    int y = currentGame.getBoard().getLvl().getPosY();
    //create the Camera and set it to the default position from the LEVEL
    camera = new Camera(x, y, 1, 0, 0, -.66);
    //setup the screen to display the correct map and with the set resolution (size)
    screen = new Screen(map, mapWidth, mapHeight, textures, size, size);
    //change the state to playing the game
    runstate = gamerunning;
    //reset inventory if game was in a play state during current run
    invHb.getChildren().removeAll(invObjects);
    //hide pause menu if starting new game from pause
    pauseVb.setVisible(false);
    // Start Timer when new game starts
    timer.initialize();
    teleTimer.initialize();
    // Music
    media = new Media(new File("music/gamemusic2.mp3").toURI().toString());
    player = new MediaPlayer(media);
    player.play();
    //begin the animation loop.
    animate.start();
  }


  /**
   * controller for the quit game menu option.
   * brings up a confirmation dialog to confirm quit.
   * @author David
   */
  protected void quitGame() {
    Alert quit = new Alert(AlertType.CONFIRMATION);
    quit.setTitle("Quit?");
    quit.setContentText("Are you sure you want to quit?");
    Optional<ButtonType> result = quit.showAndWait();
    if (result.get() == ButtonType.OK) {
      //different quit functionality depending on state.
      switch (runstate) {
        case mainmenu:
          System.exit(0);
          break;
        case gamerunning:
        case gamepaused:
          runstate = mainmenu;
          animate.start();
          break;
        default:
          break;
      }
    } else {
      quit.hide();
    }
  }


  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  protected void doMapEditor() {
    MapEditor.MapEditor(ps);
  }

  private String howToString() {
    return "UP or W: Move Forward\n"
        + "DOWN or S: Move Backward\n"
        + "LEFT or A: Rotate Left\n"
        + "RIGHT or D: Rotate Right\n"
        + "SPACE: Interact\n"
        + "ESC or P: Pause";
  }

  protected void toggleHowTo() {
    if (showHowTo) {
      howTo.setVisible(false);
      showHowTo = false;
    } else if (!showHowTo) {
      howTo.setVisible(true);
      showHowTo = true;
    }
  }

  protected void togglePause() {
    if (runstate == gamerunning) {
      runstate = gamepaused;
      onScreenTimer.setVisible(false);
    } else if (runstate == gamepaused) {
      runstate = gamerunning;
    }
    pauseVb.setVisible(false);
    animate.start();
  }

  /**
   * setupKey events for the main window.
   * @author David
   */
  private void setupKeyEvents() {
    ps.getScene().setOnKeyPressed(key -> {
      KeyCode keyCode = key.getCode();
      switch (keyCode) {
        case W:
        case UP:
          if (runstate == gamerunning) {
            camera.forward = true;
          }
          break;
        case S:
        case DOWN:
          if (runstate == gamerunning) {
            camera.back = true;
          }
          break;
        case A:
        case LEFT:
          if (runstate == gamerunning) {
            camera.left  = true;
          }
          break;
        case D:
        case RIGHT:
          if (runstate == gamerunning) {
            camera.right  = true;
          }
          break;
        case SPACE:
          if (runstate == gamerunning) {
            sortInteraction(currentGame.interact());
          }
          break;
        case ESCAPE:
        case P:  
          togglePause();
          break;
        default:
          break;
      } });

    ps.getScene().setOnKeyReleased(key -> {
      KeyCode keyCode = key.getCode();
      switch (keyCode) {
        case W:
        case UP:
          camera.forward = false;
          break;
        case S:
        case DOWN:
          camera.back = false;
          break;
        case A:
        case LEFT:
          camera.left = false;
          break;
        case D:
        case RIGHT:
          camera.right = false;
          break;
        default:
          break;
      }
    });
  }

  /**
   * List all the files under a directory.
   * @param directoryName desired directory
   */
  protected ObservableList<String> listFiles(String directoryName) {
    File directory = new File(directoryName);
    List<String> fileList = new ArrayList<String>();
    //get all the files from a directory
    File[] flist = directory.listFiles();
    if (flist == null) {
      return null;
    }
    for (File file : flist) {
      if (file.isFile()) {
        // trim the file extension before adding to list
        String name = file.getName();
        if (name.lastIndexOf(".") > 0) {
          name = name.substring(0, name.lastIndexOf("."));
        }
        fileList.add(name);
      }
    }
    //must be an observableArrayList for the ComboBox to use it
    return FXCollections.observableArrayList(fileList);
  }


  private void setupCanvas(Pane wrapper) {
    rendererCanvas = new Canvas();//create new canvas for the game.
    wrapper.getChildren().add(rendererCanvas);//add canvas into wrapper
    // bind canvas properties to the wrapper
    rendererCanvas.widthProperty().bind(wrapper.widthProperty());
    rendererCanvas.heightProperty().bind(wrapper.heightProperty());
  }


  public static void main(String[] args) {
    launch(args);
  }




}
