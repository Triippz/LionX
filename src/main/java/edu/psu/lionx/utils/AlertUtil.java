package edu.psu.lionx.utils;

import javafx.scene.control.Alert;

public class AlertUtil {

    /**
     * Will "push" an alert of choice to the user.
     * @param title Title of alert
     * @param header Header content of alert
     * @param content Body content of alert
     * @param alertType Type of alert
     * @param operation Operation to perform after awknowledgement
     */
    public static void pushAlert(String title, String header, String content, Alert.AlertType alertType, Runnable operation) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

        if ( operation != null ) {
            operation.run();
        }
    }
}
