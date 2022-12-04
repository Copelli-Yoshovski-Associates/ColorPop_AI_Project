package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Settings;
import application.model.Color;
import javafx.fxml.FXML;
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
    private static GridPane colorBlocks;

    @FXML
    private Text time;

    @FXML
    private Text score;

    public static void init(){
        colorBlocks = new GridPane();
        h.initializeBoard();

     //   Node node = colorBlocks.getChildren().get(0);
     //   node.setStyle("-fx-background-color: RED;");
        // fillWithAnchorPanes();
        h.putPreview(h.generatePreview());
        h.getBoard();
       // updateColorBlocks();
    }

    // fill grade pane with anchor panes
    private static void fillWithAnchorPanes() {
        for (int i = 0; i < Settings.ROWS; i++) {
            for (int j = 0; j < Settings.COLUMNS; j++) {
                AnchorPane a = new AnchorPane();
                //change color of a to white
                a.setStyle("-fx-background-color: red;");
                a.setPrefSize(10, 10);
                Node node = colorBlocks.getChildren().get(i * Settings.COLUMNS + j);
                node.setStyle("-fx-background-color: red;");
            }
        }
    }
/*
    // change the background color of the anchor pane at specific cell from grid pane
    private static void changeColor(int row, int column, Color color) {
        AnchorPane anchorPane = (AnchorPane) colorBlocks.getChildren().get(row * Settings.COLUMNS + column);
        anchorPane.setStyle("-fx-background-color: " + color.color);
    }

    // update the color of entire grid pane
    public static void updateColorBlocks() {
        for (int i = 0; i < Settings.ROWS; i++) {
            for (int j = 0; j < Settings.COLUMNS; j++) {
                changeColor(i, j, Color.getColor(h.board[i][j]));
            }
        }
    }

    //update colorBlocks with matrix from Handler
    public static void updateColorBlocks(){
        colorBlocks.getChildren().clear();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 20; j++){
                Rectangle r = new Rectangle(20, 20);
                r.setFill(Color.getColor())
                //add anchor pane in each ceel of the grid pane
                colorBlocks.add(new Rectangle(20, 20, Color.getColor(h.board[i][j]).color), j, i);
            }
        }
    }
*/

    

}
