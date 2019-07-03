package renderer;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.ImageView;

import java.awt.Color;

/**
 * Updates the screen based on player location on map. Screen is constantly updating
 * @author gandhishiv
 * Contributors:
 * David
 *
 */
public class Screen {
  public int[][] map;
  public int mapWidth, mapHeight, width, height;
  public ArrayList<Texture> textures;
  public ArrayList<ImageView> items;

  Random random = new Random();
  /**
   * Screen class figures out where walls are and how far away from players they are.
   * @param map
   * @param mapWidth
   * @param mapHeight
   * @param texture
   * @param width
   * @param height
   */

  public Screen(int[][] map, int mapWidth, int mapHeight, ArrayList<Texture> textures,  int width, int height) {
    this.map = map;
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.textures = textures;

    this.width = width;
    this.height = height;
  }

  /**
   * Update method recalculates how the screen should look based on player position on map
   * @param camera
   * @param pixels
   * @return pixels
   */
  public int[] update(Camera camera, int[] pixels) {


    //		javafx.scene.paint.Color randomC = javafx.scene.paint.Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    //		javafx.scene.paint.Color randomC2 = javafx.scene.paint.Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    Color ceilingColor = fxColorToAwtColor(javafx.scene.paint.Color.BLACK);
    //		Color ceilingColor = fxColorToAwtColor(randomC);

    //ceiling dark gray
    for(int i = 0; i < pixels.length/2; i++) {
      if(pixels[i] != ceilingColor.getRGB()) pixels[i] = ceilingColor.getRGB();
    }//
    //		Color floorColor = fxColorToAwtColor(randomC2) ;
    Color floorColor = fxColorToAwtColor(javafx.scene.paint.Color.DIMGRAY) ;
    //floor dark gray
    for(int i=pixels.length/2; i<pixels.length; i++){
      if(pixels[i] != floorColor.getRGB()) pixels[i] = floorColor.getRGB();
    } //

    //Main loop, goes through all vertical bars on the screen and casts a ray to tfigure out what wall
    //should be hit first.
    for(int i = 0; i < width; i++) {
      double camX = 2 * i / (double)(width) -1;
      double rayDirX = camera.xDir + camera.xPlane * camX;
      double rayDirY = camera.yDir + camera.yPlane * camX;

      //Map position
      int mapX = (int)camera.xPos;
      int mapY = (int)camera.yPos;

      //length of ray from current position to next x or y-side
      double sideDistX, sideDistY;


      //Length of ray from one side to next in map
      double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
      double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
      double wallDis;

      //Direction to go in x and y
      int stepX, stepY;
      boolean hit = false;//was a wall hit
      int side = 0;//was the wall vertical or horizontal

      //Figure out the step direction and initial distance to a side
      if (rayDirX < 0){
        stepX = -1;
        sideDistX = (camera.xPos - mapX) * deltaDistX;
      }else{
        stepX = 1;
        sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
      }

      if (rayDirY < 0){
        stepY = -1;
        sideDistY = (camera.yPos - mapY) * deltaDistY;
      }else{
        stepY = 1;
        sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
      }

      //Loop to find where the ray hits a wall
      while(!hit) {
        //Jump to next square
        if (sideDistX < sideDistY){
          sideDistX += deltaDistX;
          mapX += stepX;
          side = 0;
        } else{
          sideDistY += deltaDistY;
          mapY += stepY;
          side = 1;
        }
        //Check if ray has hit a wall
        if(map[mapX][mapY] > 0) {
          hit = true;
        }
      }
      //Calculate distance to the point of impact
      if(side == 0) {
        wallDis = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
      }else {
        wallDis = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
      }

      //Calculate the height of the wall based on the distance from the camera
      int lineHeight;
      if(wallDis > 0) {
        lineHeight = Math.abs((int)(height / wallDis));
      }else {
        lineHeight = height;
      }
      //calculate lowest and highest pixel to fill in current stripe
      int drawStart = -lineHeight/2 + height/2;
      if(drawStart < 0) {
        drawStart = 0;
      }

      int drawEnd = lineHeight/2 + height/2;
      if(drawEnd >= height){
        drawEnd = height - 1;
      }
      //add a texture
      //sees what type of object
      int texNum = map[mapX][mapY] - 1;
      double wallX;//Exact position of where wall was hit
      if(side == 1) {//If its a y-axis wall
        wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
      } else {//X-axis wall
        wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
      }
      wallX -= Math.floor(wallX);




      //x coordinate on the texture
      int textureX = (int)(wallX * (textures.get(texNum).SIZE));
      if(side == 0 && rayDirX > 0) {
        textureX = textures.get(texNum).SIZE - textureX - 1;
      }
      if(side == 1 && rayDirY < 0) {
        textureX = textures.get(texNum).SIZE - textureX - 1;
      }


      //calculate y coordinate on texture
      for(int y=drawStart; y<drawEnd; y++) {
        int textureY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
        int color;
        if(side == 0 || side == 1) {
          color = textures.get(texNum).pixels[textureX + (textureY * textures.get(texNum).SIZE)];
        }
        else {
          color = (textures.get(texNum).pixels[textureX + (textureY * textures.get(texNum).SIZE)]>>1) & 8355711;//Make y sides darker
        }
        pixels[i + y * (width)] = color;
      }
    }
    return pixels;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  private Color fxColorToAwtColor(javafx.scene.paint.Color c) {
    int r = (int) (c.getRed()*255);
    int g = (int) (c.getGreen()*255);
    int b = (int) (c.getBlue()*255);
    return new Color(r,g,b);
  }


}

