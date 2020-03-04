package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXButton;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
import edu.psu.lionx.utils.ViewRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    private Logger log = LoggerFactory.getLogger(SettingsController.class);
    private UserRepository userRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userRepository = new UserRepository();
    }

    void signOut() {
        Optional<User> user = userRepository.findUserByIsSignedInIsTrue();
        user.ifPresent(User::signOut);
        try {
            ViewRouter.loadStage(ViewRouter.routeScene(ViewRouter.FXML_ROOT, ViewRouter.Routes.LOGIN));
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(-1);
        }
    }

    @FXML
    public void onClick(ActionEvent event) {
        var source = event.getSource();

        if ( source == signOutBtn )
            signOut();
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public JFXButton signOutBtn;

}
