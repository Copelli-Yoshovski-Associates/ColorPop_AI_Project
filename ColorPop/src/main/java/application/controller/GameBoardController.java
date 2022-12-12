package application.controller;

import application.SceneHandler;
import application.Settings;
import application.model.Block;
import application.model.Color;
import application.model.Point;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameBoardController {

	private static final Handler h = new Handler();

	@FXML
	private GridPane previewBlocks;

	@FXML
	private BorderPane mainBorderpane;

	@FXML
	private GridPane colorBlocks;

	@FXML
	private Text time;

	@FXML
	private Text score;

	private final Timer timer = new Timer();

	@FXML
	void initialize() {
		h.initializeBoard();
		time.setText(Settings.MAX_TIME + "");
		score.setText("0");
		TimerTask timeTask = new TimerTask() {
			@Override
			public void run() {
				int currentTime = Integer.parseInt(time.getText());

				if (currentTime != Settings.MAX_TIME) {
					System.out.println("Adding facts");

					for (Block b : h.getBoard())
						if (b.getX() >= 0 && b.getY() >= 0) SceneHandler.solver.addFactBlock(b);
					SceneHandler.solver.prossimaMossa();
				}
				if (currentTime == 0 || h.gameOver()) showResults();
				else time.setText((currentTime - 1) + "");
				if (currentTime % 4 == 0) Platform.runLater(() -> drawBoard(false));
				System.out.println(currentTime);

			}
		};
		timer.scheduleAtFixedRate(timeTask, 1000, 1000);
	}

	private void defaultSchema() {
		if (getBoard() != null) return;
		List<Block> prova = new ArrayList<>();
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				prova.add(new Block(i, j, Settings.DEFAULT_SCHEMA[i][j]));
		h.setBoard(prova);
	}

	public void drawBoard(boolean onlyRedraw) {
		colorBlocks.getChildren().clear();
		if (!onlyRedraw) {
			if (Settings.DEBUG) System.out.println("Generating new preview");
			h.putPreview(h.generatePreview());
		}
		fillWithAnchorPanes();
		ChangeColorGrid();
		h.printBoard();
	}

	public void showResults() {
		timer.cancel();
		int currentTime = Integer.parseInt(time.getText());
		if (currentTime < 1) System.out.println("You won!");
		else System.out.println("You lost!");
		System.out.println("show results: ");
		int emptyBlocks = countEmptyBlocks();
		if (currentTime >= 1) emptyBlocks = 0;
		int currentScore = Integer.parseInt(score.getText()) + emptyBlocks * Settings.EMPTY_SCORE;
		System.out.println("\tscore: " + currentScore);
		if (currentScore > 0) System.out.println("\tYou destroyed " + currentScore / ((Settings.EMPTY_SCORE+Settings.BLOCK_SCORE)/2) + " blocks");
		System.exit(2);
	}

	private int countEmptyBlocks() {
		int emptyBlocks = 0;
		for (Block b : h.getBoard())
			if (b.getColor() == Color.EMPTY) emptyBlocks++;
		return emptyBlocks;
	}

	// fill grade pane with anchor panes
	private void fillWithAnchorPanes() {
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				colorBlocks.add(new AnchorPane(), j, i);

	}

	private void ChangeColorGrid() {
		for (int i = 0; i < Settings.ROWS; i++)
			for (int j = 0; j < Settings.COLUMNS; j++)
				changeColor(i, j, h.get(i, j));
	}

	private boolean checkEmptySpacesOnColumnForFalling() {
		int moves = 0;
		boolean hasToFall = true;
		while (hasToFall) {
			hasToFall = false;
			for (int i = 1; i < Settings.ROWS; i++)
				for (int j = 0; j < Settings.COLUMNS; j++) {
					int currentColor = h.get(i, j).getNumber();
					int colorUnder = h.get(i - 1, j).getNumber();
					if (currentColor == Color.EMPTY.getNumber() && colorUnder != Color.EMPTY.getNumber()) {
						h.set(i, j, h.get(i - 1, j));
						h.set(i - 1, j, Color.EMPTY);
						hasToFall = true;
						moves++;
					}
				}
		}
		checkEmptySpacesOnRowForCentering();
		return moves != 0;
	}

	private boolean checkEmptySpacesOnRowForCentering() {
		int moves = 0;
		boolean hasToCenter = true;
		while (hasToCenter) {
			hasToCenter = false;
			for (int i = 0; i < Settings.ROWS; i++)
				for (int j = 1; j < Settings.COLUMNS; j++) {
					int currentColor = h.get(i, j).getNumber();
					int colorLeft = h.get(i, j - 1).getNumber();
					if (currentColor == Color.EMPTY.getNumber() && colorLeft != Color.EMPTY.getNumber()) {
						h.set(i, j, h.get(i, j - 1));
						h.set(i, j - 1, Color.EMPTY);
						hasToCenter = true;
						moves++;
					}
				}
		}

		Platform.runLater(() -> drawBoard(true));
		return moves != 0;
	}

	private void changeColor(int row, int column, Color color) {
		h.set(row, column, color);
		colorBlocks.getChildren().get(row * Settings.COLUMNS + column).setStyle("-fx-background-color: " + Color.getRGB(color) + ";");
	}

	private int countNeighbors(int row, int column) {
		return h.countNeighbors(row, column, new boolean[Settings.ROWS][Settings.COLUMNS], h.get(row, column));
	}

	private List<Point> getNeighbors(int row, int column) {
		return h.getNeighbors(row, column, new boolean[Settings.ROWS][Settings.COLUMNS], h.get(row, column));
	}

	private void addToScore(boolean isSpecialBlock, int increment) {
		Platform.runLater(() -> {
			if (!isSpecialBlock)
				score.setText((Integer.parseInt(score.getText()) + increment * Settings.BLOCK_SCORE) + "");
//			else
//				score.setText((Integer.parseInt(score.getText()) + increment * Settings.SPECIAL_BLOCK_SCORE) + "");

			return;

		});
	}

	public boolean removeNeighbors(int row, int column) {
		List<Point> neighbors = getNeighbors(row, column);
		if (Settings.DEBUG) System.out.println("found " + neighbors.size() + " neighbors");
		if (neighbors.size() <= Settings.MIN_NEIGHBORS) return false;
		if (Settings.DEBUG) System.out.println("removing " + countNeighbors(row, column) + " neighbors");

		addToScore(h.get(row, column).isSpecial(), neighbors.size());
		for (Point p : neighbors)
			changeColor(p.getX(), p.getY(), Color.EMPTY);

		return checkEmptySpacesOnColumnForFalling();
	}

	public Color[][] getBoard() {
		return h.getBoardColor();
	}

	public boolean gameOver() {
		return h.gameOver();
	}

}
