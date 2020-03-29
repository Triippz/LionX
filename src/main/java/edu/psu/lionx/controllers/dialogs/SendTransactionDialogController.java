package edu.psu.lionx.controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.controllers.WalletsController;
import edu.psu.lionx.domain.StellarAsset;
import edu.psu.lionx.domain.Wallet;
import edu.psu.lionx.services.StellarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * @author Mark Tripoli
 */
public class SendTransactionDialogController implements Initializable {

    private Wallet currentWallet;
    private StellarService stellarService;
    private Logger log = LoggerFactory.getLogger(SendTransactionDialogController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stellarService = new StellarService();
        this.currentWallet = WalletsController.currentWallet;
        this.initTextField();
        this.addFieldListener();
        this.initChoiceBox();
    }

    private boolean formValid() {
        if ( ! validatePubKey() )
            return false;

        String destKey = destTF.getText().trim();
        String amount = amountTF.getText();
        String assetName = assetChoiceBox.getSelectionModel().getSelectedItem().getAssetName();
        String memo = memoTF.getText().trim();

        // Only support native TX right now
        try {
            SubmitTransactionResponse submitTransactionResponse
                    = stellarService.sendPayment(false, currentWallet, destKey, amount, memo);
            if ( submitTransactionResponse.isSuccess() ) {
                log.info(submitTransactionResponse.getDecodedTransactionResult().toString());
                return true;
            }

            errorLabel.setText("Error Sending Transaction: " + submitTransactionResponse.getDecodedTransactionResult().toString());
            errorLabel.setVisible(true);
            log.error(submitTransactionResponse.getDecodedTransactionResult().toString());
            return false;
        } catch (IOException e) {
            errorLabel.setText("Error Sending Transaction: " + e.getMessage());
            errorLabel.setVisible(true);
            return false;
        }
    }

    private boolean validatePubKey() {
        String publicKey = destTF.getText().trim();
        if ( publicKey.isEmpty() )
        {
            errorLabel.setText("Must enter a destination address");
            errorLabel.setVisible(true);
            return false;
        }

        if ( !stellarService.publicKeyExists(publicKey) )
        {
            errorLabel.setText("Please enter a valid Stellar Public Key");
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    private void initChoiceBox() {
        try {
            ArrayList<StellarAsset> allBalancesArrayList
                    = stellarService.getAllBalancesArrayList(currentWallet, false);
            ObservableList<StellarAsset> options = FXCollections.observableArrayList(allBalancesArrayList);
            assetChoiceBox.setItems(options);
            assetChoiceBox.getSelectionModel().selectFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTextField() {
        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,8})?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        TextFormatter<BigDecimal> formatter = new TextFormatter<>(filter);

        this.amountTF.setTextFormatter(formatter);
    }

    private void addFieldListener() {
        amountTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( !newValue.isEmpty() )
                this.checkBalance(newValue, oldValue);
        });

        memoTF.textProperty().addListener((ov, oldValue, newValue) -> {
            if (memoTF.getText().length() > 28) {
                String s = memoTF.getText().substring(0, 28);
                memoTF.setText(s);
            }
        });
    }


    private void checkBalance(String newValue, String oldValue) {
        BigDecimal enteredValue = new BigDecimal(newValue);
        BigDecimal currentValue =
                new BigDecimal(this.assetChoiceBox.getSelectionModel().getSelectedItem().getAssetBalance());

        if (enteredValue.compareTo(currentValue) > 0)
            amountTF.setText(oldValue);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onSubmit(ActionEvent event) {
        var source = event.getSource();

        if ( source == this.sendFundsBtn ) {
            if ( formValid() )
                this.closeStage(event);
        } else {
            this.closeStage(event);
        }
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public JFXTextField memoTF;
    @FXML
    public ChoiceBox<StellarAsset> assetChoiceBox;
    @FXML
    public JFXTextField destTF;
    @FXML
    public JFXTextField amountTF;
    @FXML
    public JFXButton sendFundsBtn;
    @FXML
    public JFXButton cancelBtn;
    @FXML
    public Label errorLabel;
}
