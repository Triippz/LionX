package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXButton;
import edu.psu.lionx.utils.ViewRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {


    public VBox settingsBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.HOME, borderPane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onMenuButtonClick(ActionEvent actionEvent) {
        // TODO Clean up into a router function
        var source = actionEvent.getSource();

        try {
            if (source == homeButton) {
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.HOME, borderPane);
            } else if (source == settingsButton) {
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.SETTINGS, borderPane);
            } else if (source == newsButton) {
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.HOME, borderPane);
            } else if (source == orderHistoryButton) {
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.ORDER_HISTORY, borderPane);
            } else if (source == walletsButton) {
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.WALLETS, borderPane);
            } else if (source == aboutButton)
                ViewRouter.routeCenterView(ViewRouter.FXML_ROOT, ViewRouter.Routes.ABOUT, borderPane);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Page!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadPage(String resource) {
        try {
            Parent home = FXMLLoader.load(getClass().getResource( resource ));
            borderPane.setCenter(home);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public JFXButton homeButton;
    @FXML
    public JFXButton walletsButton;
    @FXML
    public JFXButton orderHistoryButton;
    @FXML
    public JFXButton newsButton;
    @FXML
    public JFXButton settingsButton;
    @FXML
    public JFXButton aboutButton;
}
