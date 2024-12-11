package com.hamNews.Views;

import org.jsoup.Jsoup;

import com.hamNews.Model.Article.ArticleContent;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.Article.Category;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticleDetailView extends Application {

    private ArticleSelect article;
    private ArticleContent content;
    private Category category;

    private HBox articlesContainer = new HBox(25);
    private int currentIndex = 0;
    private static final int ARTICLES_PER_ROW = 3;
    private ScrollPane scrollPane;

    public ArticleDetailView(ArticleSelect article) {
        this.article = article;
    }

    @Override
    public void start(Stage primaryStage) {
    }

    private String htmlToPlainText(String html) {
        return Jsoup.parse(html).text();
    }

    private void displayArticles() {

        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setPadding(new Insets(20));
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 10;");

        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: white; -fx-border-width: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0);");
        card.setPrefWidth(1000);

        // ImageView imageView = new ImageView(new Image(article.getImageUrl()));
        Image icon = new Image(
                getClass().getResource("/com/hamNews/Views/images/" + article.getImageUrl()).toExternalForm());
        ImageView imageView = new ImageView(icon);
        // connectImageView.setFitWidth(150);
        // connectImageView.setFitHeight(100);
        // connectImageView.setPreserveRatio(true);
        imageView.setFitWidth(500);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        HBox titleWithRemove = new HBox(10);
        Label title = new Label(article.getTitle());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);

        Button removeButton = new Button();
        removeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
        removeButton.setMinSize(30, 30);
        Image Icon = new Image(getClass().getResource("/com/hamNews/Views/images/Return.png").toExternalForm());
        ImageView removeIcon = new ImageView(Icon);
        removeIcon.setFitHeight(12);
        removeIcon.setFitWidth(12);
        removeButton.setGraphic(removeIcon);

        titleWithRemove.getChildren().addAll(title, removeButton);

        HBox categoryDate = new HBox(5);
        Label categoryLabel = new Label(article.getCategorie());
        categoryLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        Label date = new Label(article.getPublishDate());
        date.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        categoryDate.getChildren().addAll(categoryLabel, date);

        Label snippet = new Label(article.getDescription());
        snippet.setWrapText(true);
        snippet.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        VBox titleSection = new VBox(10);
        titleSection.getChildren().addAll(titleWithRemove, categoryDate, snippet);

        HBox imageTitleSection = new HBox(20);
        imageTitleSection.getChildren().addAll(imageView, titleSection);

        Label contenu = new Label(htmlToPlainText(article.getContent()));
        contenu.setWrapText(true);
        contenu.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        removeButton.setOnAction(e -> {
            System.out.println("Remove: " + article.getTitle());
            ArticleView.openListFormWithAnimation();
        });
        card.getChildren().addAll(imageTitleSection, contenu);
        articlesContainer.getChildren().add(card);

    }

    public ScrollPane ShowArticle() {

        displayArticles();

        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-padding: 0;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        return scrollPane;

    }

}
