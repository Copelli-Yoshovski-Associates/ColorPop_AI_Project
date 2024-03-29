package application;

import application.controller.GameBoardController;
import application.controller.GameOverController;
import application.controller.PlayMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class SceneHandler {

	private Scene scene;
	private Stage stage;
	private static PlayMenuController playMenuController;
	private static GameBoardController gameBoardController;
	private static GameOverController gameOverController;
	private static SceneHandler instance = null;

	public static Solver solver;

	private SceneHandler() {
		solver = new Solver();
	}

	public static SceneHandler getInstance() {
		if (instance == null) instance = new SceneHandler();
		return instance;
	}

	public void init(Stage stage) {
		try {
			this.stage = stage;
			FXMLLoader loader = loadFXML("PlayMenu");
			BorderPane root = loader.load();
			scene = new Scene(root);
			InputStream url = getClass().getResourceAsStream(Settings.FAVICON);
			if (url != null) stage.getIcons().add(new Image(url));
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
	 *
	 * @return FXMLLoader
	 */
	public FXMLLoader loadFXML(String fileName) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/" + fileName + ".fxml"));
		if (Settings.DEBUG) System.out.println("Loading " + fileName + ".fxml from " + loader.getLocation());
		return loader;
	}

	public void setPlayMenuScene() {
		if (Settings.DEBUG) System.out.println("Starting PlayMenu Scene");
		stage.setTitle("Play Menu");
		stage.setHeight(Settings.DEFAULT_HEIGHT);
		stage.setWidth(Settings.DEFAULT_WIDTH);
		stage.setResizable(false);
		stage.setMaximized(false);
		stage.centerOnScreen();
		stage.show();
	}

	public void setGameScene() throws Exception {
		if(Settings.DEBUG) System.out.println("Starting Game Scene");
		FXMLLoader loader = loadFXML("GameBoard");
		BorderPane root = loader.load();
		gameBoardController = loader.getController();

		Solver.setController(gameBoardController);
		Solver.setup();

		scene = new Scene(root);
		stage.setMinHeight(Settings.DEFAULT_HEIGHT);
		stage.setMinWidth(Settings.DEFAULT_WIDTH);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setMaximized(false);
		stage.setWidth(stage.getWidth() + 1);
		stage.show();
		stage.setOnCloseRequest(e -> {
			try {
				gameBoardController.showResults();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void setGameOverScene(boolean win, int currentScore) throws Exception {
		FXMLLoader loader = loadFXML("GameOver");
		BorderPane root = loader.load();
		gameOverController = loader.getController();

		gameOverController.updateScreen(win, currentScore);

		System.out.println("Current SCENE HANDLER: " + currentScore);
		scene = new Scene(root);
		stage.setMinHeight(Settings.DEFAULT_HEIGHT);
		stage.setMinWidth(Settings.DEFAULT_WIDTH);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setMaximized(false);
		stage.setWidth(stage.getWidth() + 1);
		stage.show();
		stage.setOnCloseRequest(e -> {System.exit(2);});
	}

	

}
