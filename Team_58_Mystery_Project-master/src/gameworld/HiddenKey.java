package gameworld;

public class HiddenKey extends InteractableObjects{
  private double hidKX;
  private double hidKY;
  private String nameK;
  private boolean held = false;

  public HiddenKey(String name, double x, double y) {
    this.nameK = name;
    this.hidKX = x;
    this.hidKY = y;
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
      this.hidKX = 9999;
      this.hidKX = 9999;
    }
  }

  public String toString() {
    return nameK;
  }


  public double getPosX() {
    return this.hidKX;
  }

  public double getPosY() {
    return this.hidKY;
  }

  public String description(){
    //		if(Player.playerHasKey) {
    //			return "Unlock the door pls";
    //		}
    return "A key for a door";
  }

  public void use(Object o) {
    //		if(o instanceof Door) {
    //			if(Player.playerHasKey) {
    //				//use key on door
    //				((Door) o).unlock = true;
    //			}
    //		}
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
  }
  public void drop() {
   }


}
