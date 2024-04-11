package mastermind;

public class ColorCode implements Cloneable {

  private final int[] codes;

  public ColorCode() {
    codes = new int[MastermindGame.NUMBER_SLOTS];

  }


  /**
   * @param code The Array that includes the color code.
   */
  public ColorCode(int[] code) {
    codes = new int[MastermindGame.NUMBER_SLOTS];
    for (int i = 0; i < Game.NUMBER_SLOTS; i++) {
      codes[i] = code[i];
    }
  }

  /**
   * Sets the color{@code color} in the position{@code pos}.
   * @param pos The color position.
   * @param color The color.
   */
  public void setColor(int pos, int color) {
    codes[pos] = color;
  }

  /**
   * Compares two color codes and calculates the number of black spikes , and
   * using the getWhite method also calculates the white spikes. the number
   * of black spikes is increased by one if both codes have the same colors
   * in the same positions.
   *
   * It is stored in the two positions in the two arrays (where the black
   * spikes occur) -1 to not count again when calculating from the white
   * spikes.
   *
   * @param otherCode The second color code.
   * @return The resulting rating in the number of black and white spikes.
   */
  public Rating compare(ColorCode otherCode) {
    int black = 0;
    ColorCode firstCode = new ColorCode(this.codes);
    ColorCode secondCode = new ColorCode(otherCode.codes);
    for (int i = 0; i < codes.length; i++) {
      if (secondCode.codes[i] == firstCode.codes[i]) {
        black++;
        //-1 so that it isn't counted when calculating the white spikes
        secondCode.codes[i] = -1;
        firstCode.codes[i] = -1;
      }
    }
    return new Rating(black, getWhiteNumber(firstCode, secondCode));
  }


  /**
   *  To clone the color code
   *
   *  @return The clone color code.
   */
  @Override
  public ColorCode clone() {
    ColorCode code = new ColorCode();
    for (int i = 0; i < MastermindGame.NUMBER_SLOTS; i++) {
      code.codes[i] = codes[i];
    }
    return code;
  }

  /**
   * Calculates the colors that occur in the two codes but not in the same
   *  position.
   *
   * @param firstCode The first color code.
   * @param secondCode The second color code.
   * @return The number of white spikes.
   */
  public int getWhiteNumber(ColorCode firstCode, ColorCode secondCode) {
    int white = 0;
    for (int i = 0; i < codes.length; i++) {
      if (secondCode.codes[i] != -1) {
        for (int j = 0; j < codes.length; j++) {
          if (secondCode.codes[i] != -1
                  && secondCode.codes[i] == firstCode.codes[j]) {
            white++;
            secondCode.codes[i] = -1;
            firstCode.codes[j] = -1;
          }
        }
      }
    }
    return white;
  }

  /**
   * Returns the string representation of this color code.
   *
   * @return The string representation of this color code.
   */
  @Override
  public String toString() {
    StringBuilder stringbuilder = new StringBuilder();
    for (int i = 0; i < Game.NUMBER_SLOTS; i++) {
      stringbuilder.append(codes[i] + " ");
    }
    return stringbuilder.toString();
  }
}