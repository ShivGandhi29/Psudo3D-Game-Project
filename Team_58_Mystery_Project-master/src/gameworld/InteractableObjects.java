package gameworld;

/**
 * game objects that are interactable
 * @author Jaimar
 *
 */
public abstract class InteractableObjects extends GameObjects{

  /**
   * Returns a string of description
   * @return
   */
  public abstract String description();
  /**
   * returns a string of the object
   * @return
   */
  public abstract String toString();

  public abstract double getPosX();

  public abstract double getPosY();

  /**
   * abstract interact method
   * @param playerY
   * @param playerX
   */
  public abstract void interact(double x, double y);
}
