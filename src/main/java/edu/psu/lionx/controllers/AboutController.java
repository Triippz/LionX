package edu.psu.lionx.controllers;

import edu.psu.lionx.utils.ViewRouter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;


public class AboutController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadWebView();
    }

    private void loadWebView() {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();

        URL url = this.getClass().getResource(String.format("%shtml%sabout.html",
                ViewRouter.FILE_DELIM,
                ViewRouter.FILE_DELIM));
        webEngine.load(url.toString());

        borderPane.setCenter(browser);

    }

    @FXML
    public BorderPane borderPane;
}
