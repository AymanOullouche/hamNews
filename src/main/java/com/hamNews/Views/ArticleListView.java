package com.hamNews.Views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hamNews.Controler.ArticleController;
import com.hamNews.Controler.DownloadController;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.DB.Session;
import com.hamNews.Model.User.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticleListView extends Application {
    private static final int ARTICLES_PER_ROW = 6;
    private static DownloadController downloadController = new DownloadController();
    private static List<ArticleSelect> articles = new ArrayList<>();
    private static HBox articlesContainer = new HBox(20);
    private static int currentIndex = 0;
    private static ScrollPane scrollPane;
    private static User theUser;
    private static VBox mainContainer;
    private static HBox paginationContainer;
    private static Button nextButton, previousButton;

    @Override
    public void start(Stage primaryStage) {
        // Initialization logic (if required)
    }

    public static void displayArticles() {
        theUser = Session.getLoggedInUser();
        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");
        articlesContainer.getChildren().clear();

        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, articles.size()); i++) {
            ArticleSelect article = articles.get(i);
            articlesContainer.getChildren().add(createArticleCard(article));
        }
    }

    private static VBox createArticleCard(ArticleSelect article) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: white; -fx-border-width: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0);");
        card.setPrefWidth(100);
        card.setPrefHeight(360);

        Image image = loadImage(article.getImageName());
        ImageView imageView = new ImageView(image);
        imageView.resize(300, 212);
        imageView.setFitWidth(232);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);

        Label title = new Label(article.getTitle());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setMaxWidth(232);
        title.setMaxHeight(80);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);

        Label snippet = new Label(article.getDescription());
        snippet.setWrapText(true);
        snippet.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
        snippet.setMaxWidth(232);
        snippet.setMaxHeight(60);
        snippet.setStyle("-fx-text-overrun: ellipsis; -fx-wrap-text: true; -fx-text-fill: #666;");

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER);

        Button downloadButton = createActionButton("/com/hamNews/Views/images/Download.png",
                e -> downloadArticle(article));
        Button readMoreButton = createActionButton("Read More", e -> openArticleDetail(article));

        actions.getChildren().addAll(downloadButton, readMoreButton);
        actions.setStyle("-fx-spacing: 15; -fx-alignment: center;");

        Region space1 = new Region();
        Region space2 = new Region();
        VBox.setVgrow(space1, Priority.ALWAYS);
        VBox.setVgrow(space2, Priority.ALWAYS);

        card.getChildren().addAll(imageView, title, space1, snippet, space2, actions);
        return card;
    }

    private static Button createActionButton(String textOrImagePath,
            javafx.event.EventHandler<javafx.event.ActionEvent> actionHandler) {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
        button.setMinSize(100, 40);

        if (textOrImagePath.endsWith(".png")) {
            Image icon = new Image(ArticleListView.class.getResource(textOrImagePath).toExternalForm());
            ImageView iconView = new ImageView(icon);
            iconView.setFitHeight(20);
            iconView.setFitWidth(20);
            button.setGraphic(iconView);
        } else {
            button.setText(textOrImagePath);
        }

        button.setOnAction(actionHandler);
        return button;
    }

    private static Image loadImage(String imageUrl) {
        URL resourceUrl = ArticleListView.class.getResource("/com/hamNews/Views/images/" + imageUrl);
        if (resourceUrl == null) {
            System.out.println("Image not found: " + imageUrl);
            return null; // Or handle the error accordingly
        }
        return new Image(resourceUrl.toExternalForm());
    }

    public VBox ShowArticle() {
        loadArticles();
        setupScrollPane();
        setupPaginationButtons();

        mainContainer = new VBox(10);
        mainContainer.getChildren().addAll(scrollPane, paginationContainer);
        mainContainer.setAlignment(Pos.CENTER);

        return mainContainer;
    }

    private void setupScrollPane() {
        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-padding: 0;");
        scrollPane.setOnScroll(this::handleScroll);
        scrollPane.setOnKeyPressed(this::handleKeyPress);
    }

    private void setupPaginationButtons() {
        nextButton = createPaginationButton("Next", e -> {
            if (currentIndex + ARTICLES_PER_ROW < articles.size()) {
                currentIndex += ARTICLES_PER_ROW;
                displayArticles();
                updatePaginationButtons();
            }
        });

        previousButton = createPaginationButton("Previous", e -> {
            if (currentIndex - ARTICLES_PER_ROW >= 0) {
                currentIndex -= ARTICLES_PER_ROW;
                displayArticles();
                updatePaginationButtons();
            }
        });

        paginationContainer = new HBox(10);
        paginationContainer.getChildren().addAll(previousButton, nextButton);
        paginationContainer.setPadding(new Insets(10));
    }

    private Button createPaginationButton(String text,
            javafx.event.EventHandler<javafx.event.ActionEvent> actionHandler) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #54AEFF; -fx-border-color: transparent; -fx-cursor: hand; -fx-text-fill: white;");
        button.setMinSize(60, 30);
        button.setOnAction(actionHandler);
        return button;
    }

    private void updatePaginationButtons() {
        previousButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex + ARTICLES_PER_ROW >= articles.size());
    }

    private void handleScroll(ScrollEvent event) {
        if (event.getDeltaY() < 0 && currentIndex + ARTICLES_PER_ROW < articles.size()) {
            currentIndex += ARTICLES_PER_ROW;
            displayArticles();
        } else if (event.getDeltaY() > 0 && currentIndex - ARTICLES_PER_ROW >= 0) {
            currentIndex -= ARTICLES_PER_ROW;
            displayArticles();
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && currentIndex + ARTICLES_PER_ROW < articles.size()) {
            currentIndex += ARTICLES_PER_ROW;
            displayArticles();
        } else if (event.getCode() == KeyCode.UP && currentIndex - ARTICLES_PER_ROW >= 0) {
            currentIndex -= ARTICLES_PER_ROW;
            displayArticles();
        }
    }

    private void loadArticles() {
        ArticleController art = new ArticleController();
        articles = art.getArticles();
        displayArticles();
    }

    public static void openBienvenue() {
        Bienvenue bienvenue = new Bienvenue();
        Stage bienvenueStage = new Stage();
        bienvenue.start(bienvenueStage);
        bienvenueStage.show();
    }

    // Filter articles by title
    public static List<ArticleSelect> filterArticlesByTitle(String titleSubstring) {
        return articles.stream()
                .filter(article -> article.getTitle().toLowerCase().contains(titleSubstring.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Filter articles by category
    public static List<ArticleSelect> filterArticlesByCategory(String category) {
        return articles.stream()
                .filter(article -> article.getCategorie().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static void displayFilteredArticlesByTitle(String titleSubstring) {
        List<ArticleSelect> filteredArticles = filterArticlesByTitle(titleSubstring);
        displayFilteredArticles(filteredArticles);
    }

    public static void displayFilteredArticlesByCategory(String category) {
        ArticleView.sectionTitle.setText(category);
        List<ArticleSelect> filteredArticles = filterArticlesByCategory(category);
        displayFilteredArticles(filteredArticles);
    }

    // Display filtered articles with pagination
    public static void displayFilteredArticles(List<ArticleSelect> filteredArticles) {
        theUser = Session.getLoggedInUser();
        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");
        articlesContainer.getChildren().clear();

        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, filteredArticles.size()); i++) {
            ArticleSelect article = filteredArticles.get(i);
            articlesContainer.getChildren().add(createArticleCard(article));
        }
    }

    private static void downloadArticle(ArticleSelect article) {
        System.out.println("I'm downloading");
        if (theUser == null) {
            openBienvenue();
        } else {
            try {
                downloadController.downloadArticle(article);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void openArticleDetail(ArticleSelect article) {
        if (theUser == null) {
            openBienvenue();
        } else {
            ArticleView.openDetailFormWithAnimation(article);
        }
    }
}
