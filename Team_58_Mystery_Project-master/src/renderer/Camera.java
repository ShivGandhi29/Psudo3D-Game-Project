package renderer;

import java.awt.event.KeyEvent;

/**
 * Camera class represents the player. 1st person view, camera is what player sees
 * @author gandhishiv
 *
 */
public class Camera {
  public double xPos, yPos; //location on player on map
  public double xDir, yDir; //vectors that point in the direction the player is facing
  public double xPlane, yPlane; // vectors that points to the farthest edge of the camera's fov

  public boolean left, right, forward, back; //keyboard bindings to move the camera

  //Move speed and rotation speed determine how quickly the camera moves

  public final double MOVE_SPEED = .10;
  public final double ROT_SPEED = .05;

  /**
   * Camera keeps track of where player is located in the map
   * @param xPos
   * @param yPos
   * @param xDir
   * @param yDir
   * @param xPlane
   * @param yPlane
   */
  public Camera(double xPos, double yPos, double xDir, double yDir, double xPlane, double yPlane) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.xDir = xDir;
    this.yDir = yDir;
    this.xPlane = xPlane;
    this.yPlane = yPlane;
  }


  /**
   *Updates the players position and has collision detection.
   *If statement != 0, there is a collision
   *Matrix used:
   *[ cos(ROT_SPEED) -sin(ROT_SPEED) ]
   *[ sin(ROT_SPEED)  cos(ROT_SPEED) ]
   * @param map
   */
  public void update(int[][] map) {
    //left and right movement
    if(left) {
      double prevXDir =xDir;
      xDir = xDir * Math.cos(ROT_SPEED) - yDir * Math.sin(ROT_SPEED);
      yDir = prevXDir * Math.sin(ROT_SPEED) + yDir * Math.cos(ROT_SPEED);

      double oldxPlane = xPlane;
      xPlane = xPlane * Math.cos(ROT_SPEED) - yPlane * Math.sin(ROT_SPEED);
      yPlane = oldxPlane * Math.sin(ROT_SPEED) + yPlane * Math.cos(ROT_SPEED);
    }

    if(right) {
      double prevXDir = xDir;
      xDir = xDir * Math.cos(-ROT_SPEED) - yDir * Math.sin(-ROT_SPEED);
      yDir = prevXDir * Math.sin(-ROT_SPEED) + yDir * Math.cos(-ROT_SPEED);

      double prevXPlane = xPlane;
      xPlane = xPlane * Math.cos(-ROT_SPEED) - yPlane * Math.sin(-ROT_SPEED);
      yPlane = prevXPlane * Math.sin(-ROT_SPEED) + yPlane * Math.cos(-ROT_SPEED);
    }

    //Forward and back movement
    if(forward) {
      if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
        xPos += xDir*MOVE_SPEED;
      }
      if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0){
        yPos += yDir*MOVE_SPEED;
      }
    }
    if(back) {
      if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0){
        xPos -= xDir*MOVE_SPEED;
      }
      if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0){
        yPos-=yDir*MOVE_SPEED;
      }
    }


  }

  public void keyTyped(KeyEvent arg0) {
    //required method with no use.
  }

  /**
   * @return - x direction variable
   */
  public double getXDir() {
    return this.xDir;
  }

  /**
   * @return - y direction variable
   */
  public double getYDir() {
    return this.yDir;
  }

  /**
   * @return - x plane variable
   */
  public double getXPlane() {
    return this.xPlane;
  }

  /**
   * @return - y plane variable
   */
  public double getYPlane() {
    return this.yPlane;
  }
}
