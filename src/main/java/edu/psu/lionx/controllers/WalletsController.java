package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.domain.StellarAsset;
import edu.psu.lionx.domain.Transaction;
import edu.psu.lionx.domain.Wallet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class WalletsController implements Initializable {

    private Logger log = LoggerFactory.getLogger(WalletsController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onClick(ActionEvent event) {
    }

    @FXML
    public VBox contentArea;
    @FXML
    public BorderPane borderPane;
    @FXML
    public ChoiceBox<Wallet> walletChoiceBox;
    @FXML
    public Button createWalletButton;
    @FXML
    public TableView<Transaction> recentTxTable;
    @FXML
    public TableColumn<Transaction, String> recentTxCol;
    @FXML
    public JFXTextField pubKeyTF;
    @FXML
    public JFXTextField privKeyTF;
    @FXML
    public TableView<StellarAsset> assetsTable;
    @FXML
    public TableColumn<StellarAsset, ImageView> assetImgCol;
    @FXML
    public TableColumn<StellarAsset, String> assetNameCol;
    @FXML
    public TableColumn<StellarAsset, BigDecimal> balanceCol;
    @FXML
    public TableColumn<StellarAsset, String> allocationCol;
    @FXML
    public TableColumn<StellarAsset, Void> sendTxCol;
}
