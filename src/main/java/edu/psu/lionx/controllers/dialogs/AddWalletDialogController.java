package edu.psu.lionx.controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.LionXApplication;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.domain.Wallet;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.AlertUtil;
import edu.psu.lionx.utils.ViewRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWalletDialogController implements Initializable {
    private Logger log = LoggerFactory.getLogger(AddWalletDialogController.class);
    private UserService userService;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = new UserService();
        this.loadUser();
    }

    public void onSubmit(ActionEvent event) {
        if ( formValid() )
            this.closeStage(event);
    }

    @SuppressWarnings("Duplicates")
    private void loadUser() {
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
                        () -> {
                            System.exit(-1);
                        });
            }
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean formValid() {
        String walletName = walletNameTF.getText().trim();

        if ( walletName.isEmpty() ) {
            errorLabel.setText("Wallet name cannot be blank");
            errorLabel.setVisible(true);
            return false;
        }
        if ( walletName.length() > 25 ) {
            errorLabel.setText("Wallet name cannot be longer than 25 characters");
            errorLabel.setVisible(true);
            return false;
        }

        Wallet wallet = new Wallet();
        wallet.setWalletName(walletName);
        try {
            wallet.save();
            currentUser.addWallet(wallet);
            currentUser.save();
        } catch (IOException e) {
            log.error(e.getMessage());
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public Label errorLabel;
    @FXML
    public JFXTextField walletNameTF;
    @FXML
    public JFXButton createWalletBtn;
}
