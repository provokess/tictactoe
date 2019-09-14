import java.util.Scanner;

/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version. All
 * variables/methods are declared as static (belong to the class) in the non-OO
 * version.
 */
public class TTTConsoleNonOO2P {
	// Name-constants to represent the seeds and cell contents
	public static final int EMPTY = 0;
	public static final int CROSS = 1;
	public static final int NOUGHT = 2;

	// Name-constants to represent the various states of the game
	public static final int PLAYING = 0;
	public static int DRAW = 0;
	public static int CROSS_WON = 0;
	public static int NOUGHT_WON = 0;

	// The game board and the game status
	public static int ROWS = 0; // number of rows and columns
	public static int COLS = 0;
	public static int[][] board = new int[ROWS][COLS]; // game board in 2D array
														// containing (EMPTY,
														// CROSS, NOUGHT)
	public static int currentState; // the current state of the game
									// (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
	public static int currentPlayer; // the current player (CROSS or NOUGHT)
	public static int currntRow, currentCol; // current seed's row and column

	public static int box = 0;
	public static Scanner inp = new Scanner(System.in);

	public static Scanner in = new Scanner(System.in); // the input Scanner

	/** The entry main method (the program starts here) */
	public static void main(String[] args) {
		// Initialize the game-board and current status
		do {
			insertBox(box);
			if (0 < box ) {
				if (box <= 50) {
					ROWS = TTTConsoleNonOO2P.box;
					COLS = TTTConsoleNonOO2P.box;
					board = new int[ROWS][COLS];
					initGame();
				}	
			}
		}  while (box > 50 || box <= 0);
		// Play the game once
		do {
			playerMove(currentPlayer); // update currentRow and currentCol
			updateGame(currentPlayer, currntRow, currentCol); // update
																// currentState
			printBoard();
			// Print message if game-over
			NOUGHT_WON = box;
			CROSS_WON = box-1;
			DRAW = box-2;
			if (currentState == CROSS_WON) {
				System.out.println("'X' won! Bye!");
			} else if (currentState == NOUGHT_WON) {
				System.out.println("'O' won! Bye!");
			} else if (currentState == DRAW) {
				System.out.println("It's a Draw! Bye!");
			}
			// Switch player
			currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
		} while (currentState == PLAYING); // repeat if not game-over
	}

	/**
	 * Initialize the game-board
	 * 
	 * @return
	 */
	public static int insertBox(int box) {
		boolean valid = false;
		System.out.println("How much column box do you want : ");
		int input = inp.nextInt();

		do {
			if (50 >= input && input > EMPTY) {
				TTTConsoleNonOO2P.box = input;
				System.out.println("Let's Play The Game for (" + (input) + ") box ! ");
				valid = true;
			} else if (50 < input) {
				TTTConsoleNonOO2P.box = input;
				System.out.println("This column box (" + (input) + ") is too over. Try again...");
				break;
			}  else if (0 > input) {
				TTTConsoleNonOO2P.box = input;
				System.out.println("This column box (" + (input) + ") is cannot minus. Try again...");
				break;
			} else {
				TTTConsoleNonOO2P.box = input;
				System.out.println("This column box (" + (input) + ") is not valid. Try again...");
				break;
			}

		} while (!valid);
		
		return TTTConsoleNonOO2P.box;
	}

	/** Initialize the game-board contents and the current states */
	public static void initGame() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				board[row][col] = EMPTY; // all cells empty
			}
		}
		currentState = PLAYING; // ready to play
		currentPlayer = CROSS; // cross plays first
	}

	/**
	 * Player with the "theSeed" makes one move, with input validation. Update
	 * global variables "currentRow" and "currentCol".
	 */
	public static void playerMove(int theSeed) {
		boolean validInput = false; // for input validation
		do {
			if (theSeed == CROSS) {
				System.out.print("Player 'X', enter your move (row[1-" + box + "] column[1-" + box + "]): ");
			} else {
				System.out.print("Player 'O', enter your move (row[1-" + box + "] column[1-" + box + "]): ");
			}
			int row = in.nextInt() - 1; // array index starts at 0 instead of 1
			int col = in.nextInt() - 1;
			if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
				currntRow = row;
				currentCol = col;
				board[currntRow][currentCol] = theSeed; // update game-board
														// content
				validInput = true; // input okay, exit loop
			} else {
				System.out.println("This move at (" + (row + 1) + "," + (col + 1) + ") is not valid. Try again...");
			}
		} while (!validInput); // repeat until input is valid
	}

	/**
	 * Update the "currentState" after the player with "theSeed" has placed on
	 * (currentRow, currentCol).
	 */
	public static void updateGame(int theSeed, int currentRow, int currentCol) {
		if (hasWon(theSeed, currentRow, currentCol)) { // check if winning move
			currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
		} else if (isDraw()) { // check for draw
			currentState = DRAW;
		}
		// Otherwise, no change to currentState (still PLAYING).
	}

	/** Return true if it is a draw (no more empty cell) */
	// TODO: Shall declare draw if no player can "possibly" win
	public static boolean isDraw() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (board[row][col] == EMPTY) {
					return false; // an empty cell found, not draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}

	/**
	 * Return true if the player with "theSeed" has won after placing at
	 * (currentRow, currentCol)
	 */
	public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
		for (int i = 0; i < box; i++){
			for (int j = 0; j < box; j++){
				try {
					if (board[currentRow][i] == theSeed){
						return true;
					} else if (board[j][currentCol] == theSeed) {
						return true;
					} else if (board[j][i] == theSeed) {
						return true;
					} else if (currentCol+currentRow == box-1){
						return true;
					}
				} catch (Exception e) {
					System.out.print(e);
					// TODO: handle exception
				}	
			}
		}
		
		
	      return (board[currentRow][0] == theSeed         // 3-in-the-row
                  && board[currentRow][1] == theSeed
                  && board[currentRow][2] == theSeed
             || board[0][currentCol] == theSeed      // 3-in-the-column
                  && board[1][currentCol] == theSeed
                  && board[2][currentCol] == theSeed
             || currentRow == currentCol            // 3-in-the-diagonal
                  && board[0][0] == theSeed
                  && board[1][1] == theSeed
                  && board[2][2] == theSeed
             || currentRow + currentCol == 2  // 3-in-the-opposite-diagonal
                  && board[0][2] == theSeed
                  && board[1][1] == theSeed
                  && board[2][0] == theSeed);
	}

	/** Print the game board */
	public static void printBoard() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				System.out.print("|");
				printCell(board[row][col]); // print each of the cells
				if (col != COLS - 1) {
					System.out.print(""); // print vertical partition
				}
			}
			System.out.println("|");
			if (row != ROWS - 1) {
				System.out.println(""); // print horizontal partition
			}
		}
		System.out.println();
	}

	/** Print a cell with the specified "content" */
	public static void printCell(int content) {
		switch (content) {
		case EMPTY:
			System.out.print("   ");
			break;
		case NOUGHT:
			System.out.print(" O ");
			break;
		case CROSS:
			System.out.print(" X ");
			break;
		}
	}
}
