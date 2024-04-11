package mastermind;

import java.util.Scanner;

/**
 * Implements the shell functionality to interact with the Game and includes
 * the main method.
 */
public final class Shell {

  private static boolean machineGuessing = false;

  /**
   * Utility class constructor preventing instantiation.
   */
  private Shell() {
    throw new UnsupportedOperationException(
            "Illegal call of utility class constructor.");
  }

  /**
   * Reeds and processes input until the quit command has been entered.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    Game mastermind = new Game();
    Scanner scanner = new Scanner(System.in);
    boolean quit = false;

    while (!quit) {
      System.out.print("mastermind> ");
      String input = scanner.nextLine();
      if (input == null) {
        break;
      }
      String[] tokens = input.trim().split("\\s+");
      if (tokens.length < 1 || tokens[0].isEmpty()) {
        error("Empty command.");
      } else {
        tokens[0] = tokens[0].toUpperCase();
        switch (tokens[0].charAt(0)) {
          case 'H': // help
            cmdHelp();
            break;
          case 'E': // eval
            cmdEval(mastermind, tokens);
            break;
          case 'S': // switch
            toggle();
            mastermind = cmdNew(mastermind);
            break;
          case 'M': // move
            cmdMove(mastermind, tokens);
            break;
          case 'N': // new
            mastermind = cmdNew(mastermind);
            break;
          case'Q': // quit
            quit = true;
            break;
          default:
            error("Error! Unknowncommand " + tokens[0]);
            break;

        }
      }
    }
  }

  /**
   * Shows the help commands
   */
  private static void cmdHelp() {
    print("Mastermind - possible commands:\n" +
            "help\n"
            + "quit\n"
            + "new\n"
            + "switch\n"
            + "move c1 c2 c3 c4\n"
            + "eval b w");
  }


  private static void cmdEval(Game mastermind, String[] command) {
    if (mastermind.isMachineGuessing()) {
      if (isLongEnough(command, 3) && isParseInt(command)) {
        int black = Integer.parseInt(command[1]);
        int white = Integer.parseInt(command[2]);
        mastermind.eval(new Rating(black, white));

        ColorCode colorCode = mastermind.machineMove();
        if (black == 4) {
          print("Wow! I did it!");
        } else if (mastermind.getMoveCount()
                > Game.MAX_MOVES) {
          print("No more moves - I couldn't find solution.");
        } else {
          if (colorCode == null) {
            print("No possibilities left "
                    + "- you have been cheating!");
          } else {
            print("machine guess: "
                    + colorCode.toString());
          }
        }
      }
    } else {
      print("Error! Illegal command - human is guessing!");
    }
  }

  private static Game cmdNew(Game mastermind) {
    mastermind = new Game(machineGuessing);
    if (mastermind.isMachineGuessing()) {
      ColorCode colorCode = mastermind.machineMove();
      print("machine guess: " + colorCode.toString());
    }
    return mastermind;
  }


  private static void cmdMove(Game mastermind, String[] tokens) {
    if (mastermind.isMachineGuessing()) {
      error("Error! Illegal command - machine is guessing!");
      return;
    } else {
      if (isLongEnough(tokens, 5) && isParseInt(tokens)) {
        int[] code = new int[Game.NUMBER_SLOTS];
        for (int i = 0; i < Game.NUMBER_SLOTS; i++) {
          code[i] = Integer.parseInt(tokens[i + 1]);
        }

        if (mastermind.getMoveCount() == MastermindGame.MAX_MOVES
                || (mastermind.getMoveCount() != 0
                && mastermind.getRating(mastermind
                .getMoveCount() - 1).getBlack()
                == Game.NUMBER_SLOTS)) {
          error("Error! game over.");
        } else {
          ColorCode guess = new ColorCode(code);
          Rating rate = mastermind.humanMove(guess);
          print(rate.toString());
          if (rate.getBlack() == Game.NUMBER_SLOTS) {
            print("Congratulations! You needed "
                    + mastermind.getMoveCount() + " moves.");
          } else if (mastermind.getMoveCount()
                  == MastermindGame.MAX_MOVES) {
            print("No more moves - solution: "
                    + mastermind.getSecret());
          }
        }
      }
    }
  }

  private static boolean isLongEnough(String[] tokens, int exactLength) {
    if (tokens.length != exactLength) {
      error("Error!"
              + " " + (exactLength - 1) + " " + "numbers expected!");
      return false;
    } else {
      return true;
    }
  }

  private static boolean isParseInt(String[] input) {
    boolean result = true;
    int i = 1;
    try {
      while (i < input.length) {
        int number = Integer.parseInt(input[i]);
        i++;
      }
    } catch (NumberFormatException e) {
      print("Error! The rating must be entered as numbers, wrong input:"
              + input[i]);
      result = false;
    }
    return result;
  }

  private static void toggle() {
    machineGuessing = !machineGuessing;
  }

  private static void print(String msg) {
    System.out.println(msg);
  }

  private static void error(String msg) {
    System.err.println(msg);
  }
}