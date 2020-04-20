package edu.psu.lionx.controllers;

import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.LionXApplication;
import edu.psu.lionx.domain.Order;
import edu.psu.lionx.domain.Transaction;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.services.StellarService;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.AlertUtil;
import edu.psu.lionx.utils.ViewRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.Network;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class HomeController implements Initializable {
    private Logger log = LoggerFactory.getLogger(OrderHistoryController.class);
    private UserService userService;
    private StellarService stellarService;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = new UserService();
        this.stellarService = new StellarService();

        this.loadUser();
        welcomeLabel.setText("Welcome " + currentUser.getEmail());

        this.buildOrderService();
        this.buildTransactionService();
    }

    private void buildTransactionTable(ObservableList<Transaction> txList) {
        TableColumn<Transaction, String> srcCol = new TableColumn<>("Source Account");
        srcCol.setCellValueFactory(new PropertyValueFactory<>("sourceAccount"));
        srcCol.prefWidthProperty().bind(transactionsTable.widthProperty().multiply(0.4));
        srcCol.getStyleClass().add("table-view-row");

        TableColumn<Transaction, Network> networkCol = new TableColumn<>("Network");
        networkCol.setCellValueFactory(new PropertyValueFactory<>("network"));
        networkCol.prefWidthProperty().bind(transactionsTable.widthProperty().multiply(0.3));
        networkCol.getStyleClass().add("table-view-row");

        TableColumn<Transaction, Integer> operationCol = new TableColumn<>("Operations");
        operationCol.setCellValueFactory(new PropertyValueFactory<>("operationCount"));
        operationCol.prefWidthProperty().bind(transactionsTable.widthProperty().multiply(0.1));
        operationCol.getStyleClass().add("table-view-row");

        TableColumn<Transaction, String> memoCol = new TableColumn<>("Memo");
        memoCol.setCellValueFactory(new PropertyValueFactory<>("memo"));
        memoCol.prefWidthProperty().bind(transactionsTable.widthProperty().multiply(0.2));
        memoCol.getStyleClass().add("table-view-row");

        this.transactionsTable.getColumns().add(srcCol);
        this.transactionsTable.getColumns().add(networkCol);
        this.transactionsTable.getColumns().add(operationCol);
        this.transactionsTable.getColumns().add(memoCol);

        this.transactionsTable.setItems(txList);
        this.transactionsTable.setSelectionModel(null);
        this.transactionsTable.setPlaceholder(new Label("Waiting on transactions..."));
    }

    private void buildOrderTable(ObservableList<Order> orderList) {
        TableColumn<Order, String> baseAssetCodeCol = new TableColumn<>("Base Asset");
        baseAssetCodeCol.setCellValueFactory(new PropertyValueFactory<>("baseAssetCode"));
        baseAssetCodeCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.1));
        baseAssetCodeCol.getStyleClass().add("table-view-row");

        TableColumn<Order, Network> baseAmountCol = new TableColumn<>("Amount");
        baseAmountCol.setCellValueFactory(new PropertyValueFactory<>("baseAmount"));
        baseAmountCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.1));
        baseAmountCol.getStyleClass().add("table-view-row");

        TableColumn<Order, Integer> baseAccountCol = new TableColumn<>("Base Account");
        baseAccountCol.setCellValueFactory(new PropertyValueFactory<>("baseAccount"));
        baseAccountCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.2));
        baseAccountCol.getStyleClass().add("table-view-row");

        TableColumn<Order, String> counterAssetCodeCol = new TableColumn<>("Counter Asset");
        counterAssetCodeCol.setCellValueFactory(new PropertyValueFactory<>("counterAssetCode"));
        counterAssetCodeCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.1));
        counterAssetCodeCol.getStyleClass().add("table-view-row");

        TableColumn<Order, String> counterAmountCol = new TableColumn<>("Counter Amount");
        counterAmountCol.setCellValueFactory(new PropertyValueFactory<>("counterAmount"));
        counterAmountCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.1));
        counterAmountCol.getStyleClass().add("table-view-row");

        TableColumn<Order, String> counterAcountCol = new TableColumn<>("Counter Account");
        counterAcountCol.setCellValueFactory(new PropertyValueFactory<>("counterAcount"));
        counterAcountCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.2));
        counterAcountCol.getStyleClass().add("table-view-row");

        TableColumn<Order, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.prefWidthProperty().bind(ordersTable.widthProperty().multiply(0.2));
        priceCol.getStyleClass().add("table-view-row");

        this.ordersTable.getColumns().add(baseAssetCodeCol);
        this.ordersTable.getColumns().add(baseAmountCol);
        this.ordersTable.getColumns().add(baseAccountCol);
        this.ordersTable.getColumns().add(counterAssetCodeCol);
        this.ordersTable.getColumns().add(counterAmountCol);
        this.ordersTable.getColumns().add(counterAcountCol);
        this.ordersTable.getColumns().add(priceCol);

        this.ordersTable.setItems(orderList);
        this.ordersTable.setSelectionModel(null);
        this.ordersTable.setPlaceholder(new Label("Waiting on orders..."));
    }

    private void buildTransactionService() {
        ObservableList<Transaction> txList = FXCollections.observableArrayList();
        this.buildTransactionTable(txList);
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try {
                                stellarService.getCurrentTransactions(
                                        false,
                                        txList);
                            } finally {
                                latch.countDown();
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    private void buildOrderService() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        this.buildOrderTable(orderList);
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try {
                                stellarService.getCurrentOrders(false, orderList);
                            } finally {
                                latch.countDown();
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();
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
    public Label welcomeLabel;
    @FXML
    public TableView<Order> ordersTable;
    @FXML
    public TableView<Transaction> transactionsTable;
}
