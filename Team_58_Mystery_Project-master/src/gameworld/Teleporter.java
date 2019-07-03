package gameworld;

import java.util.ArrayList;

/**
 *	teleporter class, send player to a random teleporter
 * @author Jaimar
 *
 */
public class Teleporter {
  private double telX;
  private double telY;
  private boolean active = true;

  /**
   * Creates a new Teleporter object
   */
  public Teleporter(double x,double y) {
    this.telX = x;
    this.telY = y;
  }



  @Override
  public String toString() {
    return "Teleporter [telX=" + telX + ", telY=" + telY + "]";
  }



  /**
   * sets teleporter if its usable
   */
  public void setActivity() {
    if(active) this.active = false;
    else this.active = true;
  }


  public double getTeleporterX() {
    return this.telX;
  }

  public double getTeleporterY() {
    return this.telY;
  }

  /**
   * checks if its usable
   * @return
   */
  public boolean available() {
    return this.active;
  }
}
