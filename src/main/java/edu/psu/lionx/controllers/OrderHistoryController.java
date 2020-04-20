package edu.psu.lionx.controllers;

import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.LionXApplication;
import edu.psu.lionx.domain.*;
import edu.psu.lionx.services.StellarService;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.AlertUtil;
import edu.psu.lionx.utils.ViewRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.KeyPair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    private Logger log = LoggerFactory.getLogger(OrderHistoryController.class);
    private StellarService stellarService;
    private UserService userService;
    private User currentUser;
    private String currentPagingToken;
    public static Transaction currentTransaction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stellarService = new StellarService();
        this.userService = new UserService();
        currentPagingToken = null;
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
        this.transactionTable.setVisible(false);
        this.walletChoiceBox.setVisible(false);
        this.borderPane.setCenter(null);
    }

    private void showAll() {
        log.info("Showing Elements");
        this.transactionTable.setVisible(true);
        this.walletChoiceBox.setVisible(true);
        this.borderPane.setCenter(this.transactionTable);
    }

    private void loadAll() {
        log.info("Loading components");
        this.loadUser();
        this.loadWalletChoiceBox();
        this.buildTable();
    }

    private void buildTable() {
        if (currentUser.getWallets().isEmpty() ) {
            transactionTable.setPlaceholder(new Label("No Asset Balances for Wallet"));
            return;
        }
        try {
            KeyPair pair =
                    KeyPair.fromAccountId ( walletChoiceBox.getSelectionModel().getSelectedItem().getPublicKey() );
            Pair<String, ArrayList<Transaction>> transactions =
                    this.stellarService.getTransactions(pair, currentPagingToken, false);

            this.currentPagingToken = transactions.getKey();
            ObservableList<Transaction> data = FXCollections.observableList(transactions.getValue());
            this.defineTransactionColumns();

            transactionTable.setItems(data);
            transactionTable.setSelectionModel(null);

        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            this.setColWidthProperties();
            transactionTable.setPlaceholder(new Label("Unable to retrieve transactions for wallet"));
        }
    }

    private void setColWidthProperties() {
        assetNameCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.1));
        operationTypeCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.2));
        amountCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.2));
        dateCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.1));
        memoCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.4));
    }

    private void defineTransactionColumns() {
        assetNameCol.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        assetNameCol.getStyleClass().add("table-view-row");

        operationTypeCol.setCellValueFactory(new PropertyValueFactory<>("operationType"));
        operationTypeCol.getStyleClass().add("table-view-row");

        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.getStyleClass().add("table-view-row");

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.getStyleClass().add("table-view-row");

        memoCol.setCellValueFactory(new PropertyValueFactory<>("memo"));
        memoCol.getStyleClass().add("table-view-row");

        this.setColWidthProperties();
    }

    private void loadWalletChoiceBox() {
        ObservableList<Wallet> options = FXCollections.observableArrayList(this.currentUser.getWallets());
        walletChoiceBox.setItems(options);
        walletChoiceBox.getSelectionModel().selectFirst();
    }

    private void addListeners() {
        // Add a listener for when a user selects a new item
        walletChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    walletChoiceBox.getSelectionModel().select(newValue.intValue());
                    this.buildTable();
                });

        // add a listener for when a user clicks on a table row
        transactionTable.setRowFactory( tv -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked( mouseEvent -> {
                if ( !row.isEmpty()
                        && mouseEvent.getButton() == MouseButton.PRIMARY
                        && mouseEvent.getClickCount() == 2 ) {
                    Transaction clickedRow = row.getItem();
                    this.loadTransactionBrowser(clickedRow);
                }
            });
            return row;
        });
    }

    private void loadTransactionBrowser(Transaction transaction) {
        try {
            currentTransaction = transaction;
            ViewRouter.loadDialog(
                    ViewRouter.routeScene(
                            ViewRouter.FXML_DIALOG, ViewRouter.Routes.TRANSACTION_EXPLORER));
        } catch (IOException e) {
            log.error(e.getMessage());
            AlertUtil.pushAlert("Error Loading Dialog",
                    "Unable to load transaction",
                    "If this error continues, please contact the authors",
                    Alert.AlertType.ERROR,
                    null);
        }
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

    @FXML
    public BorderPane borderPane;
    @FXML
    public TableView<Transaction> transactionTable;
    @FXML
    public TableColumn<Transaction, String> assetNameCol;
    @FXML
    public TableColumn<Transaction, String> operationTypeCol;
    @FXML
    public TableColumn<Transaction, String> amountCol;
    @FXML
    public TableColumn<Transaction, String> dateCol;
    @FXML
    public TableColumn<Transaction, String> memoCol;
    @FXML
    public ChoiceBox<Wallet> walletChoiceBox;
}
