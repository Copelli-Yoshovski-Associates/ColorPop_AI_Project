public class MainApplication extends Application{

    public static void main(String[] args){
        laiunch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneHandler.getInstance().init(primaryStage);
    }
}