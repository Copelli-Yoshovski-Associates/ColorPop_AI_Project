package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Settings;
import application.model.Color;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameBoardController {

    private static Handler h = new Handler();

    @FXML
    private GridPane previewBlocks;

    @FXML
    private BorderPane mainBorderpane;

    @FXML
    private static GridPane colorBlocks = new GridPane();

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
                if(time.getText().equals("0")) {
                    timer.cancel();
                    showResults();
                } else {
                    time.setText((Integer.parseInt(time.getText()) - 1) + "");
                }
                System.out.println(time.getText());
            }
        };
        timer.scheduleAtFixedRate(timeTask, Long.parseLong("1000"), Long.parseLong("1000"));
        mainBorderpane.setCenter(colorBlocks);
        //   colorBlocks = new GridPane();
    //    colorBlocks.setGridLinesVisible(true);


        h.initializeBoard();
        // fill the colorBlocks with anchorpanes

        // Node node = colorBlocks.getChildren().get(0);
        // node.setStyle("-fx-background-color: RED;");
        fillWithAnchorPanes();
        h.putPreview(h.generatePreview());
        h.getBoard();
        // updateColorBlocks();
    }

    private void showResults() {
        // TODO Auto-generated method stub
        System.out.println("show results");
    }

    // fill grade pane with anchor panes
    private static void fillWithAnchorPanes() {
        for (int i = 0; i < Settings.ROWS; i++) {
            for (int j = 0; j < Settings.COLUMNS; j++) {
                AnchorPane a = new AnchorPane();

                // change color of a to white
                // Rectangle a = new Rectangle();
                a.setPrefSize(50, 50);
                a.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.rgb(255, 255, 255),
                        CornerRadii.EMPTY, Insets.EMPTY)));
                colorBlocks.add(a, j, i);
                Node node = colorBlocks.getChildren().get(i * Settings.COLUMNS + j);
                node.setStyle("-fx-background-color: RED;");
                colorBlocks.getChildren().set(i * Settings.COLUMNS + j, node);
            }
        }
    }

    //

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
