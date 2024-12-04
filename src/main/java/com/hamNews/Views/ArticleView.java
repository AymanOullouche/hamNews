package com.hamNews.Views;

import com.hamNews.Model.Article.ArticleSelect;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ArticleView extends Application {

    private ArticleListView articleListView;
    private ArticleDetailView  articleDetailView;
    private ScrollPane ArticlePan;
    private VBox mainLayout;

    @Override
    public void start(Stage primaryStage) {
        articleListView = new ArticleListView();
        ArticleSelect articleSelect=articleListView.article;
        articleDetailView=new ArticleDetailView(articleSelect);
        ArticlePan = articleListView.ShowArticle();
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");

        // Titre de la section
        Text sectionTitle = new Text("Home");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        sectionTitle.setFill(Color.BLACK);


        mainLayout.getChildren().addAll(sectionTitle, ArticlePan);

        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");


        Scene scene = new Scene(mainLayout, 1150, 550);
        primaryStage.setTitle("Authentification");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
