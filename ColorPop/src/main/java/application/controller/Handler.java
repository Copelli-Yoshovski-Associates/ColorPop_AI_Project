package application.controller;

import application.Settings;
import application.model.Color;

public class Handler {

	public int[][] board = new int[Settings.ROWS][Settings.COLUMNS];

	/**
	 * Generates a random color
	 *
	 * @return
	 */
	private Color randomColor() {
		int randomNumber = (int) (Math.random() * Settings.TOTAL_COLORS) + 1;
		return Color.getColor(randomNumber);
	}

	public void initializeBoard() {
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				board[i][j] = Color.EMPTY.number;
	}

	// genarate an array of 10 random colors
	public Color[] generatePreview() {
		Color[] preview = new Color[Settings.ROWS];
		for (int i = 0; i < Settings.ROWS; i++)
			preview[i] = randomColor();
		return preview;
	}

	// put the generated array of color in the last row of the board
	public void putPreview(Color[] preview) {

		shiftBoardUp();

		for (int i = 0; i < Settings.COLUMNS; i++)
			board[Settings.ROWS - 1][i] = preview[i].number;

	}

	public void getBoard() {
		for (int i = 0; i < Settings.ROWS; i++) {
			for (int j = 0; j < Settings.COLUMNS; j++)
				System.out.print(board[i][j] + " ");
			System.out.println();
		}
	}

	public Color[][] getBoardArray() {
		Color[][] boardArray = new Color[Settings.ROWS][Settings.COLUMNS];
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				boardArray[i][j] = Color.getColor(board[i][j]);
		return boardArray;
	}

	//shift the board up
	private void shiftBoardUp() {
		if (gameOver()) System.exit(1);
		for (int j = 0; j < Settings.ROWS - 1; j++)
			for (int i = 0; i < Settings.COLUMNS; i++)
				board[j][i] = board[j + 1][i];
	}


	public boolean gameOver() {
		for (int i = 0; i < Settings.COLUMNS; i++)
			if (board[0][i] != Color.EMPTY.number) return true;
		return false;


	}


}
