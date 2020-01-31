package edu.psu.lionx.utils;

import edu.psu.lionx.LionXApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;

public class ViewRouter {

    public static final String FILE_DELIM = File.separator;
    public static final String FXML_ROOT = FILE_DELIM + "fxml" + FILE_DELIM;
    public static final String FXML_DIALOG = FILE_DELIM + "fxml" + FILE_DELIM + "dialogs" + FILE_DELIM;

    public enum Routes {
        MAIN("Main.fxml"),
        HOME("Home.fxml"),
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

    public static FXMLLoader loadRoot(String path, Routes route) {
        return new FXMLLoader(ViewRouter.class.getResource( path + route.route ));
    }

    public static void routeCenterView(String path, Routes route, BorderPane borderPane ) throws IOException {
        FXMLLoader loader = new FXMLLoader( ViewRouter.class.getResource( path + route.route ) );
        borderPane.setCenter( loader.load() );
    }

    public static void loadScene( Class<Object> controller, FXMLLoader fxmlLoader ) throws IOException {
        fxmlLoader.setController( controller );
        Parent root = fxmlLoader.load( );
        Scene scene = new Scene ( root );
        LionXApplication.rootStage.setScene ( scene );
    }

}
