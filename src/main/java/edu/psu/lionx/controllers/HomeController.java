package edu.psu.lionx.controllers;

import edu.psu.lionx.Exceptions.LionXUserNotFoundException;
import edu.psu.lionx.LionXApplication;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
import edu.psu.lionx.services.UserService;
import edu.psu.lionx.utils.AlertUtil;
import edu.psu.lionx.utils.ViewRouter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Logger log = LoggerFactory.getLogger(OrderHistoryController.class);
    private UserService userService;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = new UserService();

        this.loadUser();
        welcomeLabel.setText("Welcome " + currentUser.getEmail());
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
}
