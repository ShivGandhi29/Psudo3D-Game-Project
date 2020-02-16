package gameworld;

import java.util.ArrayList;
import java.util.List;
import renderer.Camera;

/**
 * Controllable player class
 * @author Jaimar
 *
 */
public class Player {

  public boolean playerHasKey = false;
  public boolean playerHasHiddenKey = false;
  public double playerX;
  public double playerY;
  private Camera cam;

  /**
   * Creates a new player object
   */
  public Player(double pX, double pY) {
    this.playerX = pX;
    this.playerY = pY;
  }

  /**
   * checks if player is on a teleport
   * @return
   */
  public boolean isOnTeleport(List<Teleporter> teleports) {
    for(Teleporter t: teleports) {
      if(playerX > t.getTeleporterX() && playerX < (t.getTeleporterX()+1)
          && playerY > t.getTeleporterY() && playerY < (t.getTeleporterY()+1)
          ) {
        System.out.println("On teleporter");
        return true;
      }
    }
    return false;
  }



  /**
   * Player interacts on a furniture, guard, door etc
   */
  public Interaction interactOn(List<GameObjects> gO, int[][] map) {

    InteractableObjects g = (InteractableObjects) closestObject(gO);
    if(g!=null) {
      if (g instanceof Door && ((Door) g).isFinalDoor()) {
        return Interaction.OPEN_FINAL_DOOR;
      } else if (g instanceof Door && playerHasKey ){
        map[(int) g.getPosX()][(int) g.getPosY()] = 0;
        return Interaction.OPEN_DOOR;
      } else if (g instanceof RedDoor && playerHasHiddenKey ){
        map[(int) g.getPosX()][(int) g.getPosY()] = 0;
        return Interaction.OPEN_DOOR;
      }  else if (g instanceof HiddenDoor){
        map[(int) g.getPosX()][(int) g.getPosY()] = 0;
        return Interaction.OPEN_HIDDEN_DOOR;
      } else if (g instanceof HiddenKey) {
        map[(int) g.getPosX()][(int) g.getPosY()] = 4;
        playerHasHiddenKey = true;
        return Interaction.PICKUP_HIDDEN_KEY;
      } else if (g instanceof Key) {
        playerHasKey = true;
        map[(int) g.getPosX()][(int) g.getPosY()] = 4;
        return Interaction.PICKUP_KEY;
      }
      g.interact(playerX, playerY);
    }
    System.out.println("Interacting on object: " + g);
    return Interaction.NOTHING;
  }


  //	public static void putInBag(InteractableObjects o) {
  //		bags.add(o);
  //		System.out.println("Successful to bag" + bags.get(0));
  //	}

  /**
   * chooses the closest object to the player
   * @param List<GameObjects>
   * @return GameObjects
   */
  public GameObjects closestObject(List<GameObjects> gO) {
    GameObjects g = null;
    double coX;//closestX
    double coY;//closestY

    for(GameObjects o:gO) {
      if(o instanceof Key) {
        coX = ((Key) o).getPosX();
        coY = ((Key) o).getPosY();

        //				System.out.println("North: "+(coX-1)+" "+(coX-2)+" "+coY+" "+(coY+1));

        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }
      }else if(o instanceof Door) {
        coX = ((Door) o).getPosX();
        coY = ((Door) o).getPosY();
        //				System.out.println("South: "+(coX+1)+" "+(coX+2)+" "+coY+" "+(coY+1));
        //
        //				System.out.println((coY+1));
        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }
        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }
      }else if(o instanceof Door) {
        coX = ((Door) o).getPosX();
        coY = ((Door) o).getPosY();
        //				System.out.println("South: "+(coX+1)+" "+(coX+2)+" "+coY+" "+(coY+1));
        //
        //				System.out.println((coY+1));

        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }

      }else if(o instanceof HiddenDoor) {
        coX = ((HiddenDoor) o).getPosX();
        coY = ((HiddenDoor) o).getPosY();
        //				System.out.println("South: "+(coX+1)+" "+(coX+2)+" "+coY+" "+(coY+1));
        //
        //				System.out.println((coY+1));
        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }

      }else if(o instanceof HiddenKey) {
        coX = ((HiddenKey) o).getPosX();
        coY = ((HiddenKey) o).getPosY();
        //				System.out.println("South: "+(coX+1)+" "+(coX+2)+" "+coY+" "+(coY+1));
        //
        //				System.out.println((coY+1));
        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }


      }else if(o instanceof RedDoor) {
        coX = ((RedDoor) o).getPosX();
        coY = ((RedDoor) o).getPosY();
        //				System.out.println("South: "+(coX+1)+" "+(coX+2)+" "+coY+" "+(coY+1));
        //
        //				System.out.println((coY+1));
        if((playerX >= (coX+1) && playerX <= (coX +2) && playerY >= coY && playerY <= (coY+1)) //south of the block
            || (playerX <= (coX+1) && playerX >= (coX -1) && playerY >= coY && playerY <= (coY+1)) //north of the block
            || (playerY >= (coY+1) && playerY <= (coY +2) && playerX >= coX && playerX <= (coX+1)) //right of the block
            || (playerY <= (coY+1) && playerY >= (coY -1) && playerX >= coX && playerX <= (coX+1))) //left fo the block
        {
          g = o;
        }
      }
    }
    return g;
  }

  public void resetKeys() {
    playerHasKey = false;
    playerHasHiddenKey = false;
  }

  /**
   * uses what ever object it wants to use
   * @param u
   */
  public void use(InteractableObjects u) {
    if(u instanceof Key) {
      //unlock door
    }
  }

  /**
   * Get Player X position
   * @return - double - returns player position X
   */
  public double getPositionX() {
    return this.playerX;
  }

  /**
   * Get Player Y position
   * @return - double - returns player position Y
   */
  public double getPositionY() {
    return this.playerY;
  }


  /**
   * return boolean - player has key
   */
  public boolean getPlayerKey() {
    return this.playerHasKey;
  }

  /**
   * return boolean - player has hidden key
   */
  public boolean getPlayerHiddenKey() {
    return this.playerHasHiddenKey;
  }

  /**
   * @param - Boolean - adds key value
   */
  public void setPlayerKey(Boolean b) {
    this.playerHasKey = b;
  }

  /**
   * @param - Boolean - adds key value
   */
  public void setPlayerHiddenKey(Boolean b) {
    this.playerHasHiddenKey = b;
  }

  /**
   * @return - Get camera object with variables of player
   */
  public Camera getCam() {
    return this.cam;
  }

  /**
   * sets x and y position
   * @param x
   */
  public void setPositionXY(double x,double y) {
    this.playerX = x;
    this.playerY = y;
  }

  /**
   * sets the cam field to the camera passed in the parameter
   * @param camera
   */
  public void setCamera(Camera camera) {
    this.cam = camera;
  }
}