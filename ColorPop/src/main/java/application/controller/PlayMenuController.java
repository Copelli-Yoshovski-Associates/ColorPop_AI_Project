package application.controller;

import application.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayMenuController {

    @FXML
    private ImageView imageLogo;

    @FXML
    private ImageView imagePlayHuman;

    @FXML
    private ImageView imagePlayComputer;

    @FXML
    private ImageView imageScore;

    @FXML
    private Label labelScore;

    @FXML
    private Label labelCredits;

    @FXML
    private ImageView imageInfo;

    @FXML
    void playComputerMode(MouseEvent event) throws Exception{

        SceneHandler.getInstance().setGameScene();
    }

    @FXML
    void playHumanMode(MouseEvent event) {

    }

    @FXML
    void requestInfo(MouseEvent event) {

    }

}
