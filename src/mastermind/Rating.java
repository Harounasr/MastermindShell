package mastermind;
/**
 * class Rating
 */
public class Rating {

  private int black, white;

  /**
   * @param black Rating for the black spikes.
   * @param white Rating for the white spikes.
   */
  public Rating(int black, int white) {
    this.black = black;
    this.white = white;
  }
  /**
   * @return The rating in the number of black spikes.
   */
  public int getBlack() {
    return black;
  }

  /**
   * @return The rating in the number of white spikes.
   */
  public int getWhite() {
    return white;
  }

  /**
   * Returns the string representation of this rating.
   *
   * @return The string representation of this rating.
   */
  @Override
  public String toString() {
    return "black: " + getBlack() + " white: " + getWhite();
  }
}
