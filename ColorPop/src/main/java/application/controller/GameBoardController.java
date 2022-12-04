package application.controller;

import application.Settings;
import application.model.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class GameBoardController {

	private static Handler h = new Handler();

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

	private Timer timer = new Timer();

	@FXML
	void initialize() {
		time.setText(Settings.MAX_TIME + "");
		TimerTask timeTask = new TimerTask() {
			@Override
			public void run() {
				if (time.getText().equals("0") || h.gameOver()) showResults();
				time.setText(String.valueOf(Integer.parseInt(time.getText()) - 1));
				System.out.println(time.getText());
			}
		};
		timer.scheduleAtFixedRate(timeTask, Long.parseLong("1000"), Long.parseLong("1000"));
		h.initializeBoard();
		for (int i = 0; i < Settings.ROWS/2; i++)
			drawBoard();

	}

	private void drawBoard() {
		colorBlocks.getChildren().clear();
		h.putPreview(h.generatePreview());
		fillWithAnchorPanes();
		ChangeColorGrid();
		h.getBoard();
	}

	private void showResults() {
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
				changeColor(i, j, h.getBoardArray()[i][j]);
	}

	private void changeColor(int row, int column, Color color) {
		colorBlocks.getChildren().get(row * Settings.COLUMNS + column).setStyle("-fx-background-color: " + Color.getColor(color.getNumber()).color + ";");
	}

	/*
	 * // change the background color of the anchor pane at specific cell from grid
	 * pane
	 * private static void changeColor(int row, int column, Color color) {
	 * AnchorPane anchorPane = (AnchorPane) colorBlocks.getChildren().get(row *
	 * Settings.COLUMNS + column);
	 * anchorPane.setStyle("-fx-background-color: " + color.color);
	 * }
	 *
	 * // update the color of entire grid pane
	 * public static void updateColorBlocks() {
	 * for (int i = 0; i < Settings.ROWS; i++) {
	 * for (int j = 0; j < Settings.COLUMNS; j++) {
	 * changeColor(i, j, Color.getColor(h.board[i][j]));
	 * }
	 * }
	 * }
	 *
	 * //update colorBlocks with matrix from Handler
	 * public static void updateColorBlocks(){
	 * colorBlocks.getChildren().clear();
	 * for(int i = 0; i < 10; i++){
	 * for(int j = 0; j < 20; j++){
	 * Rectangle r = new Rectangle(20, 20);
	 * r.setFill(Color.getColor())
	 * //add anchor pane in each ceel of the grid pane
	 * colorBlocks.add(new Rectangle(20, 20, Color.getColor(h.board[i][j]).color),
	 * j, i);
	 * }
	 * }
	 * }
	 */

}
