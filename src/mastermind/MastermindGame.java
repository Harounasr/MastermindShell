package mastermind;

/**
 * Interface for a Mastermind game logic.
 */
public interface MastermindGame {

  /**
   * Maximum number of moves.
   */
  byte MAX_MOVES = 7;

  /**
   * Number of colors.
   */
  byte NUMBER_COLORS = 6;

  /**
   * Number of slots for the colors.
   */
  byte NUMBER_SLOTS = 4;

  /**
   * Determines who is guessing at the moment.
   *
   * @return {@code true}, if machine is guessing, {@code false} otherwise.
   */
  boolean isMachineGuessing();

  /**
   * Returns the number of moves already done. Attention with the admissible
   * range! The number of moves is incremented by {@link #machineMove()} or
   * {@link #humanMove(ColorCode)}, respectively.
   *
   * @return The number of guesses so far.
   */
  int getMoveCount();

  /**
   * Returns the color choice for move {@code moveNo}. Valid, irrespective
   * of human or machine guessing.
   *
   * @param moveNo A valid move number.
   * @return The color choice for move {@code moveNo}.
   */
  ColorCode getColorCode(int moveNo);

  /**
   * Returns the black and white rating for move {@code moveNo}. Valid,
   * irrespective of human or machine guessing.
   *
   * {@link Rating} is a bean which has the black and white spike count as
   * properties.
   *
   * @param moveNo A valid move number.
   * @return The rating in the number of black and white spikes.
   */
  mastermind.Rating getRating(int moveNo);

  /**
   * Queries on the secret machine color code. Only if a human is guessing!
   * Should only be displayed at the end of futile guessing.
   *
   * @return The secret machine color code.
   */
  ColorCode getSecret();

  /**
   * Updates the game state according to the next guessed color code by the
   * human. Only if a human is guessing!
   *
   * @param move Human's guessed color code.
   * @return The resulting rating in the number of black and white spikes.
   */
  mastermind.Rating humanMove(ColorCode move);

  /**
   * Performs the next machine guess. Only if the machine is guessing!
   *
   * @return The next color code guessed by the machine or {@code null}, if
   *         there is no admissible color code left (human has cheated by
   *         declaring the black/white rating).
   */
  ColorCode machineMove();

  /**
   * Updates internal game state according to the human's rating for the last
   * color code guess by the machine. Only if the machine is guessing!
   *
   * @param rating The rating in black and white spikes declared by the human.
   */
  void eval(mastermind.Rating rating);

}