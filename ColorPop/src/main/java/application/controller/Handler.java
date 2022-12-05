package application.controller;

import application.Settings;
import application.model.Color;
import application.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Handler {

	private int[][] board = new int[Settings.ROWS][Settings.COLUMNS];

	/**
	 * Generates a random color
	 *
	 * @return a random color from the Color enum
	 * @see Color
	 *
	 */
	private Color randomColor() {
		Random random = new Random();
		int color = random.nextInt(Settings.TOTAL_COLORS) + 1;
		return Color.getColor(color);
	}

	public void initializeBoard() {
		this.setBoard(new int[Settings.ROWS][Settings.COLUMNS]);
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
			board[Settings.ROWS - 1][i] = preview[i].getNumber();

	}

	public void printBoard() {
		for (int[] row : board) {
			for (int cell : row)
				System.out.print(cell + " ");
			System.out.println();
		}
		for (int i = 0; i < Settings.COLUMNS; i++)
			System.out.print("==");
		System.out.println();
	}

	public Color[][] getBoardColor() {
		Color[][] boardArray = new Color[Settings.ROWS][Settings.COLUMNS];
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				boardArray[i][j] = get(i, j);
		return boardArray;
	}

	//shift the board up
	private void shiftBoardUp() {
		if (gameOver()) System.exit(1);
		for (int j = 0; j < Settings.ROWS - 1; j++)
			System.arraycopy(board[j + 1], 0, board[j], 0, Settings.COLUMNS);
	}


	public boolean gameOver() {
		for (int i = 0; i < Settings.COLUMNS; i++)
			if (board[0][i] != Color.EMPTY.getNumber()) return true;
		return false;
	}

	public void setBoard(int[][] prova) {
		this.board = prova;
	}

	private boolean checkBorders(int row, int column) {
		return row < 0 || row >= Settings.ROWS || column < 0 || column >= Settings.COLUMNS;
	}

	public List<Point> getNeighbors(int row, int column, boolean[][] visited, Color initialColor) {
		if (checkBorders(row, column) || visited[row][column] || get(row, column) == Color.EMPTY)
			return new ArrayList<>();
		visited[row][column] = true;
		if (get(row, column) != initialColor) return new ArrayList<>();
		List<Point> neighbors = new ArrayList<>();
		neighbors.add(new Point(row, column));
		for (Point coords : getNeighborsCoords(row, column))
			neighbors.addAll(getNeighbors(coords.getX(), coords.getY(), visited, initialColor));
		return neighbors;
	}

	private List<Point> getNeighborsCoords(int row, int column) {
		return List.of(
				new Point(row - 1, column), // up
				new Point(row + 1, column), // down
				new Point(row, column - 1), // left
				new Point(row, column + 1)  // right
		);
	}

	public int countNeighbors(int row, int column, boolean[][] visited, Color initialColor) {
		if (checkBorders(row, column) || visited[row][column] || get(row, column) == Color.EMPTY) return 0;
		visited[row][column] = true;
		if (initialColor != get(row, column)) return 0;
		int count = 1;
		for (Point coords : getNeighborsCoords(row, column))
			count += countNeighbors(coords.getX(), coords.getY(), visited, initialColor);
		return count;
	}

	public Color get(int row, int column) {
		return Color.getColor(board[row][column]);
	}

}
