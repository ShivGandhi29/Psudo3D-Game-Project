package gameworld;

public class HiddenDoor extends InteractableObjects{
  private double hidDX;
  private double hidDY;
  private String nameD;
  private boolean unlock;
  private boolean open;

  public HiddenDoor(String name, double x, double y) {
    this.nameD = name;
    this.hidDX = x;
    this.hidDY = y;
  }
  public double getPosX() {
    return this.hidDX;
  }

  public double getPosY() {
    return this.hidDY;
  }

  public String toString() {
    return this.nameD;
  }

  /**
   * opens the door if the door is unlocked
   *
   *
   */
  public void openDoor() {
    if(unlock) {
      open = true;
    }
  }

  /**
   * checks if unlocked
   */
  public boolean ifUnlock() {
    if(unlock) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * unlock the door
   *
   */
  public void unlockDoor(){
    unlock = true;
  }

  /**
   * checks if the door is open
   * @return true if its open and unlocked = can enter, else
   * false cant enter since its closed or locked
   *
   */
  public boolean ifEnterDoor() {
    if(open && unlock) return true;
    else if((!open) && unlock)return false;
    else return false;
  }


  @Override
  public String description() {
    if(!unlock) return "Door is locked, need the key";
    else if(unlock && open) return "You opened the door";
    else
      return "The door is unlocked";

  }

  @Override
  public int toInt() {
    return 7;
  }

  @Override
  public void interact(double playerX, double playerY) {
    //		if(!Player.playerHasKey) {
    //			System.out.println(description());
    //		} else if(ifUnlock()) {
    //			openDoor();
    //			System.out.println(description());
    //		} else {
    //			unlock = true;
    //			System.out.println("Unlocked? "+unlock);
    //		}
  }
}



