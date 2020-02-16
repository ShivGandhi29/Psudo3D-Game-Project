package gameworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Main GameWorld class
 *
 * @author Jaimar
 *
 * Contributors:
 * David
 * Tom
 *
 */
public class MainGameWorld {
  private List<GameObjects> gameObjects = new ArrayList<>();//keeps all objects of the game
  private List<Teleporter> activeTeleporters = new ArrayList<Teleporter>();

  private Random random = new Random();

  private Player player; //field for a player
  private Board board; //field for new board
  private int[][] map;

	Timer timer;

  //testing
  public Key testKey;
  public Door testDoor;

  private double telX;
  private double telY;
  int x1 = random.nextInt(20)+1;
  int y1 = random.nextInt(19)+1;

  int x2 = random.nextInt(20)+1;
  int y2 = random.nextInt(19)+1;

  int x3 = random.nextInt(20)+1;
  int y3 = random.nextInt(19)+1;

  int x4 = random.nextInt(20)+1;
  int y4 = random.nextInt(19)+1;

  int x5 = random.nextInt(20)+1;
  int y5 = random.nextInt(19)+1;

  int x6 = random.nextInt(20)+1;
  int y6 = random.nextInt(19)+1;

  int x7 = random.nextInt(20)+1;
  int y7 = random.nextInt(19)+1;

  int x8 = random.nextInt(20)+1;
  int y8 = random.nextInt(19)+1;

  int x9 = random.nextInt(20)+1;
  int y9 = random.nextInt(19)+1;

  int x10 = random.nextInt(20)+1;
  int y10 = random.nextInt(19)+1;

  int x11 = random.nextInt(20)+1;
  int y11 = random.nextInt(19)+1;

  int x12 = random.nextInt(20)+1;
  int y12 = random.nextInt(19)+1;

  int x13 = random.nextInt(20)+1;
  int y13 = random.nextInt(19)+1;

  int x14 = random.nextInt(20)+1;
  int y14 = random.nextInt(19)+1;

  int x15 = random.nextInt(20)+1;
  int y15 = random.nextInt(19)+1;

  int x16 = random.nextInt(20)+1;
  int y16 = random.nextInt(19)+1;

  int x17 = random.nextInt(20)+1;
  int y17 = random.nextInt(19)+1;

  /**
   * new game only constructor, loads default LEVEL_ONE and creates new player
   * @author David
   */
  public MainGameWorld(Level lvl) {
    this.board = new Board(lvl);
    this.player = new Player(board.getLvl().getPosX(),board.getLvl().getPosY());
    timer = new Timer();
    this.map = returnMap();
    initialize();
    }


  public void initialize() {
    for(int i = 0; i < map.length-1; i++) {
      for(int j = 0; j < map.length-1; j++) {
        if(map[i][j] == 2) {
          this.gameObjects.add(new Key("Key",(double)i,(double)j));
        }else if(map[i][j] == 3) {
          this.gameObjects.add(new HiddenKey("HiddenKey",(double)i,(double)j));
        }else if(map[i][j]==6) {
          this.gameObjects.add(new HiddenDoor("HiddenDoor",(double)i,(double)j));
        }else if(map[i][j] == 5) {
          this.gameObjects.add(new Door("Door",i,j));
        }else if(map[i][j] == 8) {
          this.gameObjects.add(new Door("FinalDoor",i,j,true));
        }else if(map[i][j] == 7) {
          this.gameObjects.add(new RedDoor("RedDoor",i,j));
        }
      }
    }
    //initilise teleporters depending on lvl. (David)
    //specifiy coordinates of every teleporter on the level here. (David)
    switch (this.getBoard().getLvl()) {
      case LEVEL_TWO:
        createTeleporters(activeTeleporters, 13,1,14,15,8,15,11,16,20,16,21,5,21,7,21,15);
        break;
      case LEVEL_THREE:
        break;
      case LEVEL_FOUR:
        createTeleporters(activeTeleporters, y1,x1,y2,x2,y3,x3,y4,x4,y5,x5,y6,x6,y7,x7,y8,x8,y9,x9,y10,x10,
            y11,x11,y12,x12,y13,x13,y14,x14,y15,x15,y16,x16,y17,x17);
        break;
      default:
        break;
    }
  }

  /**
   * add telporters to list from coordinates given from method call
   * coorinates are Y,X.
   * @param teleList
   * @param i variable number of int arguments for the cordinates.
   * @author David
   */
  private void createTeleporters(List<Teleporter> teleList, int... i) {
    for (int j = 0; j < i.length ; j = j+2) {
      teleList.add(new Teleporter(i[j],i[j+1]));
    }
  }

  public List<Teleporter> getTeleportList(){
    return this.activeTeleporters;
  }

  /**
   * Tells player to interact
   */
  public Interaction interact() {
    return player.interactOn(this.gameObjects, map);
  }

  public void resetMap() {
    for (GameObjects g : gameObjects) {
      if ( g instanceof Door) {
        map[(int) ((Door) g).getPosX()][(int) ((Door) g).getPosY()] = 5;
      }
    }
  }

  /**
   * checks if player is on a teleporter
   * does lots of checks and finds a teleport node that the player isnt at, and moves them therre
   * @author David
   *
   */
  public void checkTeleporter() {
    Teleporter atTeleporter=null;
    //check if player is on telporter
    if(player.isOnTeleport(activeTeleporters)) {
      //new list of teleporters that arent the current one
      List<Teleporter> otherTeleporters = new ArrayList<Teleporter>();
      //clone the active teleporter list
      for (Teleporter tp : activeTeleporters) {
        Teleporter copy = deepCopy(tp);
        otherTeleporters.add(copy);
      }
      //iterate though all teleporters and see if player is there
      for (int i = activeTeleporters.size()-1; i >= 0 ; i--) {
        if (player.playerX > activeTeleporters.get(i).getTeleporterX() && player.playerX < (activeTeleporters.get(i).getTeleporterX()+1)
            && player.playerY > activeTeleporters.get(i).getTeleporterY() && player.playerY < (activeTeleporters.get(i).getTeleporterY()+1)
            ) {
          //check all otherteleporters and choose the one needed to remove
          for (Teleporter tpp : otherTeleporters) {
            if (tpp.getTeleporterX() == activeTeleporters.get(i).getTeleporterX() &&
                tpp.getTeleporterY() == activeTeleporters.get(i).getTeleporterY()) {
              //assign the one needed to remove to outside this loop for concurecy errors
              atTeleporter = tpp;
            }
          }
        }
      }
      //remove current teleporter from the other list
      otherTeleporters.remove(atTeleporter);
      int i  = random.nextInt(activeTeleporters.size());
      //move player to random other teleporter
      player.getCam().xPos = otherTeleporters.get(i).getTeleporterX();
      player.getCam().yPos = otherTeleporters.get(i).getTeleporterY();

    }

  }

  public Teleporter deepCopy(Teleporter input) {
    Teleporter copy = new Teleporter(input.getTeleporterX(),input.getTeleporterY());
    return copy;
  }


  /**
   * sets new position when player enters teleport
   * @param r
   */
  public void setPlayerTelePos(Random r) {
    int val = r.nextInt(activeTeleporters.size());
    if(activeTeleporters.get(val).available()) {
      System.out.println(activeTeleporters.get(val).getTeleporterX());
      System.out.println(activeTeleporters.get(val).getTeleporterY());
      activeTeleporters.get(val).setActivity();
      System.out.println(activeTeleporters.get(val).available());
      this.telX = activeTeleporters.get(val).getTeleporterX();
      this.telY = activeTeleporters.get(val).getTeleporterY();
    }
  }

  /**
   * gives a new position for player when it enters a teleport
   * @return
   */
  public double getNewTeleX() {
    return this.telX;
  }
  public double getNewTeleY() {
    return this.telY;
  }

  /**
   * returns the current board
   * @return
   */
  public Board getBoard() {
    return this.board;
  }

  /**
   * sets the board - for loading
   * @param loadBoard
   */
  public void setBoard(Board loadBoard) {
    this.board = loadBoard;
  }

  /**
   * returns a map/level
   * @return
   */
  public int[][] returnMap(){
    return this.board.getMap();
  }

  /**
   * gets the player object
   * @return
   */
  public Player getPlayer() {
    return this.player;
  }

	/**
	 * Sets the timer
	 */
	public void setTimer(Timer t) {
		this.timer = t;
	}

	/**
	 * gets the timer object
	 */
	public Timer getTimer() {
		return this.timer;
	}

	/**
	 * sets the player including position and bag - for loading
	 * @param loadedPlayer
	 */
	public void setPlayer(Player loadedPlayer) {
		this.player = loadedPlayer;
	}
}
