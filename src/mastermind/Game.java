package mastermind;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class Game
 */
public class Game implements mastermind.MastermindGame {

  private boolean machineGuessing;
  private static final int NO_POSSIBILITIES = 1296;
  private int numberOfMoves;
  private final ArrayList<ColorCode> possibleCodes;
  private final ArrayList<ColorCode> moves;
  private final ArrayList<Rating> ratings;
  private final ColorCode secretCode;

  public Game() {
    machineGuessing = false;
    numberOfMoves = 0;
    possibleCodes = new ArrayList<>();
    moves = new ArrayList<>();
    ratings = new ArrayList<>();
    fill();
    Random random = new Random();
    secretCode = possibleCodes.get(random.nextInt(possibleCodes.size()));
  }

  /**
   * Game constructor
   */
  public Game(boolean machineGuessing) {
    this();
    this.machineGuessing = machineGuessing;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public boolean isMachineGuessing() {
    return machineGuessing;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public int getMoveCount() {
    return numberOfMoves;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public ColorCode getColorCode(int moveNo) {
    return moves.get(moveNo);
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public Rating getRating(int moveNo) {
    return ratings.get(moveNo);
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public ColorCode getSecret() {
    return secretCode;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public Rating humanMove(ColorCode move) {
    numberOfMoves++;
    Rating rate = getSecret().compare(move);
    ratings.add(rate);
    return rate;
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public ColorCode machineMove() {
    numberOfMoves++;

    if (possibleCodes.size() == 0) {
      return null;
    }

    moves.add(possibleCodes.get(0));
    return possibleCodes.get(0);
  }

  /**
   * {@inheritDoc }
   */
  @Override
  public void eval(Rating rating) {

    ColorCode last = moves.get(moves.size() - 1);
    ArrayList<ColorCode> tmp = new ArrayList<>(possibleCodes);
    possibleCodes.clear();

    for (ColorCode colorCode : tmp) {
      Rating r = colorCode.compare(last);
      if (r.getBlack() == rating.getBlack()
              && r.getWhite() == rating.getWhite()) {
        possibleCodes.add(colorCode);
      }
    }
  }


  private void fill() {
    for (int i = 0; i < NO_POSSIBILITIES; ++i) {
      ColorCode colorCode = new ColorCode();
      int aux = i;
      for (byte j = mastermind.MastermindGame.NUMBER_SLOTS - 1; j >= 0; --j) {
        colorCode.setColor(j
                , (byte) (aux % (int) mastermind.MastermindGame.NUMBER_COLORS));
        aux /= mastermind.MastermindGame.NUMBER_COLORS;
      }
      possibleCodes.add(colorCode);
    }
  }
}
