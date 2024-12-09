package com.hamNews.Views;

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
    DownloadController downloadController = new DownloadController();

    private List<ArticleSelect> articles = new ArrayList<>();
    private HBox articlesContainer = new HBox(25);
    private int currentIndex = 0; // Indice des articles chargés
    private static final int ARTICLES_PER_ROW = 4; // Nombre d'articles à afficher par ligne
    private ScrollPane scrollPane;

    private Button readMoreButton;
    private Button likeButton;
    private Button downloadButton;
    private User theUser;

    @Override
    public void start(Stage primaryStage) {
    }

    private void displayArticles() {
        theUser = Session.getLoggedInUser();
        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");
        articlesContainer.getChildren().clear();

        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, articles.size()); i++) {
            ArticleSelect article = articles.get(i);

            VBox card = new VBox(20);
            card.setPadding(new Insets(10));

            card.setStyle(
                    "-fx-background-color: white; -fx-border-width: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0);");
            card.setPrefWidth(100);

            ImageView imageView = new ImageView(new Image(article.getImageUrl()));
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

            likeButton = new Button();
            likeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
            likeButton.setMinSize(40, 40);

            downloadButton = new Button();
            downloadButton
                    .setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");

            downloadButton.setMinSize(40, 40);

            Image IconI = new Image(getClass().getResource("/com/hamNews/Views/images/Like.png").toExternalForm());
            ImageView ImageIcon = new ImageView(IconI);
            ImageIcon.setFitWidth(20);
            ImageIcon.setFitHeight(20);
            likeButton.setGraphic(ImageIcon);

            Image IconD = new Image(getClass().getResource("/com/hamNews/Views/images/Donwload.png").toExternalForm());
            ImageView downloadIcon = new ImageView(IconD);
            downloadIcon.setFitHeight(20);
            downloadIcon.setFitWidth(20);
            downloadButton.setGraphic(downloadIcon);

            readMoreButton = new Button("Read More");
            readMoreButton
                    .setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
            readMoreButton.setMinSize(100, 40);

            likeButton.setOnAction(e -> {
                System.out.println("Liked article: " + article.getTitle());

            });

            downloadButton.setOnAction(e -> {
                if (theUser == null) {

                    openBienvenue();
                } else {
                    article.setDownloaded(!article.isDownloaded());
                    if (article.isDownloaded()) {
                        downloadIcon.setImage(new Image(
                                getClass().getResource("/com/hamNews/Views/images/Remove.png").toExternalForm()));
                    } else {
                        downloadIcon.setImage(new Image(
                                getClass().getResource("/com/hamNews/Views/images/Download.png").toExternalForm()));
                    }
                    System.out.println("Article " + article.getUrl() + " "
                            + (article.isDownloaded() ? "saved for" : "removed from") + " offline reading");
                }
            });

            readMoreButton.setOnAction(e -> {
                if (theUser == null) {

                    openBienvenue();
                } else {

                    System.out.println("Read more: " + article.getTitle());
                    ArticleView.openDetailFormWithAnimation(article);
                }
            });

            actions.getChildren().addAll(likeButton, downloadButton, readMoreButton);
            actions.setStyle("-fx-spacing: 15; -fx-alignment: center;");

            Region space1 = new Region();
            Region space2 = new Region();
            VBox.setVgrow(space1, Priority.ALWAYS);
            VBox.setVgrow(space2, Priority.ALWAYS);

            card.getChildren().addAll(imageView, title, space1, snippet, space2, actions);
            articlesContainer.getChildren().add(card);

        }
    }

    public ScrollPane ShowArticle() {
        loadArticles();

        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-padding: 0;");
        scrollPane.setOnScroll(event -> handleScroll(event));
        scrollPane.setOnKeyPressed(event -> handleKeyPress(event));

        return scrollPane;

    }

    public void handleScroll(ScrollEvent event) {

        if (event.getDeltaY() < 0) {
            if (currentIndex + ARTICLES_PER_ROW < articles.size()) {
                currentIndex += ARTICLES_PER_ROW;
                displayArticles();
            }
        } else if (event.getDeltaY() > 0) {
            if (currentIndex - ARTICLES_PER_ROW >= 0) {
                currentIndex -= ARTICLES_PER_ROW;
                displayArticles();
            }
        }

    }

    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            if (currentIndex + ARTICLES_PER_ROW < articles.size()) {
                currentIndex += ARTICLES_PER_ROW;
                displayArticles();
            }
        } else if (event.getCode() == KeyCode.UP) {
            if (currentIndex - ARTICLES_PER_ROW >= 0) {
                currentIndex -= ARTICLES_PER_ROW;
                displayArticles();
            }
        }
    }

    public void openBienvenue() {
        System.out.println("Ouverture de l'interface Home...");

        Bienvenue bienvenue = new Bienvenue();
        Stage bienvenueStage = new Stage();
        bienvenue.start(bienvenueStage);
        bienvenueStage.show();
    }

    private void loadArticles() {
        ArticleController art = new ArticleController();
        articles = art.getArticles();
        displayArticles();
    }

    // Filter articles by title
    public List<ArticleSelect> filterArticlesByTitle(String titleSubstring) {
        return articles.stream()
                .filter(article -> article.getTitle().toLowerCase().contains(titleSubstring.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Filter articles by category
    public List<ArticleSelect> filterArticlesByCategory(String category) {
        return articles.stream()
                .filter(article -> article.getCategorie().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public void displayFilteredArticlesByTitle(String titleSubstring) {
        List<ArticleSelect> filteredArticles = filterArticlesByTitle(titleSubstring);
        displayFilteredArticles(filteredArticles);
    }

    public void displayFilteredArticlesByCategory(String category) {
        List<ArticleSelect> filteredArticles = filterArticlesByCategory(category);
        displayFilteredArticles(filteredArticles);
    }

    // Display filtered articles with pagination
    private void displayFilteredArticles(List<ArticleSelect> filteredArticles) {
        articles = filteredArticles;
        currentIndex = 0; // Reset to the first page of the filtered articles
        displayArticles();
    }

}
