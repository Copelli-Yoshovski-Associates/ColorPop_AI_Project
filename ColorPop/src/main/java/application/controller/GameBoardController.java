package application.controller;

import application.Settings;
import application.model.Color;
import application.model.Point;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
		time.setText(Settings.MAX_TIME + "");
		TimerTask timeTask = new TimerTask() {
			@Override
			public void run() {
				if (time.getText().equals("0") || h.gameOver()) showResults();
				else time.setText(String.valueOf(Integer.parseInt(time.getText()) - 1));
				System.out.println(time.getText());
			}
		};
		timer.scheduleAtFixedRate(timeTask, 1000, 1000);
		h.initializeBoard();
		drawBoard();
		int posX = Settings.ROWS - 1;
		int posY = Settings.COLUMNS - 2;
		System.err.println(countNeighbors(posX, posY));
		System.err.println(getNeighbors(posX, posY));
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
		checkEmptySpacesOnColumnForFalling();
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

	private void checkEmptySpacesOnColumnForFalling() {
		boolean hasToFall = true;
		while (hasToFall) {
			hasToFall = false;
			for (int i = 1; i < Settings.ROWS; i++)
				for (int j = 0; j < Settings.COLUMNS; j++) {
					int currentColor = h.get(i, j).getNumber();
					int colorUnder = h.get(i - 1, j).getNumber();
					if (currentColor != Color.EMPTY.getNumber() || colorUnder == currentColor) continue;
					currentColor = colorUnder;
					colorUnder = Color.EMPTY.getNumber();
					hasToFall = true;
				}
		}
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

}
