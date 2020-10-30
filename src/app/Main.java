package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 */
public class Main extends Application {

    public static final int squareSideLength = 40;
    public static final Model model = new Model(squareSideLength);
    public static final InteractionModel iModel = new InteractionModel();
    public static final View view = new View();
    public static final Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) {
        try {
            Scene scene = new Scene(view,400,400);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }



}