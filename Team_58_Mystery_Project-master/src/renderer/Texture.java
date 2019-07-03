package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Texture class loads the textures in for the game to use.
 * @author gandhishiv
 *
 */
public class Texture {
  public int[] pixels; //holds the data for all the pixels in the image of the texture
  private String loc; //finds the image file
  public final int SIZE; //texture size

  /**
   *Texture files that used to load in the images
   */
  public static Texture wallDarkGray = new Texture("src/resources/wall1.png", 128); //1
  public static Texture keyYellow = new Texture("src/resources/keyYellow.jpg", 128); //2

  public static Texture keyRed = new Texture("src/resources/keyRed.png", 128); //3
  public static Texture wallwithoutKey = new Texture("src/resources/wallWithoutKey.png", 128); //4



  public static Texture door = new Texture("src/resources/door.png", 128); //5
  public static Texture hiddenWall = new Texture("src/resources/hiddenDoor.png", 128); //6
  public static Texture hiddenWallLocked = new Texture("src/resources/hiddenWallLocked.png", 128); //7
  public static Texture finalDoor = new Texture("src/resources/finalDoor.png", 128); //8


  public static Texture wallRed = new Texture("src/resources/wall3.png", 128); //8

  /**
   * Method loads the image data into pixels
   * @param location
   * @param size
   */
  public Texture(String location, int size) {
    loc = location;
    this.SIZE = size;
    pixels = new int[SIZE * SIZE];
    load();
  }

  /**
   * Load method gets data from images and store them in an array of pixel data
   */
  private void load() {
    try {
      BufferedImage image = ImageIO.read(new File(loc));
      int width = image.getWidth();
      int height = image.getHeight();
      image.getRGB(0, 0, width, height, pixels, 0, width);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
