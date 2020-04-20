package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.LionXApplication;
import edu.psu.lionx.domain.*;
import edu.psu.lionx.services.StellarService;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.AlertUtil;
import edu.psu.lionx.utils.ViewRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.KeyPair;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Mark Tripoli
 */
public class WalletsController implements Initializable {

    private Logger log = LoggerFactory.getLogger(WalletsController.class);
    private UserService userService;
    private StellarService stellarService;
    public static Wallet currentWallet;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = new UserService();
        this.stellarService = new StellarService();
        this.loadPage();
    }

    private void loadPage() {
        log.info("Loading Page...");
        this.hideAll();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(50, 50);
        this.borderPane.setCenter(progressIndicator);
        loadAll();
        showAll();
        this.addListeners();
    }

    private void hideAll() {
        log.info("Hiding Elements");
        this.titleHBox.setVisible(false);
        this.rightVBox.setVisible(false);
        this.centerVBox.setVisible(false);
        this.borderPane.setCenter(null);
    }

    private void showAll() {
        log.info("Showing Elements");
        this.titleHBox.setVisible(true);
        this.rightVBox.setVisible(true);
        this.centerVBox.setVisible(true);
        this.borderPane.setCenter(this.centerVBox);
    }

    private void loadAll() {
        log.info("Loading components");
        this.loadUser();
        this.loadWalletChoiceBox();
        this.loadWalletTextFields();
        this.loadAssetTable();
        this.loadTransactionTable();
    }

    private void loadUser() {
        log.info("Loading User");
        try {
            this.currentUser = userService.getLoggedInUser();
        } catch (LionXUserNotFoundException e) {
            log.warn(e.getMessage());
            try {
                LionXApplication.openStage(LionXApplication.rootStage, ViewRouter.Routes.REGISTER);
            } catch ( IOException e2 ) {
                log.error(e.getMessage());
                AlertUtil.pushAlert("Error Loading Page",
                        "We encountered an error loading a page",
                        "If this problem persists, please contact the authors",
                        Alert.AlertType.ERROR,
                        () -> System.exit(-1));
            }
        }
    }

    private void loadWalletChoiceBox() {
        ObservableList<Wallet> options = FXCollections.observableArrayList(this.currentUser.getWallets());
        walletChoiceBox.setItems(options);
        walletChoiceBox.getSelectionModel().selectFirst();
    }

    private void loadWalletTextFields() {
        String privateKey = this.walletChoiceBox.getSelectionModel().getSelectedItem() != null
                ? this.walletChoiceBox.getSelectionModel().getSelectedItem().getPrivateKey()
                : "None";
        String publicKey = this.walletChoiceBox.getSelectionModel().getSelectedItem() != null
                ? this.walletChoiceBox.getSelectionModel().getSelectedItem().getPublicKey()
                : "None";
        this.privKeyTF.setText(privateKey);
        this.pubKeyTF.setText(publicKey);
    }

    private void loadAssetTable() {
        if (currentUser.getWallets().isEmpty() ) {
            assetsTable.setPlaceholder(new Label("No Asset Balances for Wallet"));
            return;
        }
        try {
            StellarAsset[] stellarAssets = stellarService.getAllBalances(
                    this.walletChoiceBox.getSelectionModel().getSelectedItem(),
                    false
            );

            List<AssetPositionTableModel> assetPositions = new ArrayList<>();
            for ( StellarAsset asset : stellarAssets ) {
                String imageUrl = getClass().getClassLoader().getResource("images/xlm.png") != null
                        ? getClass().getClassLoader().getResource("images/xlm.png").toString()
                        : "https://raw.githubusercontent.com/atomiclabs/cryptocurrency-icons/master/32%402x/color/xlm%402x.png";
                assetPositions.add(
                        AssetPositionTableModel.create(
                                asset.getAssetName(),
                                asset.getAssetBalance(),
                                imageUrl,
                                "100%"
                        )
                );
            }

            ObservableList<AssetPositionTableModel> data = FXCollections.observableList(assetPositions);
            this.defineAssetColumns();

            assetsTable.setItems(data);
            assetsTable.setSelectionModel(null);

        } catch (IOException e) {
            log.error(e.getMessage());
            assetsTable.setPlaceholder(new Label("Unable to retrieve balances for wallet"));
            this.setColWidProperties();
        }
    }

    private void defineAssetColumns() {
        assetImgCol.setCellValueFactory(new PropertyValueFactory<>("assetImage"));
        assetImgCol.getStyleClass().add("table-view-row");

        assetNameCol.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        assetNameCol.getStyleClass().add("table-view-row");

        balanceCol.setCellValueFactory(new PropertyValueFactory<>("assetBalance"));
        balanceCol.getStyleClass().add("table-view-row");

        allocationCol.setCellValueFactory(new PropertyValueFactory<>("assetAllocation"));
        allocationCol.getStyleClass().add("table-view-row");

        this.setColWidProperties();
    }

    private void setColWidProperties() {
        assetImgCol.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.25));
        assetNameCol.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.25));
        balanceCol.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.25));
        allocationCol.prefWidthProperty().bind(assetsTable.widthProperty().multiply(0.25));
    }

    private void loadTransactionTable() {
        if (currentUser.getWallets().isEmpty() ) {
            recentTxTable.setPlaceholder(new Label("No recent transactions"));
            return;
        }
        try {
            KeyPair pair =
                    KeyPair.fromAccountId(walletChoiceBox.getSelectionModel().getSelectedItem().getPublicKey());
            Pair<String, ArrayList<Transaction>> transactions =
                    this.stellarService.getTransactions(pair, null, false);
            List<Transaction.RecentTx> recentTxes = new ArrayList<>();

            for ( Transaction tx : transactions.getValue() )
                recentTxes.add(
                        new Transaction.RecentTx(
                                tx.getOperationType(),
                                tx.getAmount()
                        )
                );

            ObservableList<Transaction.RecentTx> data = FXCollections.observableList(recentTxes);
            recentTxCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            recentTxCol.prefWidthProperty().bind(recentTxTable.widthProperty().multiply(1));
            recentTxCol.getStyleClass().add("table-view-row");

            recentTxTable.setItems(data);
            recentTxTable.setSelectionModel(null);
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            recentTxTable.setPlaceholder(new Label("Unable to load recent transactions"));
        }
    }


    private void addListeners() {
        walletChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    walletChoiceBox.getSelectionModel().select(newValue.intValue());
                    this.loadWalletTextFields();
                    this.loadTransactionTable();
                    this.loadAssetTable();
                });
    }

    @FXML
    public void onClick(ActionEvent event) {
        try {
            ViewRouter.loadDialog( ViewRouter.routeScene(ViewRouter.FXML_DIALOG, ViewRouter.Routes.ADD_WALLET));
        } catch (IOException e) {
            log.error(e.getMessage());
            AlertUtil.pushAlert("Error Loading Dialog",
                    "Unable to open Dialog",
                    "If this error continues, please contact the authors",
                    Alert.AlertType.ERROR,
                    null);
        }

        loadUser();
        this.walletChoiceBox.getItems().clear();
        this.loadWalletChoiceBox();
    }

    @FXML
    public void onSendClick(ActionEvent event) {
        currentWallet = this.walletChoiceBox.getSelectionModel().getSelectedItem();

        try {
            ViewRouter.loadDialog( ViewRouter.routeScene(ViewRouter.FXML_DIALOG, ViewRouter.Routes.SEND_TX));
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("Unable to load dialog, please try again");
        }

        loadUser();
        this.loadTransactionTable();
        this.loadAssetTable();
    }

    @FXML
    public void onMouseClick(MouseEvent mouseEvent) {
        var source = mouseEvent.getSource();

        if ( source == privKeyTF ) {
            StringSelection ss = new StringSelection(privKeyTF.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        } else if ( source == pubKeyTF ) {
            StringSelection ss = new StringSelection(pubKeyTF.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        }
    }

    @FXML
    public VBox contentArea;
    @FXML
    public Button sendFundsBtn;
    @FXML
    public BorderPane borderPane;
    @FXML
    public ChoiceBox<Wallet> walletChoiceBox;
    @FXML
    public Button createWalletButton;
    @FXML
    public TableView<Transaction.RecentTx> recentTxTable;
    @FXML
    public TableColumn<Transaction.RecentTx, String> recentTxCol;
    @FXML
    public JFXTextField pubKeyTF;
    @FXML
    public JFXTextField privKeyTF;
    @FXML
    public TableView<AssetPositionTableModel> assetsTable;
    @FXML
    public TableColumn<AssetPositionTableModel, ImageView> assetImgCol;
    @FXML
    public TableColumn<AssetPositionTableModel, String> assetNameCol;
    @FXML
    public TableColumn<AssetPositionTableModel, String> balanceCol;
    @FXML
    public TableColumn<AssetPositionTableModel, String> allocationCol;
    @FXML
    public VBox centerVBox;
    @FXML
    public HBox titleHBox;
    @FXML
    public VBox rightVBox;
}
