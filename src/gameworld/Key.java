package gameworld;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class Key extends InteractableObjects{

  private double keyPosX;
  private double keyPosY;
  private String keyName;
  private boolean held = false;


  public Key(String name, double x, double y) {
    this.keyPosX = x;
    this.keyPosY = y;
    this.keyName = name;
  }

  /**
   * checks if the player is holding this item
   * @return
   */
  public boolean ifKeyHold() {
    return this.held;
  }

  public void setHasKey(boolean b) {
    this.held = b;
  }

  public void holdKey() {
    this.held = true;
  }

  public void sendKeyToCanada() {
    if(held) {
      this.keyPosX = 9999;
      this.keyPosY = 9999;
    }
  }

  public String toString() {
    return keyName;
  }

  public double getPosX() {
    return this.keyPosX;
  }


  public double getPosY() {
    return this.keyPosY;
  }

  public String description(){
    //		if(Player.playerHasKey) {
    //			return "Unlock the door pls";
    //		}
    return "A key for a door";
  }

  @Override
  public int toInt() {
    return 8;
  }

  @Override
  public void interact(double pX,double pY) {
    if(!ifKeyHold()) {
      pickup();
    }
  }

  /**
   * pick ups a key
   * @param k
   */
  public void pickup() {
    //		Player.playerHasKey = true;
    this.holdKey();
    this.sendKeyToCanada();
    System.out.println("hello?"+this);
  }
  public void drop() {
    System.out.println("removed"+this);
  }
}
