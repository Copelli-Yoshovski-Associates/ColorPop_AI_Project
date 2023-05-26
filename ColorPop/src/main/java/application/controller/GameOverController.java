package application.controller;

import application.ResultsReader;
import application.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class GameOverController {

    @FXML
    private Text endText;

    @FXML
    private BorderPane gameOverText;

    @FXML
    private ImageView attualScore;

    @FXML
    private Text currentScore;

    @FXML
    private Text bestScore;

    @FXML
    private HBox playAgain;

    @FXML
    public void initialize() {
        int highestScore = ResultsReader.getInstance().getHighestScore();
        bestScore.setText(highestScore + "");
    }

    @FXML
    void play(MouseEvent event) throws Exception {
        SceneHandler.getInstance().setGameScene();
    }

    public void updateScreen(boolean win,int score){
        if(win){
            endText.setText("You Win!");
        }else{
            endText.setText("Game Over!");
        }
        currentScore.setText(score+"");
    }

}
