package application.controller;

import application.Settings;
import application.model.Color;

import java.util.Random;

public class Handler {

	private int[][] board = new int[Settings.ROWS][Settings.COLUMNS];

	/**
	 * Generates a random color
	 *
	 * @return
	 */
	private Color randomColor() {
		Random random = new Random();
		int color = random.nextInt(Settings.TOTAL_COLORS)+1;
		return Color.getColor(color);
	}

	public void initializeBoard() {
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				board[i][j] = Color.EMPTY.number;
	}

	public Color[] generatePreview() {
		Color[] preview = new Color[Settings.COLUMNS];
		for (int i = 0; i < Settings.COLUMNS; i++)
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
		System.out.println("---------------\n");
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
