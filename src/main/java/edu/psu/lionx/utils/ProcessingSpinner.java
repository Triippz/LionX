package edu.psu.lionx.utils;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

public class ProcessingSpinner {
    private Task worker;

    public static void processingSpinner(Task worker, BorderPane borderPane) {
        ProcessingSpinner spinner = new ProcessingSpinner();
        spinner.worker = worker;

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(50, 50);
        borderPane.setCenter(progressIndicator);

        new Thread(worker).start();
    }
}
