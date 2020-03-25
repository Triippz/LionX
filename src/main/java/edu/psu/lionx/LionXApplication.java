package edu.psu.lionx;

import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.DummyData;
import edu.psu.lionx.utils.ViewRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * JavaFX App
 */
public class LionXApplication extends Application {

    public static Scene scene;
    public static Stage rootStage;
    private static Logger logger = LoggerFactory.getLogger(LionXApplication.class);
    private static UserService userService;

    @Override
    public void start(Stage stage) throws IOException {
        initialize();

        userService.userLoggedIn(
                () -> {
                    try {
                        openStage(stage, ViewRouter.Routes.MAIN);
                    } catch ( IOException e ) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    try {
                        openStage(stage, ViewRouter.Routes.REGISTER);
                    } catch ( IOException e ) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void initialize() {
        userService = new UserService();
    }


    public static void openStage(Stage stage, ViewRouter.Routes route) throws IOException {
        rootStage = stage;
        FXMLLoader fxmlLoader = ViewRouter.routeScene(ViewRouter.FXML_ROOT, route);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LionX");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void persistDummyUsers() throws IOException {
        for ( User user : DummyData.getDummyUsers() )
        {
            try {
                user.save();
            } catch (ConstraintViolationException e) {
                logger.warn("User={} already exists", user.getEmail());
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}