package edu.psu.lionx;

import edu.psu.lionx.utils.ViewRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class LionXApplication extends Application {

    public static Scene scene;
    public static Stage rootStage;

    @Override
    public void start(Stage stage) throws IOException {
        rootStage = stage;
        FXMLLoader fxmlLoader = ViewRouter.loadRoot(ViewRouter.FXML_ROOT, ViewRouter.Routes.MAIN);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LionX");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}