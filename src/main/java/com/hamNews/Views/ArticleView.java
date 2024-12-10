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

    private static ArticleListView articleListView;
    private static ArticleDetailView  articleDetailView;
    private static VBox ArticlePan;
    private static ScrollPane DetailPan;
    private static Text sectionTitle;
    private static VBox mainLayout;
    private static ArticleSelect selectedArticle;


    @Override
    public void start(Stage primaryStage) {

        articleListView = new ArticleListView();
        ArticlePan = articleListView.ShowArticle();
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");


        // Titre de la section
        sectionTitle = new Text("Home");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        sectionTitle.setFill(Color.BLACK);


        mainLayout.getChildren().addAll(sectionTitle, ArticlePan);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");


        Scene scene = new Scene(mainLayout, 1150, 550);
        primaryStage.setTitle("Liste des articles");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public static void openDetailFormWithAnimation(ArticleSelect selectedArticle) {

        articleDetailView = new ArticleDetailView(selectedArticle);
        DetailPan =articleDetailView.ShowArticle();
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(DetailPan);
        DetailPan.setTranslateX(1000);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), DetailPan);
        transition.setFromX(1000);
        transition.setToX(0);
        transition.play();

    }

    public static void openListFormWithAnimation() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), DetailPan);
        transition.setFromX(0);
        transition.setToX(1300);
        transition.setOnFinished(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(sectionTitle, ArticlePan);
        });
        transition.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
