package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent home = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            borderPane.setCenter(home);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onMenuButtonClick(ActionEvent actionEvent) {
        // TODO Clean up into a router function
        var source = actionEvent.getSource();

        if ( source == homeButton ) {
            loadPage("/fxml/Home.fxml");
        } else if ( source == settingsButton ) {
            loadPage("/fxml/Settings.fxml");
        } else if ( source == newsButton ) {
            loadPage("/fxml/News.fxml");
        } else if ( source == orderHistoryButton ) {
            loadPage("/fxml/OrderHistory.fxml");
        } else if ( source == walletButton ) {
            loadPage("/fxml/Wallets.fxml");
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
    public JFXButton walletButton;
    @FXML
    public JFXButton orderHistoryButton;
    @FXML
    public JFXButton newsButton;
    @FXML
    public JFXButton settingsButton;
    @FXML
    public VBox settingsBar;
}
