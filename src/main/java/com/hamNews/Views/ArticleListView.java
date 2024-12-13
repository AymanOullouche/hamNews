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
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ArticleListView extends Application {
    private static final int ARTICLES_PER_ROW = 8;
    private static DownloadController downloadController = new DownloadController();
    private static List<ArticleSelect> articles = new ArrayList<>();
    public static List<ArticleSelect> filteredArticles = new ArrayList<>();
    private static HBox articlesContainer = new HBox(20);
    public static int currentIndex = 0;
    private static ScrollPane scrollPane;
    private static User theUser;
    private static VBox mainContainer;
    private static HBox paginationContainer;
    private static Button nextButton, previousButton;

    @Override
    public void start(Stage primaryStage) {
    }
//    public static void displayArticles() {
//        theUser = Session.getLoggedInUser();
//        articlesContainer.setAlignment(Pos.CENTER);
//        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");
//        articlesContainer.getChildren().clear();
//        List<ArticleSelect> displayList = filteredArticles.isEmpty() ? articles : filteredArticles;
//        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, displayList.size()); i++) {
//            ArticleSelect article = displayList.get(i);
//            articlesContainer.getChildren().add(createArticleCard(article));
//        }
//    }

    public static void displayArticles() {
        theUser = Session.getLoggedInUser();
        articlesContainer.getChildren().clear(); // Clear previous articles
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");

        // Decide whether to use filtered articles or all articles
        List<ArticleSelect> displayList = filteredArticles.isEmpty() ? articles : filteredArticles;

        // Create a GridPane to arrange the articles in a grid (2 rows, max ARTICLES_PER_ROW / 2 columns)
        GridPane grid = new GridPane();
        grid.setHgap(20); // Set horizontal gap between articles
        grid.setVgap(20); // Set vertical gap between rows
        grid.setAlignment(Pos.CENTER);

        // Add articles to the grid
        int row = 0;
        int col = 0;
        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, displayList.size()); i++) {
            ArticleSelect article = displayList.get(i);
            grid.add(createArticleCard(article), col, row);
            col++;

            // Move to the next row after 2 columns
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        // Add the GridPane to the main container
        articlesContainer.getChildren().add(grid);
    }





    public static VBox createArticleCard(ArticleSelect article) {
        // Create the card container
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #f8f8f8; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 5, 5);");
        card.setPrefWidth(350);
        card.setPrefHeight(380);
        card.setAlignment(Pos.TOP_CENTER);

        // Load and display the image
        Image image = loadImage(article.getImageName());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Title
        Label title = new Label(article.getTitle());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: 'Arial';");
        title.setMaxWidth(320);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        // Snippet or description
        Label snippet = new Label(article.getDescription());
        snippet.setWrapText(true);
        snippet.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
        snippet.setMaxWidth(320);
        snippet.setMaxHeight(70);
        snippet.setStyle("-fx-text-overrun: ellipsis; -fx-wrap-text: true;");
        snippet.setAlignment(Pos.CENTER);

        // Action buttons container
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.BOTTOM_RIGHT);

        Button downloadButton = createActionButton("/com/hamNews/Views/images/Download.png", e -> downloadArticle(article));
        Button readMoreButton = createActionButton("Lire Plus", e -> openArticleDetail(article));

        // Style buttons with hover effect
        downloadButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5px 10px;");
        readMoreButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5px 10px;");
        readMoreButton.setOnMouseEntered(e -> readMoreButton.setStyle("-fx-background-color: #405bb5; -fx-text-fill: white; -fx-border-radius: 5px;"));
        readMoreButton.setOnMouseExited(e -> readMoreButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5px;"));


        actions.getChildren().addAll(downloadButton, readMoreButton);

        // Space between elements for balanced look
        Region space1 = new Region();
        VBox.setVgrow(space1, Priority.ALWAYS);

        // Add elements to card
        card.getChildren().addAll(imageView, title, space1, snippet, actions);
        return card;
    }



    private static Button createActionButton(String textOrImagePath, javafx.event.EventHandler<javafx.event.ActionEvent> actionHandler) {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
        button.setMinSize(80, 30);

        if (textOrImagePath.endsWith(".png")) {
            Image icon = new Image(ArticleListView.class.getResource(textOrImagePath).toExternalForm());
            ImageView iconView = new ImageView(icon);
            iconView.setFitHeight(30);
            iconView.setFitWidth(30);
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
            return null;
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


    private  void setupScrollPane() {
        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-padding: 0;");
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setOnScroll(this::handleScroll);
        scrollPane.setOnKeyPressed(this::handleKeyPress);
    }


    private  void setupPaginationButtons() {
        nextButton = createPaginationButton("Suivant", e -> changePage(ARTICLES_PER_ROW));
        previousButton = createPaginationButton("Précedent", e -> changePage(-ARTICLES_PER_ROW));

        paginationContainer = new HBox(10);
        paginationContainer.getChildren().addAll(previousButton, nextButton);
        paginationContainer.setPadding(new Insets(10));
    }





    private Button createPaginationButton(String text,
            javafx.event.EventHandler<javafx.event.ActionEvent> actionHandler) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #5271ff; -fx-border-color: transparent; -fx-cursor: hand; -fx-text-fill: white;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #405bb5; -fx-text-fill: white; -fx-border-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5px;"));
        button.setMinSize(60, 30);
        button.setOnAction(actionHandler);
        return button;
    }




    private void handleScroll(ScrollEvent event) {
        if (event.getDeltaY() < 0) {
            changePage(ARTICLES_PER_ROW);
        } else if (event.getDeltaY() > 0) {
            changePage(-ARTICLES_PER_ROW);
        }
    }



    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            changePage(ARTICLES_PER_ROW);
        } else if (event.getCode() == KeyCode.UP) {
            changePage(-ARTICLES_PER_ROW);
        }
    }



    private  void loadArticles() {
        ArticleController art = new ArticleController();
        articles = art.getArticles();
        displayArticles();
    }


    private static void openBienvenue() {
        Bienvenue bienvenue = new Bienvenue();
        Stage bienvenueStage = new Stage();
        bienvenue.start(bienvenueStage);
        bienvenueStage.show();
    }



    public static void displayFilteredArticlesByTitle(String titleSubstring) {
        displayFilteredArticles(filterArticlesByTitle(titleSubstring));
    }

    public static void displayFilteredArticlesByCategory(String category) {
        ArticleView.sectionTitle.setText(category);
        displayFilteredArticles(filterArticlesByCategory(category));
    }

    private static List<ArticleSelect> filterArticlesByTitle(String titleSubstring) {
        return articles.stream()
                .filter(article -> article.getTitle().toLowerCase().contains(titleSubstring.toLowerCase()))
                .collect(Collectors.toList());
    }

    private static List<ArticleSelect> filterArticlesByCategory(String category) {
        return articles.stream()
                .filter(article -> article.getCategorie().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    private  void changePage(int offset) {
        // Use filtered list if available, else use all articles
        List<ArticleSelect> displayList = filteredArticles.isEmpty() ? articles : filteredArticles;
        int newIndex = currentIndex + offset;

        // Ensure we don't exceed the bounds of the filtered list
        if (newIndex >= 0 && newIndex < displayList.size()) {
            currentIndex = newIndex;
            displayArticles();
            updatePaginationButtons();
        }
    }

    private static void updatePaginationButtons() {
        List<ArticleSelect> displayList = filteredArticles.isEmpty() ? articles : filteredArticles;
        previousButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex + ARTICLES_PER_ROW >= displayList.size());
    }

    private static void displayFilteredArticles(List<ArticleSelect> filteredArticlesList) {
        filteredArticles = filteredArticlesList;  // Update filtered list
        currentIndex = 0;  // Reset pagination index
        displayArticles();
        updatePaginationButtons();
    }


    private static void downloadArticle(ArticleSelect article) {
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
