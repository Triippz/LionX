package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.Exceptions.LionXConstraintException;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
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
import java.util.regex.Pattern;

import static edu.psu.lionx.config.Constants.EMAIL_REGEX;

public class RegisterController implements Initializable {

    private Logger log = LoggerFactory.getLogger(RegisterController.class);
    private UserRepository userRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userRepository = new UserRepository();
    }

    @FXML
    public void onClick(ActionEvent event) {
        var source = event.getSource();
        try {
            if ( source == registerButton ) {
                if ( validForm() )
                    ViewRouter.loadStage(ViewRouter.routeScene(ViewRouter.FXML_ROOT, ViewRouter.Routes.MAIN));
            } else if ( source == loginRouteButton ) {
                ViewRouter.loadStage(ViewRouter.routeScene(ViewRouter.FXML_ROOT, ViewRouter.Routes.LOGIN));
            }
        } catch (NullPointerException | IOException e )
        {
            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private boolean validForm() {
        clearError();

        User user = new User();
        String userName = userNameTF.getText().trim();
        String password1 = password1TF.getText().trim();
        String password2 = password2TF.getText().trim();
        String email = emailTF.getText().trim();

        /* Validate the Username */
        if ( userName.equals("") ) {
            errorLabel.setVisible(true);
            errorLabel.setText("Username must be filled out!");
            return false;
        }
        user.setLogin( userName );

        /* Validate the password(s) */
        if ( password1.equals("") || password2.equals("") ) {
            errorLabel.setVisible(true);
            errorLabel.setText("You must enter a password!");
            return false;
        }

        if ( !password1.equals(password2) ) {
            errorLabel.setVisible(true);
            errorLabel.setText("Passwords Do Not Match!");
            return false;
        }
        user.setPassword(password1);

        /* Validate the email */
        Pattern pat = Pattern.compile(EMAIL_REGEX);
        if ( !pat.matcher(email).matches() ) {
            errorLabel.setVisible(true);
            errorLabel.setText("Must enter a valid email address!");
            return false;
        }
        user.setEmail(email);
        user.setSignedIn(true);

        /* Everything is valid, lets try to add the user */
        try {
            user.save();
        } catch ( LionXConstraintException  e ) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
            return false;
        }

        return true;
    }

    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public JFXTextField userNameTF;
    @FXML
    public JFXPasswordField password1TF;
    @FXML
    public JFXPasswordField password2TF;
    @FXML
    public JFXTextField emailTF;
    @FXML
    public Button registerButton;
    @FXML
    public Button loginRouteButton;
    @FXML
    public Label errorLabel;

}
