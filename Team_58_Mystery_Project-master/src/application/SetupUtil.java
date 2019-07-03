package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import renderer.Camera;
import renderer.Screen;
import renderer.Texture;

/**
 * Utility class for setup methods
 * @author David
 *
 */
public class SetupUtil {


  /**
   * creates and assigns Objects needed for rendering
   * @author David
   * Contributor: Shiv
   */
  protected void constructRenderObjects(Main main) {
    main.image = new WritableImage(main.size, main.size);
    main.imgv = new ImageView(main.image);

    main.pixels = getPixels(main.image, 0, 0, (int) main.image.getWidth(),(int) main.image.getHeight());
    main.imgPixels = getPixels(main.image, 0, 0, (int) main.image.getWidth(),(int) main.image.getHeight());
    main.textures = new ArrayList<Texture>();

    main.textures.add(Texture.wallDarkGray); //1
    main.textures.add(Texture.keyYellow);//2 Yellow key wall

    //textures for wallObjects
    main.textures.add(Texture.keyRed); //3 Red key wall
    main.textures.add(Texture.wallwithoutKey); //4

    //filer textures
    main.textures.add(Texture.door); //5 door
    main.textures.add(Texture.hiddenWall); //6 hidden wall
    main.textures.add(Texture.hiddenWallLocked); //7 hidden wall locked
    main.textures.add(Texture.finalDoor); //8 the final door

    //red wall
    main.textures.add(Texture.wallRed);//9 the red walls



    //these are default settings.
    //change first 2 variables to change starting pos in map
    main.camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
    main.screen = new Screen(main.map, main.mapWidth,
        main.mapHeight, main.textures, main.size, main.size);
  }

  /**
   * Returns pixels.
   * @return pixels
   */
  private int[] getPixels(Image img, int x, int y, int w, int h) {

    int[] pixels = new int[w * h];
    PixelReader reader = img.getPixelReader();
    PixelFormat.Type type =  reader.getPixelFormat().getType();
    WritablePixelFormat<IntBuffer> format = null;

    if(type == PixelFormat.Type.INT_ARGB_PRE ) {
      format = PixelFormat.getIntArgbPreInstance();
    } else {
      format = PixelFormat.getIntArgbInstance();
    }

    reader.getPixels(x, y, w, h, format, pixels, 0, w);
    return pixels;

  }

  /**
   * Sets up inventory, loads images in, to be toggled when player picks the items up.
   * @author David
   */
  protected void setupInv(Main main, BorderPane hud) {
    String imgpathKey = "src/resources/inventoryKey.png";
    String imgpathHiddenKey = "src/resources/inventoryKeyRed.png";
    Image keyImg = null; // need to initilise
    Image hiddenKeyImg = null;
    try {
      keyImg = new Image(new File(imgpathKey).toURI().toURL().toExternalForm());
      hiddenKeyImg = new Image(new File(imgpathHiddenKey).toURI().toURL().toExternalForm());
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
    }
    main.invHb.setPadding(new Insets(10,10,10,10));
    hud.setBottom(main.invHb);
    main.invObjects.add(new ImageView(keyImg));
    main.invObjects.add(new ImageView(hiddenKeyImg));
    main.invHb.setVisible(false);

  }

  /**
   * set all in game messages for display to screen, and any animations to them.
   * @author David
   */
  protected void setupGameMessages(Main main) {

    main.saveFade = createFader(main, main.saveSuccLbl);
    main.loadFade = createFader(main, main.loadSuccLbl);

    main.ps.getScene().getStylesheets().add("css/fancytext.css");
    main.saveSuccLbl.setTextFill(Color.WHITE);
    main.saveSuccLbl.setId("fancytext");
    main.loadSuccLbl.setTextFill(Color.WHITE);
    main.loadSuccLbl.setId("fancytext");

    main.saveGameList.setEditable(true);

  }


  /**
   * Used to create fade transition.
   * @param node node to add animation to.
   * @return a FadeTranstion animation for given node.
   */
  private FadeTransition createFader(Main main, Node node) {
    FadeTransition fade = new FadeTransition(Duration.seconds(2.5), node);
    fade.setFromValue(1);
    fade.setToValue(0);
    fade.setOnFinished(event -> main.wrapper.getChildren().remove(node));
    return fade;
  }



}
