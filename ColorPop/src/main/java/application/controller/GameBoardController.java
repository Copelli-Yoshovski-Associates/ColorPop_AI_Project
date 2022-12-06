package application.controller;

import application.Settings;
import application.model.Color;
import application.model.Point;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;
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
		time.setText(Settings.MAX_TIME + "");
		TimerTask timeTask = new TimerTask() {
			@Override
			public void run() {
				int currentTime = Integer.parseInt(time.getText());
				if (currentTime == 0 || h.gameOver()) showResults();
				else time.setText((currentTime - 1) + "");
				Random random = new Random();

				if (currentTime % 5 == 0) Platform.runLater(() -> {
					int randomColumn = random.nextInt(Settings.COLUMNS);
					int randomRow = random.nextInt(Settings.ROWS);
					if (removeNeighbors(randomRow, randomColumn)) drawBoard();

				});


				if (currentTime % (Settings.GENERATION_TIME * 2) == 0 || currentTime == Settings.MAX_TIME)
					Platform.runLater(() -> {
						System.out.println("Generating new preview");
						drawBoard();
					});
				System.out.println(currentTime);


			}
		};
		timer.scheduleAtFixedRate(timeTask, 1000, 1000);
		h.initializeBoard();
	}

	private void defaultSchema() {
		int[][] prova = Settings.DEFAULT_SCHEMA;
		h.setBoard(prova);
	}

	private void drawBoard() {
		colorBlocks.getChildren().clear();
		if (Settings.DEBUG) defaultSchema();
		else h.putPreview(h.generatePreview());
		fillWithAnchorPanes();
		ChangeColorGrid();
		h.printBoard();
	}

	public void showResults() {
		timer.cancel();
		// TODO Auto-generated method stub
		System.out.println("show results");
		System.exit(2);
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
	private boolean checkEmptySpacesOnRowForCentering(){
		int moves = 0;
		boolean hasToCenter = true;
		while (hasToCenter) {
			hasToCenter = false;
			for (int i = 0; i < Settings.ROWS; i++)
				for (int j = 1; j < Settings.COLUMNS; j++) {
					int currentColor = h.get(i, j).getNumber();
					int colorLeft = h.get(i, j-1).getNumber();
					if (currentColor == Color.EMPTY.getNumber() && colorLeft != Color.EMPTY.getNumber()) {
						h.set(i, j, h.get(i, j-1));
						h.set(i, j-1, Color.EMPTY);
						hasToCenter = true;
						moves++;
					}
				}
		}
		return moves != 0;
	}

	private void changeColor(int row, int column, Color color) {
		colorBlocks.getChildren().get(row * Settings.COLUMNS + column).setStyle("-fx-background-color: " + Color.getRGB(color) + ";");
	}

	private int countNeighbors(int row, int column) {
		return h.countNeighbors(row, column, new boolean[Settings.ROWS][Settings.COLUMNS], h.get(row, column));
	}

	private List<Point> getNeighbors(int row, int column) {
		return h.getNeighbors(row, column, new boolean[Settings.ROWS][Settings.COLUMNS], h.get(row, column));
	}

	private boolean removeNeighbors(int row, int column) {
		List<Point> neighbors = getNeighbors(row, column);
		System.out.println("removing " + neighbors.size() + " neighbors");
		if (neighbors.size() < 2) return false;
		System.out.println("removing " + countNeighbors(row, column) + " neighbors");
		for (Point p : neighbors) {
			h.set(p.getX(), p.getY(), Color.EMPTY);
			changeColor(p.getX(), p.getY(), Color.EMPTY);
		}

		return checkEmptySpacesOnColumnForFalling();
	}

}
