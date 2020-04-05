package edu.psu.lionx.controllers.dialogs;

import edu.psu.lionx.controllers.OrderHistoryController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionExplorerController implements Initializable {

    private Logger log = LoggerFactory.getLogger(TransactionExplorerController.class);
    private String STELLAR_CHAIN_TEST_NET = "https://stellar.expert/explorer/testnet/tx/%s";
    private String STELLAR_CHAIN_MAIN_NET = "https://stellarchain.io/tx/%s";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadWebView();
    }

    private void loadWebView() {
        Platform.runLater(() -> {
            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();

            String url = String.format(
                    STELLAR_CHAIN_TEST_NET,
                    OrderHistoryController.currentTransaction.getTransactionHash());
            webEngine.load(url);
            borderPane.setCenter(browser);
        });
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onClick(ActionEvent event) {
        this.closeStage(event);
    }

    @FXML
    public Button finishedButton;
    @FXML
    public BorderPane borderPane;
}
