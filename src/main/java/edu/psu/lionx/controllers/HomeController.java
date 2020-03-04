package edu.psu.lionx.controllers;

import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.impl.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private UserRepository userRepository;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userRepository = new UserRepository();

        Optional<User> foundUser = this.userRepository.findUserByIsSignedInIsTrue();
        foundUser.ifPresent(value -> user = value);

        welcomeLabel.setText("Welcome " + user.getEmail());
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public Label welcomeLabel;
}
