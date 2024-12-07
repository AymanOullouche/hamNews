package com.hamNews.Views;

import com.hamNews.Controler.ArticleController;
import com.hamNews.Controler.NewsScraperTask;
import com.hamNews.Model.Article.ArticleSelect;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.ScrollEvent;
/*import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;
import javax.imageio.ImageIO;*/



import com.hamNews.Model.Article.Article;


import java.util.ArrayList;
import java.util.List;

import static com.hamNews.Views.ArticleView.openSignUpFormWithAnimation;

public class ArticleListView extends Application {

    private List<ArticleSelect> articles = new ArrayList<>();
    private HBox articlesContainer = new HBox(20);
    private int currentIndex = 0;
    private static final int ARTICLES_PER_ROW = 3;
    private ScrollPane scrollPane;
    public  ArticleSelect article;
    private Button readMoreButton;

    @Override
    public void start(Stage primaryStage) {


    }

    // Méthode pour afficher les articles
    private void displayArticles() {

        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 15;");
        // Clear the container before adding new articles
        articlesContainer.getChildren().clear();

        // Afficher une ligne d'articles
        for (int i = currentIndex; i < Math.min(currentIndex + ARTICLES_PER_ROW, articles.size()); i++) {
            ArticleSelect article = articles.get(i);

            // Conteneur de la carte
            VBox card = new VBox(10); // Espacement entre les éléments de la carte
            card.setPadding(new Insets(10));
            // Style mis à jour : pas de bordure et fond blanc
            card.setStyle("-fx-background-color: white; -fx-border-width: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0);");
            card.setPrefWidth(250); // Largeur fixe de chaque carte

            // Image de l'article
            ImageView imageView = new ImageView(new Image(article.getImageUrl()));
            imageView.setFitHeight(150);
            imageView.setFitWidth(250);
            imageView.setPreserveRatio(true);

            // Titre de l'article
            Label title = new Label(article.getTitle());
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
            title.setMaxWidth(250);
            title.setMaxHeight(60);
            title.setWrapText(true);
            title.setAlignment(Pos.CENTER); // Centrer le titre


            // Résumé (description de l'article)
            Label snippet = new Label(article.getDescription());
            snippet.setWrapText(true);
            snippet.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
            snippet.setMaxWidth(250); // Largeur maximale
            snippet.setMaxHeight(60); // Hauteur maximale pour le champ de description
            snippet.setStyle("-fx-text-overrun: ellipsis; -fx-wrap-text: true; -fx-text-fill: #666;");


            HBox actions = new HBox(10);
            actions.setAlignment(Pos.CENTER);

            Button likeButton = new Button();
            likeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");  // Cache la bordure du bouton
            likeButton.setMinSize(40, 40);

            Button downloadButton = new Button();
            downloadButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
            downloadButton.setMinSize(40, 40);

            Image Danger = new Image(getClass().getResource("/com/hamNews/Views/images/Like.png").toExternalForm());
            ImageView dangericon = new ImageView(Danger);
            dangericon.setFitWidth(20);
            dangericon.setFitHeight(20);
            likeButton.setGraphic(dangericon);

            Image downloadIcon = new Image(getClass().getResource("/com/hamNews/Views/images/Donwload.png").toExternalForm());
            ImageView downloadImage = new ImageView(downloadIcon);
            downloadImage.setFitWidth(20);
            downloadImage.setFitHeight(20);
            downloadButton.setGraphic(downloadImage);  // Ajoutez l'icône au bouton

// Bouton "Read More"
            readMoreButton = new Button("Read More");
            readMoreButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
            readMoreButton.setMinSize(100, 40);

// Ajouter des actions sur les boutons
            likeButton.setOnAction(e -> {
                System.out.println("Liked article: " + article.getTitle());
            });

            downloadButton.setOnAction(e -> {
                article.setDownloaded(!article.isDownloaded());
                // Change l'icône ou le texte si nécessaire (en fonction de l'état de téléchargement)
                if (article.isDownloaded()) {

                    Image RemoveIcon = new Image(getClass().getResource("/com/hamNews/Views/images/Remove.png").toExternalForm());
                    ImageView RemoveImage = new ImageView(RemoveIcon);
                    RemoveImage.setFitWidth(20);
                    RemoveImage.setFitHeight(20);
                    downloadButton.setGraphic(RemoveImage);

                } else {

                    downloadImage.setImage(new Image("file:/D:/ENSIASD/TP S1/java/projet2/telecharger.png")); // Rechangez l'icône pour l'icône "Download"
                }
                System.out.println("Article " + article.getUrl() + " " + (article.isDownloaded() ? "saved for" : "removed from") + " offline reading");
            });




            actions.getChildren().addAll(likeButton, downloadButton, readMoreButton);
            actions.setStyle("-fx-spacing: 15; -fx-alignment: center;");

            Region space1 = new Region();
            Region space2 = new Region();
            VBox.setVgrow(space1, Priority.ALWAYS);
            VBox.setVgrow(space2, Priority.ALWAYS);


            // Ajouter les éléments à la carte
            card.getChildren().addAll(imageView, title, space1, snippet, space2, actions);

            // Ajouter la carte au conteneur
            articlesContainer.getChildren().add(card);
            readMoreButton.setOnAction(e -> {
                System.out.println("Read more: " + article.getTitle());

                ArticleDetailView articleDetailView = new ArticleDetailView(article);
                openSignUpFormWithAnimation(articleDetailView);
            });
        }
    }

    public ScrollPane ShowArticle() {
        loadArticles();

        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-padding: 0;");

        scrollPane.setOnScroll(event -> handleScroll(event));

        return scrollPane;


    }

    // Méthode pour gérer le défilement
    public void handleScroll(ScrollEvent event) {
        if (event.getDeltaY() < 0) { // L'utilisateur défile vers le bas
            if (currentIndex + ARTICLES_PER_ROW < articles.size()) {
                currentIndex += ARTICLES_PER_ROW; // Charger les articles suivants
                displayArticles();
            }
        } else if (event.getDeltaY() > 0) { // L'utilisateur défile vers le haut
            if (currentIndex - ARTICLES_PER_ROW >= 0) {
                currentIndex -= ARTICLES_PER_ROW; // Charger les articles précédents
                displayArticles();
            }
        }
    }

    private void loadArticles() {

        ArticleController art = new ArticleController();
        articles = art.getArticles();
        displayArticles();
    }


}
