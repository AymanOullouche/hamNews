package com.hamNews.Views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.hamNews.Controler.ArticleController;
import com.hamNews.Controler.DownloadController;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.DB.Session;
import com.hamNews.Model.User.User;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class OfflineNews extends Application {

    private static final int ARTICLES_PER_ROW = 6;
    private static DownloadController downloadController = new DownloadController();
    private static List<ArticleSelect> articles = new ArrayList<>();
    private static HBox articlesContainer = new HBox(20);
    private static int currentIndex = 0;
    private static ScrollPane scrollPane;
    private static VBox mainContainer;
    private static HBox paginationContainer;
    private static Button nextButton, previousButton;
    private static ArticleSelect article;
    private static ArticleDetailView articleDetailView;
    private static VBox mainLayout ;
    private static VBox articlePane ;
    private Stage primaryStage;
    private static ScrollPane DetailPan;

    private static Text sectionTitle;


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        WindowManager.addWindow(primaryStage);

        sectionTitle = new Text("Hors Ligne Nouvelles");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        sectionTitle.setFill(Color.BLACK);

        articlePane = new VBox();
        articlePane = ShowArticle();
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");
        mainLayout.getChildren().addAll(sectionTitle, articlePane);

        Scene scene = new Scene(mainLayout);
        primaryStage.setMaximized(true);

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);

        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.show();

    }

    public VBox ShowOfflineView() {

        sectionTitle = new Text("Hors Ligne Nouvelles");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        sectionTitle.setFill(Color.BLACK);

        articlePane = new VBox();
        articlePane = ShowArticle();
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");
        mainLayout.getChildren().addAll(sectionTitle, articlePane);


        return mainLayout;

    }

    public static void displayArticles() {
        articlesContainer.getChildren().clear(); // Clear previous articles
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");

        // Decide whether to use filtered articles or all articles

        if (articles.isEmpty()) {
            Label noArticlesLabel = new Label("Aucun article a afficher");
            noArticlesLabel.setStyle("-fx-font-size: 16; -fx-text-fill: gray; -fx-padding: 10;");
            noArticlesLabel.setAlignment(Pos.CENTER);

            // Add the label to the container
            articlesContainer.getChildren().add(noArticlesLabel);
            return; // Exit the method since there are no articles to display
        }
        // Create a GridPane to arrange the articles in a grid (2 rows, max ARTICLES_PER_ROW / 2 columns)
        GridPane grid = new GridPane();
        grid.setHgap(20); // Set horizontal gap between articles
        grid.setVgap(20); // Set vertical gap between rows
        grid.setAlignment(Pos.CENTER);


        // Add articles to the grid
        int row = 0;
        int col = 0;
        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, articles.size()); i++) {
            ArticleSelect article = articles.get(i);
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

        Button readMoreButton = createActionButton("Lire Plus", e -> openArticleDetail(article));

        // Style buttons with hover effect
        readMoreButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5px 10px;");
        readMoreButton.setOnMouseEntered(e -> readMoreButton.setStyle("-fx-background-color: #405bb5; -fx-text-fill: white; -fx-border-radius: 5px;"));
        readMoreButton.setOnMouseExited(e -> readMoreButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5px;"));


        actions.getChildren().add(readMoreButton);

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
            return null; // Or handle the error accordingly
        }
        return new Image(resourceUrl.toExternalForm());
    }

    public  VBox ShowArticle() {
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

    private  void changePage(int offset) {
        // Use filtered list if available, else use all articles
        int newIndex = currentIndex + offset;

        // Ensure we don't exceed the bounds of the filtered list
        if (newIndex >= 0 && newIndex < articles.size()) {
            currentIndex = newIndex;
            displayArticles();
            updatePaginationButtons();
        }
    }

    private static void updatePaginationButtons() {
        previousButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex + ARTICLES_PER_ROW >= articles.size());
    }


    public static void loadArticles() {
        DownloadController download = new DownloadController();
        articles = download.ArticleLoader();
        if (!articles.isEmpty()) {
            displayArticles();
        } else {
            System.out.println("No articles available to display.");
        }
    }




    private static void openArticleDetail(ArticleSelect article) {
        System.out.println("i'm opening");
        articleDetailView = new ArticleDetailView(article);
        DetailPan = articleDetailView.ShowArticle();

        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(DetailPan);
        DetailPan.setTranslateX(1000);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), DetailPan);
        transition.setFromX(1000);
        transition.setToX(0);
        transition.play();
    }
    public static void openDownloadFormWithAnimation() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), DetailPan);
        transition.setFromX(0);
        transition.setToX(1200);
        transition.setOnFinished(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(sectionTitle,articlePane);
        });
        transition.play();
    }
}