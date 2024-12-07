package com.hamNews.Views;

import com.hamNews.Model.Article.Article;
import com.hamNews.Model.Article.ArticleContent;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.Article.Category;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.Jsoup;

import static com.hamNews.Views.ArticleView.openSignUpFormWithAnimation;
import static com.hamNews.Views.ArticleView.reverseAnimation;

public class ArticleDetailView extends Application {

    private ArticleSelect article;
    private ArticleContent content  ;
    private Category category ;

    private HBox articlesContainer = new HBox(20);
    private int currentIndex = 0; // Indice des articles chargés
    private static final int ARTICLES_PER_ROW = 3; // Nombre d'articles à afficher par ligne

    public ArticleDetailView(ArticleSelect article) {
        this.article = article;
    }

    @Override
    public void start(Stage primaryStage) {




    }

    private String htmlToPlainText(String html) {
        return Jsoup.parse(html).text(); // Utilise Jsoup pour supprimer les balises HTML
    }
    // Méthode pour afficher les articles
    private void displayArticles() {
        // Clear the container before adding new articles
        articlesContainer.getChildren().clear();

        // Conteneur de la carte
        VBox card = new VBox(40); // Espacement entre les éléments de la carte
        card.setPadding(new Insets(10));
        // Style mis à jour : pas de bordure et fond blanc
        card.setStyle("-fx-background-color: white; -fx-border-width: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 0);");
        card.setPrefWidth(400); // Largeur fixe de chaque carte

        // Titre de l'article et bouton "remove"
        HBox titleWithRemove = new HBox(10);
        Label title = new Label(article.getTitle());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER); // Centrer le titre

        Button removeButton = new Button();
        removeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;");
        removeButton.setMinSize(30, 30);
        Image RemoveIcon = new Image(getClass().getResource("/com/hamNews/Views/images/Return.png").toExternalForm());
        ImageView removeIcon = new ImageView(RemoveIcon);
        removeIcon.setFitHeight(20);  // Taille de l'icône
        removeIcon.setFitWidth(20);
        removeButton.setGraphic(removeIcon);  // Ajoutez l'icône au bouton
        titleWithRemove.getChildren().addAll(title, removeButton);
        removeButton.setOnAction(e -> {
            reverseAnimation();
        });

        // Image de l'article
        ImageView imageView = new ImageView(new Image(article.getImageUrl()));
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // Catégorie et date de publication
        HBox categoryDate = new HBox(5);
        Label categoryLabel = new Label(article.getCategorie());
        categoryLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        Label date = new Label(article.getPublishDate());
        date.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        categoryDate.getChildren().addAll(categoryLabel, date);

        // Résumé (description de l'article)
        Label snippet = new Label(article.getDescription());
        snippet.setWrapText(true);
        snippet.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        // Contenu (Résumé plus détaillé)
        Label contenu = new Label(htmlToPlainText(article.getContent()));
        contenu.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        // Ajouter les éléments à la carte
        card.getChildren().addAll(titleWithRemove, imageView, categoryDate, snippet, contenu);

        // Ajouter la carte au conteneur
        articlesContainer.getChildren().add(card);
    }

    public HBox getArticlesContainer() {
        articlesContainer.setAlignment(Pos.CENTER);
        articlesContainer.setStyle("-fx-background-color: white; -fx-padding: 10;");

        displayArticles();


        return articlesContainer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
