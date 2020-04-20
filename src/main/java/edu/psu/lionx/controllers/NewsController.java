package edu.psu.lionx.controllers;

import com.jfoenix.controls.JFXTextField;
import edu.psu.lionx.domain.gson.Article;
import edu.psu.lionx.services.NewsService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewsController implements Initializable {
    private org.slf4j.Logger log = LoggerFactory.getLogger(MainController.class);
    private NewsService newsService;
    private String queryTerm = "Stellar";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadNewsService();
        this.loadNewsTable();
    }

    private void loadNewsTable() {
        List<Article> articles = new ArrayList<>();
        try {
            articles = this.loadArticles();
        } catch (IOException e) {
            log.error(e.getMessage());
            this.newsTable.setPlaceholder(new Label("Unable to load news"));
        }

        for ( Article article : articles )
            article.buildImage();

        TableColumn<Article, ImageView> imageCol = new TableColumn<>("Images");
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageCol.prefWidthProperty().bind(newsTable.widthProperty().multiply(0.1));
        imageCol.getStyleClass().add("table-view-row");

        TableColumn<Article, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellFactory(tc -> {
            TableCell<Article, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(titleCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            cell.setTextFill(Color.WHITE);
            return cell ;
        });
        titleCol.prefWidthProperty().bind(newsTable.widthProperty().multiply(0.2));
        titleCol.getStyleClass().add("table-view-row");

        TableColumn<Article, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setCellFactory(tc -> {
            TableCell<Article, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(descriptionCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            cell.setTextFill(Color.WHITE);
            return cell ;
        });
        descriptionCol.prefWidthProperty().bind(newsTable.widthProperty().multiply(0.7));
        descriptionCol.getStyleClass().add("table-view-row");

        this.newsTable.getColumns().add(imageCol);
        this.newsTable.getColumns().add(titleCol);
        this.newsTable.getColumns().add(descriptionCol);

        this.newsTable.setItems(FXCollections.observableList(articles));
        this.newsTable.setSelectionModel(null);
//        this.newsTable.getStyleClass().add("noheader");
    }

    private List<Article> loadArticles() throws IOException {
        NewsService.NewsSearchBuilder builder = new NewsService.NewsSearchBuilder();
        builder.query(queryTerm);
        return this.newsService.getNews(builder.build());
    }

    private void loadNewsService() {
        try {
            this.newsService = new NewsService();
        } catch (Exception e) {
            log.error(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error News Service!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public BorderPane borderPane;
    @FXML
    public TableView<Article> newsTable;
    @FXML
    public JFXTextField searchBox;

    public void onEnter(ActionEvent event) {
        this.queryTerm = this.searchBox.getText().trim().replaceAll(" ", "_");
        this.newsTable.getItems().clear();
        this.newsTable.getColumns().clear();
        this.loadNewsTable();
    }
}
