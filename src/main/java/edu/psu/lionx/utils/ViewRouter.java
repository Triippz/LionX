package edu.psu.lionx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static edu.psu.lionx.LionXApplication.rootStage;

public class ViewRouter {

    public static final String FILE_DELIM = File.separator;
    public static final String FXML_ROOT = FILE_DELIM + "fxml" + FILE_DELIM;
    public static final String FXML_DIALOG = FILE_DELIM + "fxml" + FILE_DELIM + "dialogs" + FILE_DELIM;
    private static Logger log = LoggerFactory.getLogger(ViewRouter.class);

    public enum Routes {
        MAIN("Main.fxml"),
        HOME("Home.fxml"),
        NEWS("News.fxml"),
        MANAGE_ALERTS("ManageAlert.fxml"),
        MANAGE_PORTFOLIOS("Wallets.fxml"),
        ABOUT("About.fxml"),
        SETTINGS("Settings.fxml"),
        WALLETS("Wallets.fxml"),
        ORDER_HISTORY("OrderHistory.fxml"),
        LOGIN("Login.fxml"),
        REGISTER("Register.fxml");
        private String route;

        Routes ( String route ) {
            this.route = route;
        }

        public String getRoute() {
            return this.route;
        }
    }

    /**
     * Routes UI to a new scene
     * @param path The file path leading to fxml file
     * @param route The route (scene) to switch to
     */
    public Parent route(String path, Routes route) throws IOException {
        FXMLLoader fxmlLoader = ViewRouter.routeScene(path, route);
        return fxmlLoader.load();
    }

    public static FXMLLoader routeScene(String path, Routes route) throws NullPointerException{
        log.info("Routing to= {}", route.route);
        return new FXMLLoader(ViewRouter.class.getResource( path + route.route ));
    }

    public static void loadStage(FXMLLoader fxmlLoader) throws IOException {
        Parent root = fxmlLoader.load();
        Scene scene = new Scene ( root );
        rootStage.setScene ( scene );
    }

    public static FXMLLoader loadRoot(String path, Routes route) {
        log.info("Routing to= {}", route.route);
        return new FXMLLoader(ViewRouter.class.getResource( path + route.route ));
    }

    public static void routeCenterView(String path, Routes route, BorderPane borderPane ) throws IOException {
        log.info("Routing to= {}", route.route);
        FXMLLoader loader = new FXMLLoader( ViewRouter.class.getResource( path + route.route ) );
        borderPane.setCenter( loader.load() );
    }

    public static void loadScene( Class<Object> controller, FXMLLoader fxmlLoader ) throws IOException {
        fxmlLoader.setController( controller );
        Parent root = fxmlLoader.load( );
        Scene scene = new Scene ( root );
        rootStage.setScene ( scene );
    }

}
