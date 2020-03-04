package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.utils.ViewRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LoginController implements Initializable {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onClick(ActionEvent event) {
        var source = event.getSource();
        try {
            if ( source == loginButton ) {
                User.authenticateUser(
                        userNameTF.getText().trim(),
                        passwordTF.getText().trim()
                );
                ViewRouter.loadStage(ViewRouter.routeScene(ViewRouter.FXML_ROOT, ViewRouter.Routes.MAIN));
            } else if ( source == noAccountBtn ) {
                ViewRouter.loadStage(ViewRouter.routeScene(ViewRouter.FXML_ROOT, ViewRouter.Routes.REGISTER));
            }
        } catch (NullPointerException | IOException e )
        {
            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (LionxAuthenticationError e2) {
            errorLabel.setVisible(true);
            errorLabel.setText(e2.getMessage());
        }
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public JFXTextField userNameTF;
    @FXML
    public JFXPasswordField passwordTF;
    @FXML
    public Button loginButton;
    @FXML
    public Label errorLabel;
    @FXML
    public Button noAccountBtn;

}
