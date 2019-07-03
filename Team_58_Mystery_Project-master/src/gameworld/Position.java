package gameworld;

/**
 * Position class to know where each objects are placed
 * Used to update x's and y's objects.
 * @author Jaimar
 *
 */
public class Position {

  public int x;
  public int y;

  /*
   * Creates a new position
   */
  public Position(int posX, int posY) {
    this.x = posX;
    this.y = posY;
  }


  /**
   * Updates the x of the player
   * @param amount
   * @return
   */
  public void updateX(int amount) {
    this.x = x + amount;
  }

  /**
   * updates the y of the player
   * @param amount
   */
  public void updateY(int amount) {
    this.y = y + amount;
  }

  /**
   * Used for testing purposes
   */
  public int getPosX(){
    return x;
  }

  /**
   * Used for testing purposes
   */
  public int getPosY(){
    return y;
  }
}
