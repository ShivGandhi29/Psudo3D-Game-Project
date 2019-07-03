package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;


/**
 * Utility class for Main methods for menu and sub menu/dialog creation.
 * @author David
 *
 */
final class MenuUtil {

  /**
   * sets up the main menu and pause menu.
   * @author David
   */
  protected void setupGameMenus(Main main) {

    main.ps.getScene().getStylesheets().add("css/title.css");
    main.ps.getScene().getStylesheets().add("css/button.css");


    Label gameTitle = new Label("Project Mystery: Team 58");
    final Label pauseTitle = new Label("Paused");

    DropShadow ds = new DropShadow();
    ds.setOffsetY(3.0f);
    ds.setColor(Color.SILVER);
    gameTitle.setEffect(ds);
    gameTitle.setId("title");

    ds.setOffsetY(3.0f);
    ds.setColor(Color.BLACK);
    pauseTitle.setEffect(ds);
    pauseTitle.setId("title");

    //There is obviously a better way to do this
    Label blankspace = new Label(" ");
    blankspace.setId("title");
    final Label blankspace2 = new Label(" ");
    blankspace.setId("title");

    main.howTo.setMinSize(100, 100);
    main.howTo.setTextAlignment(TextAlignment.CENTER);
    main.howTo.setTextFill(Color.WHITESMOKE);

    //buttons for mainmenu
    Button newGame = new Button("New Game");
    Button loadGame = new Button("Load Game");
    Button instructions = new Button("How to Play");
    final Button quitGame = new Button("Quit Game");
    final Button levelSwitch = new Button("Map Editor");

    //buttons for pause menu (avoid duplicates because of setting visibliity
    final Button resumeGame = new Button("Resume");
    final Button newGamePause = new Button("New Game");
    final Button saveGame = new Button("Save Game");
    final Button loadGamePause = new Button("Load Game");
    final Button quitToMenu = new Button("Quit To Menu");

    //add css styles to buttons
    newGame.setId("button");
    loadGame.setId("button");
    instructions.setId("button");
    quitGame.setId("button");
    resumeGame.setId("button");
    saveGame.setId("button");
    newGamePause.setId("button");
    loadGamePause.setId("button");
    levelSwitch.setId("button");

    //add actions to each button
    newGame.setOnAction(event -> main.newGame());
    loadGame.setOnAction(event -> loadGameDialog(main));
    instructions.setOnAction(event -> main.toggleHowTo());
    quitGame.setOnAction(event -> main.quitGame());
    resumeGame.setOnAction(event -> main.togglePause());
    saveGame.setOnAction(event -> saveGameDialog(main));
    quitToMenu.setOnAction(event -> main.quitGame());
    newGamePause.setOnAction(event -> main.newGame());
    loadGamePause.setOnAction(event -> loadGameDialog(main));
    levelSwitch.setOnAction(event -> main.doMapEditor());
  //  levelSwitch.setOnAction(event -> main.eventUtil.switchLevel(main));



    //add all buttons to the VBox that represents the MAIN menu.
    main.menuVb.getChildren().add(gameTitle);
    main.menuVb.getChildren().add(blankspace);
    main.menuVb.getChildren().add(newGame);
    main.menuVb.getChildren().add(loadGame);
    main.menuVb.getChildren().add(instructions);
    main.menuVb.getChildren().add(quitGame);
    main.menuVb.getChildren().add(levelSwitch);
    main.menuVb.getChildren().add(main.howTo);
    main.menuVb.setAlignment(Pos.BASELINE_CENTER);

    //add all buttons to the VBox that represents the PAUSE menu
    main.pauseVb.getChildren().add(pauseTitle);
    main.pauseVb.getChildren().add(blankspace2);
    main.pauseVb.getChildren().add(resumeGame);
    main.pauseVb.getChildren().add(newGamePause);
    main.pauseVb.getChildren().add(saveGame);
    main.pauseVb.getChildren().add(loadGamePause);
    main.pauseVb.getChildren().add(quitToMenu);
    main.pauseVb.setAlignment(Pos.BASELINE_CENTER);
    main.pauseVb.setVisible(false);

    main.wrapper.getChildren().add(main.menuVb);
    main.wrapper.getChildren().add(main.pauseVb);
  }

  /**
   * Saves the game to XML format through persistence package.
   * @author David
   */
  protected void saveGameDialog(Main main) {
    // set up a new stage for save game dialog.
    Stage saveGameStage = new Stage();
    saveGameStage.initModality(Modality.APPLICATION_MODAL); //blocks interaction with other windows
    saveGameStage.initOwner(main.ps);//this stage belongs to the primaryStage
    //setup the layout for the dialog
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10,10,10,10));
    grid.setVgap(5);
    grid.setHgap(50);

    //set label for dialog
    Label saveNameLbl = new Label("Enter Save Game Name:");
    GridPane.setConstraints(saveNameLbl, 0, 0);
    grid.getChildren().add(saveNameLbl);

    //user save game name entry
    //editiable ComboBox with all save games in the directory.
    //TODO overwrite duplicate warning
    GridPane.setConstraints(main.saveGameList, 0,1);
    //set the default choice to the last used.
    main.saveGameList.getEditor().setText(main.lastSaveGameName);
    grid.getChildren().add(main.saveGameList);

    //button to confirm and save game name.
    Button save = new Button("Save");
    save.setOnAction(event -> main.eventUtil.saveGameEvents(main, saveGameStage));
    GridPane.setConstraints(save, 1, 0);
    save.setMinWidth(100);
    grid.getChildren().add(save);

    //cancel button closes dialog
    Button cancel = new Button("Close");
    cancel.setOnAction(event -> saveGameStage.close());
    GridPane.setConstraints(cancel, 1, 1);
    cancel.setMinWidth(100);
    grid.getChildren().add(cancel);

    //set the scene and display the dialog
    Scene dialogScene = new Scene(grid, 335, 70);
    saveGameStage.setScene(dialogScene);
    saveGameStage.show();

  }

  /**
   * Loads game through persistence package.
   * Gives the new gameWorld back
   * @author David
   */
  protected void loadGameDialog(Main main) {
    //create a new window to act as a load game dialog
    Stage loadGameStage = new Stage();
    loadGameStage.initModality(Modality.APPLICATION_MODAL); //blocks interaction with other windows
    loadGameStage.initOwner(main.ps);//this stage belongs to the primaryStage

    //setup the layout for the dialog
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10,10,10,10));
    grid.setVgap(5);
    grid.setHgap(50);

    //set label for dialog
    Label loadNameLbl = new Label("Enter Load Game Name:");
    GridPane.setConstraints(loadNameLbl, 0, 0);
    grid.getChildren().add(loadNameLbl);

    //user load game name entry
    GridPane.setConstraints(main.saveGameList, 0,1);
    //set the default choice to the last used.
    main.saveGameList.getEditor().setText(main.lastSaveGameName);
    grid.getChildren().add(main.saveGameList);

    Button loadBtn = new Button("Load");
    loadBtn.setOnAction(event -> main.eventUtil.loadGameEvents(main, loadGameStage));
    main.saveGameList.getEditor().setText(main.lastSaveGameName);
    GridPane.setConstraints(loadBtn, 1, 0);
    loadBtn.setMinWidth(100);
    grid.getChildren().add(loadBtn);

    //close button closes dialog
    Button cancel = new Button("Close");
    cancel.setOnAction(event -> loadGameStage.close());
    GridPane.setConstraints(cancel, 1, 1);
    cancel.setMinWidth(100);
    grid.getChildren().add(cancel);

    //set the scene and display the dialog
    Scene dialogScene = new Scene(grid, 335, 70);
    loadGameStage.setScene(dialogScene);
    loadGameStage.show();

  }

  /**
   * Build menu and menu object and add all relevant menus and functionality.
   * TODO if can't get resoltion change working, merge into one menu.
   * @return a MenuBar object with all menu elements added.
   * @author David
=
   **/
  protected MenuBar buildMenuBar(Main main) {

    final MenuBar mb = new MenuBar();
    // create Menu(s)
    final Menu newMenu = new Menu("Game");
    final Menu settingsMenu = new Menu("Settings");

    // Create all Game menu items
    MenuItem menuNewGame = new MenuItem("New Game");
    MenuItem menuSave = new MenuItem("Save Game");
    MenuItem menuLoad = new MenuItem("Load Game");
    final MenuItem menuQuit = new MenuItem("Quit");
    final MenuItem menuDevQuit = new MenuItem("Dev Quit");

    //set actions of Game menu button events
    menuNewGame.setOnAction(event -> main.newGame());
    menuSave.setOnAction(event -> saveGameDialog(main));
    menuLoad.setOnAction(event -> loadGameDialog(main));
    menuQuit.setOnAction(event -> main.quitGame());
    menuDevQuit.setOnAction(event -> System.exit(0));

    //Create settings menus
    MenuItem menuDisplay = new MenuItem("Display Settings");
    MenuItem menuMapEdit = new MenuItem("Map Editor");

    //set acstions of settings menu button events.
    menuDisplay.setOnAction(event -> showDisplaySettings(main));
    menuMapEdit.setOnAction(event -> main.doMapEditor());

    // Assemble Game menu
    newMenu.getItems().add(menuNewGame);
    newMenu.getItems().add(menuSave);
    newMenu.getItems().add(menuLoad);
    newMenu.getItems().add(menuQuit);
    newMenu.getItems().add(menuDevQuit);

    //Assemble settings menu
    settingsMenu.getItems().add(menuDisplay);
    settingsMenu.getItems().add(menuMapEdit);

    //Assemble Menu Bar
    mb.getMenus().add(newMenu);
    mb.getMenus().add(settingsMenu);

    return mb;
  }

  /**
   * Display Settings window. Uses GridPane, has 3 objects.
   * TODO fix this or comment it out.
   * @author David
   */
  protected void showDisplaySettings(Main main) {
    final Stage displaySettingsDialog = new Stage();
    //blocks interaction with main window
    displaySettingsDialog.initModality(Modality.APPLICATION_MODAL);
    displaySettingsDialog.initOwner(main.ps);//Dialog belongs to primaryStage

    //Set up layout of displaySettingsDialog
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10,10,10,10));
    grid.setVgap(5);
    grid.setHgap(5);

    Label labelRes = new Label("Resolution(>150): ");
    GridPane.setConstraints(labelRes, 0, 0);
    grid.getChildren().add(labelRes);

    // Resolution Text entry
    final TextField resolution = new TextField();
    resolution.setPromptText(Integer.toString(main.size));
    resolution.setPrefColumnCount(7);
    resolution.getText();
    GridPane.setConstraints(resolution, 1, 0);
    grid.getChildren().add(resolution);

    //set up button to save changes
    Button submit = new Button("Submit");
    submit.setOnAction(event -> main.eventUtil.changeResolution(main,
        resolution.getText(), displaySettingsDialog));
    GridPane.setConstraints(submit, 2, 0);
    grid.getChildren().add(submit);

    Scene dialogScene = new Scene(grid, 350, 50);
    submit.setDefaultButton(true); // press ENTER to hit button.
    displaySettingsDialog.setScene(dialogScene);
    displaySettingsDialog.show();
  }

}
