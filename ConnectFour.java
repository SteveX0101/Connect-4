import java.util.Scanner;

public class ConnectFour {
  //Initalize the board and round
  static final int ROW_SIZE = 6; //Default value of 6
  static final int COLUMN_SIZE = 7; //Default value of 7, any value above 10 breaks the formatting
  static int[][] board = new int[ROW_SIZE][COLUMN_SIZE]; //Generates the board
  static int round = 1; //Tracks the round

  public static void main(String[] args) { //START OF MAIN METHOD
    Scanner input = new Scanner(System.in);
    while(true) { //START OF THE GAME
      printBoard();
      if (hasWinner()) { //Checks if there is a winner
        println("" + xo(turn()) + " Wins!"); //Announces the winner
      } else if (round > (ROW_SIZE * COLUMN_SIZE)) {
        println("Tie!"); //If the board is filled and there hasn't been a winner then it's a tie
      }
      print("\nEnter a column: "); //Ask the user for a column
      String playerInput = input.nextLine(); //Gets user input
      if (playerInput.equals("EXIT")) { //Breaks out of the game loop
        break;
      } else if (playerInput.equals("RESET")) { //Resets the board and rounds
        resetGame();
      } else if (isVaildinput(playerInput)) { //Check if that columns exist
        if (isSlotopen(strInt(playerInput)) && !hasWinner()) { //Checks if that column has and open slot
          updateBoard(strInt(playerInput)); //Updates the board
          if (!hasWinner()) { //If there hasn't been a winner yet then the rounds will increase by 1
            ++round;
          }
        }
      }
    } //END OF THE GAME
    System.exit(0);
  } //END OF MAIN METHOD

  //Prints the board
  static void printBoard() {
    //Formatting Stuff
    printSpace();
    println("[Connect Four]"); //Title
    if (round <= (ROW_SIZE * COLUMN_SIZE)) { //Prints the current round and who turn it is
      print("[Round " + round + "]");
      println(" => " + xo(turn()));
    } else {
      println();
    }
    //Prints the actual board
    for (int i = 0; i < board.length; ++i) {
      print(" ");
      for (int j = 0; j < board[i].length; ++j) {
        print(xo(board[i][j]));
        print(" ");
      }
      println();
    }
    //Prints the column numbers
    for (int i = 0; i < board[0].length; ++i) {
      print(" " + (i + 1));
    }
    println();
  }

  //Updates the board
  static void updateBoard(int slot) {
    int player = turn(); //Finds who turn it is
    slot -= 1;
    //Finds the lowest place to place
    for (int i = board.length - 1; i >= 0; --i) {
      if (board[i][slot] == 0) {
        board[i][slot] = player;
        break;
      }
    }
  }

  //Reset the board and rounds
  static void resetGame() {
    //Sets all values of the board to zero
    for (int i = 0; i < board.length; ++i) {
      for (int j = 0; j < board[i].length; ++j) {
        board[i][j] = 0;
      }
    }
    //Sets round to one
    round = 1;
  }

  //Return a boolean if there has been a winner
  static boolean hasWinner() {
    if (horizontal() ||
        vertical() ||
        diagonalTB() ||
        diagonalBT()) {
      return true;
    }
    return false;
  }

  //Checks if there has been a horizontal victory
  static boolean horizontal() {
    for (int i = 0; i < board.length; ++i) {
      for (int j = 0; j + 3 < board[i].length; ++j) {
        if (isFour(board[i][j],board[i][j+1],board[i][j+2],board[i][j+3])) {
          return true;
        }
      }
    }
    return false;
  }

  //Checks if there has been a horizontal victory
  static boolean vertical() {
    for (int i = 0; i < board[0].length; ++i) {
      for (int j = 0; j + 3 < board.length; ++j) {
        if (isFour(board[j][i],board[j+1][i],board[j+2][i],board[j+3][i])) {
          return true;
        }
      }
    }
    return false;
  }

  //Checks if there has been a diagonal (Top to Bottom) victory
  static boolean diagonalTB() {
    for (int i = 0; i + 3 < board.length; ++i) {
      for (int j = 0; j + 3 < board[i].length; ++j) {
        if (isFour(board[i][j],board[i+1][j+1],board[i+2][j+2],board[i+3][j+3])) {
          return true;
        }
      }
    }
    return false;
  }

  //Checks if there has been a diagonal (Bottom to Top) victory
  static boolean diagonalBT() {
    for (int i = 3; i < board.length; ++i) {
      for (int j = 0; j + 3 < board[i].length; ++j) {
        if (isFour(board[i][j],board[i-1][j+1],board[i-2][j+2],board[i-3][j+3])) {
          return true;
        }
      }
    }
    return false;
  }

  //Check if any values of the column is zero
  static boolean isSlotopen(int slot) {
    slot -= 1;
    for (int i = 0; i < board.length; ++i) {
      if (board[i][slot] == 0) {
        return true;
      }
    }
    return false;
  }

  //Checks if the string is an vaild column
  static boolean isVaildinput(String input) {
    for (int i = 1; i <= COLUMN_SIZE; ++i) {
      if (input.equals("" + i)) {
        return true;
      }
    }
    return false;
  }

  //Check if four numbers are equal to each other and none are zero
  static boolean isFour(int one, int two, int three, int four) {
    if (one == 0 || two == 0 || three == 0 || four == 0) {
      return false;
    }
    if (one == two && two == three && three == four) {
      return true;
    }
    return false;
  }

  //Converts a string to an integer
  static int strInt(String str) {
    if (str.length() == 1) {
      return Integer.parseInt(str);
    } else {
      return -1;
    }
  }

  //Finds who turn it is
  static int turn() {
    switch (round % 2) {
      case 0: return 2;
      case 1: return 1;
      default: return -1;
    }
  }

  //Return either an x or o
  static char xo(int i) {
    switch (i) {
      case 1: return 'X';
      case 2: return 'O';
      default: return '-';
    }
  }

  //Formatting stuff
  static void printSpace(){
    for (int i = 0; i < 50; ++i) {
      println();
    }
  }

  static void printSpace(int s){
    if (s < 1) { //Min value of 1
      s = 1;
    } else if (s > 100) { //Max value of 100
      s = 100;
    }
    for (int i = 0; i < 50; ++i) {
      println();
    }
  }

  //Too lazy to constantly type System.out.println();
  static void print(String s) {
    System.out.print(s);
  }
  static void print(char c) {
    System.out.print(c);
  }
  static void print(int i) {
    System.out.print(i);
  }
  static void print(boolean b) {
    System.out.print(b);
  }
  static void print(double d) {
    System.out.print(d);
  }
  static void print(long l) {
    System.out.print(l);
  }
  static void print(short sh) {
    System.out.print(sh);
  }
  static void print(byte by) {
    System.out.print(by);
  }
  static void println() {
    System.out.println();
  }
  static void println(String s) {
    System.out.println(s);
  }
  static void println(char c) {
    System.out.println(c);
  }
  static void println(int i) {
    System.out.println(i);
  }
  static void println(boolean b) {
    System.out.println(b);
  }
  static void println(double d) {
    System.out.println(d);
  }
  static void println(long l) {
    System.out.println(l);
  }
  static void println(short sh) {
    System.out.println(sh);
  }
  static void println(byte by) {
    System.out.println(by);
  }
}
