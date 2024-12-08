package com.hamNews.Views;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
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
    private static ScrollPane ArticlePan;
    private static Text sectionTitle;
    private static HBox articleDetail;
    private static VBox mainLayout;

    @Override
    public void start(Stage primaryStage) {

    }

    public VBox ShowArticleView() {
        articleListView = new ArticleListView();
        ArticlePan = articleListView.ShowArticle();
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");

        sectionTitle = new Text("Home");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        sectionTitle.setFill(Color.BLACK);

        mainLayout.getChildren().addAll(sectionTitle, ArticlePan);

        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white");

        return mainLayout;

    }

    public static void openSignUpFormWithAnimation(ArticleDetailView articleDetailView) {
        articleDetail = articleDetailView.getArticlesContainer();
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(articleDetail);
        articleDetail.setTranslateX(100);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), articleDetail);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    public static void reverseAnimation() {
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(sectionTitle, ArticlePan);
        ArticlePan.setTranslateX(600);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), ArticlePan);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
