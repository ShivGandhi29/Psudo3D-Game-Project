package gameworld;
/**
 * @author clarktom
 *
 * This class is the timer logic for the game
 * and the teleporter refresh rate
 */
public class Timer {

	long startTime;  // Uses the system clock
	long stopTime;  // To work out difference in time
	int min;
	int sec;


	/**
	 * This initializes the time to reference the start of the game
	 */
	public void initialize() {
		startTime = System.currentTimeMillis();
		sec = 0;
		min = 0;
	}

  /**
   * This returns the time in string for for the label
   * @return String - Label format of time thats past
   */
  public String getTime() {
    stopTime = System.currentTimeMillis();
    sec = (int) (stopTime - startTime)/1000;
    if(sec == 60) {
      sec = 0;
      min++;
      startTime = System.currentTimeMillis();
    }
    return min + " : " + sec;
  }


	/**
	 *
	 * @return int - returns the seconds in int
	 */
	public int getTimeInt() {
		stopTime = System.currentTimeMillis();
		sec = (int) (stopTime - startTime)/1000;
		return sec;
	}

  /**
   * This returns the int version of the seconds time
   * @return int - Seconds
   */
  public int getSeconds() {
    return this.sec;
  }

	/**
	 * sets the timer seconds
	 */
	public void setSeconds(int s) {
		this.sec = s;
	}

	/**
	 * sets the timer seconds
	 */
	public void setMinutes(int m) {
		this.min = m;
	}

	/**
	 * get the minutes from class
	 * @return int - return the mins
	 */
	public int getMinutes() {
		return this.min;
	}

}