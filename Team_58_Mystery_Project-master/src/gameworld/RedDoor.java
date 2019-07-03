package gameworld;

/**
 * Celldoor class
 * @author Jaimar
 *
 */
public class RedDoor extends InteractableObjects{

  public boolean unlock;
  public boolean open;
  private String doorName;

  private int doorX;
  private int doorY;

  /**
   * Create locked door
   */
  public RedDoor(String name,int x,int y) {
    this.doorY = y;
    this.doorX = x;
    this.unlock = false;
    this.open = false;
    this.doorName = name;
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

  public boolean ifUnlock() {
    if(unlock) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * Used for testing purposes
   * @Michael
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
  public String toString() {
    return this.doorName;
  }

  @Override
  public void interact(double playerX, double playerY) {
    /*	if(!Player.playerHasKey) {
			System.out.println(description());
		} else if(ifUnlock()) {
			openDoor();
			System.out.println(description());
		} else {
			unlock = true;
			System.out.println("Unlocked? "+unlock);
		}*/
  }


  public double getPosX() {
    return this.doorX;
  }

  public double getPosY() {
    return this.doorY;
  }




}
