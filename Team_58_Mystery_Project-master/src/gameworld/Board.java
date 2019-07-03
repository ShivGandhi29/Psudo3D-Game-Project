package gameworld;

/**
 * Creates a new board level
 * @author Jaimar
 *
 */

public class Board {

  public int cellSize;
  private int width;
  private int height;
  private Level lvl;

	// ceil, floor and walls keep the texture index. 0 = empty
	private int[][] currentMap;
	private int[][] map;

  /**
   * Contrsutor for the Board object.
   */
  public Board(Level level) {
    this.width = level.getWidth();
    this.height = level.getHeight();
    this.cellSize = 50; //??
    this.map = level.getMap();
    this.currentMap = map;
    this.lvl = level;
  }

  /**
   * returns the map in a 2d array int.
   * @return
   */
  public int[][] getMap() {
    return this.map;
  }

  /**
   * returns the width of the map.
   * @return
   */
  public int getWidth() {
    return width;
  }

  /**
   * returns the height of the map.
   * @return
   */
  public int getHeight() {
    return height;
  }

  /**
   * Used to return the level.
   * @returns - lvl 1 Level.
   */
  public Level getLvl() {
    return lvl;
  }
  public void setLvl(Level lvl) {
    this.lvl = lvl;
    this.map = this.lvl.getMap();
  }


  /**
   * used to get the level.
   */
  public void getLvl(Level l) {
    this.map = l.getMap();
  }

  public Board() {

  }


}
