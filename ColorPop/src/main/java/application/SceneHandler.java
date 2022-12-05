package application;

import application.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneHandler {

    private Scene scene;
    private Stage stage;
    private static PlayMenuController playMenuController;
    private static GameBoardController gameBoardController;
    private static SceneHandler instance = null;

    private SceneHandler() {
    }

    public static SceneHandler getInstance(){
        if(instance == null)
            instance = new SceneHandler();
        return instance;
    }

    public void init(Stage stage) {
        try {
            this.stage = stage;
            FXMLLoader loader = loadFXML("PlayMenu");
            BorderPane root = loader.load();
            scene = new Scene(root);
          //  stage.getIcons().add(new Image(getClass().getResourceAsStream(Settings.DEFAULT_DASHBOARD_BG_PHOTO_PATH)));
            stage.setScene(scene);
            setPlayMenuScene();

        } catch (Exception e) {
           // Logger.getInstance().captureException(e);
            return;
        }
    }

    /**
     * Genera un FXMLLoader a partire dal nome di un file di tipo .fxml <br>
     * Il file deve risiedere obbligatoriamente nella cartella del progetto
     * <i>"application/view"
     *
     * @param fileName - <i>Nome del file senza l'estensione. Accettati solo di tipo
     *                 .fxml</i>
     * @return FXMLLoader
     */
    public FXMLLoader loadFXML(String fileName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/" + fileName + ".fxml"));
        System.out.println("hello");
        return loader;
    }

    public void setPlayMenuScene() {
        stage.setTitle("Play Menu");
        stage.setHeight(Settings.DEFAULT_HEIGHT);
        stage.setWidth(Settings.DEFAULT_WIDTH);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void setGameScene() throws Exception{
			FXMLLoader loader = loadFXML("GameBoard");
			BorderPane root = loader.load();
			gameBoardController = loader.getController();
            scene = new Scene(root);
			stage.setMinHeight(Settings.DEFAULT_HEIGHT);
			stage.setMinWidth(Settings.DEFAULT_WIDTH);
            stage.setScene(scene);
			stage.setResizable(true);
			stage.setMaximized(true);
			stage.show();
            stage.setOnCloseRequest(e -> gameBoardController.showResults());
    }
}
