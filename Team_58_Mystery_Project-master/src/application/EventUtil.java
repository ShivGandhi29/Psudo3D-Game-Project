package application;

import gameworld.Level;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import renderer.Camera;
import renderer.Screen;

/**
 * Utility class for Main events, usually from the menus.
 * @author David
 *
 */
final class EventUtil {


  /**
   * Container method for all the event calls from the save game button action.
   * @author David
   */
  protected void saveGameEvents(Main main, Stage saveGameStage) {
    try {
      //save the game using thstagee user chosen game name and current state of the game.
      main.gameState.saveGame(main.saveGameList.getEditor().getText(), main.currentGame);
      //update the last save game name used for quick access next time.
      main.lastSaveGameName = main.saveGameList.getEditor().getText();;
      //add the successful save message and play the animation.
      main.wrapper.getChildren().add(main.saveSuccLbl);
      main.saveFade.play();
      //reinitialse combobox to get any new additions to savegame list
      main.saveGameList = new ComboBox<>(main.listFiles(main.savePath));
      main.saveGameList.setEditable(true);
      //close the dialog
      saveGameStage.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * container method for all the event calls from the load game button action.
   * @author David
   */
  protected void loadGameEvents(Main main, Stage loadGameStage) {
    try {
      main.currentGame = main.gameState.loadGame(main.saveGameList.getValue().toString());
      //lastSaveGameName = loadText.getText();
      main.wrapper.getChildren().add(main.loadSuccLbl);
      main.loadFade.play();
      main.map = main.currentGame.returnMap();
      double x = main.currentGame.getPlayer().getPositionX();
      double y = main.currentGame.getPlayer().getPositionY();
      double xdir = main.currentGame.getPlayer().getCam().getXDir();
      double ydir = main.currentGame.getPlayer().getCam().getYDir();
      double xp = main.currentGame.getPlayer().getCam().getXPlane();
      double yp = main.currentGame.getPlayer().getCam().getYPlane();
      main.camera = new Camera(x, y, xdir, ydir, xp, yp);
      main.screen = new Screen(main.map, main.mapWidth, main.mapHeight,
          main.textures, main.size, main.size);
      main.runstate = main.gamerunning;
      main.pauseVb.setVisible(false);
      loadGameStage.close();
      main.animate.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void switchLevel(Main main) {
    switch (main.currentGame.getBoard().getLvl()) {
      case LEVEL_ONE:
        main.currentGame.getBoard().setLvl(Level.LEVEL_TWO);
        main.currentLvl = Level.LEVEL_TWO;
        main.currentGame.getPlayer().resetKeys();
        System.out.println("lvl change lvl two");
        break;
      case LEVEL_TWO:
        System.out.println("lvl change lvl three");
        main.currentLvl = Level.LEVEL_THREE;
        main.currentGame.getPlayer().resetKeys();
        main.currentGame.getBoard().setLvl(Level.LEVEL_THREE);
        break;
      case LEVEL_THREE:
        System.out.println("lvl change lvl four");
        main.currentLvl = Level.LEVEL_FOUR;
        main.currentGame.getPlayer().resetKeys();
        main.currentGame.getBoard().setLvl(Level.LEVEL_FOUR);
        break;
      case LEVEL_FOUR:
        System.out.println("lvl change lvl one");
        main.currentLvl = Level.LEVEL_ONE;
        main.currentGame.getBoard().setLvl(Level.LEVEL_ONE);
        break;
      default: break;
    }



  }


  /**
   * changes window size when button is pressed.
   * also closes settings dialog
   * @param newRes new resolution value inputted from user
   * @param displaySettingsDialog the dialog that is in use for this action.
   * @author David
   */

  protected void changeResolution(Main main, String newRes, Stage displaySettingsDialog) {
    if (!newRes.isEmpty()) {
      int newSize = Integer.parseInt(newRes);
      if (newSize >= 150) {
        main.size = newSize;
        main.setWidth(newSize);
        main.setHeight(newSize);
        // update the primaryStage(ref) with new resolution size.
        main.ps.setMinHeight(newSize);
        main.ps.setMinWidth(newSize);
        main.ps.setMaxHeight(newSize);
        main.ps.setMaxWidth(newSize);
        main.screen.setHeight(newSize);
        main.screen.setWidth(newSize);
      }
    }
    displaySettingsDialog.close();
  }



}
