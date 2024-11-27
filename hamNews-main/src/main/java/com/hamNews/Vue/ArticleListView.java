package com.hamNews.Vue;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.animation.FadeTransition;
import javafx.util.Duration;



public class ArticleListView extends Application {
    StackPane finalRoot;
    @Override
    public void start(Stage primaryStage) {
         finalRoot = new StackPane(createMainInterface(),createFormAndBackground());
        Scene scene = new Scene(finalRoot, 1150, 550);
        primaryStage.setTitle("Interface principale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StackPane createFormAndBackground() {
        Image backgroundImage = new Image("com/hamNews/Vue/images/flow.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1150);
        backgroundImageView.setFitHeight(550);

        Image formImage = new Image("com/hamNews/Vue/images/LoginBack.png");
        ImageView formImageView = new ImageView(formImage);
        formImageView.setFitWidth(400);
        formImageView.setFitHeight(300);

        Button submitButton = new Button("Soumettre");
        submitButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");

        Text userText = new Text("Formulaire d'inscription");
        userText.setFill(Color.WHITE);

        VBox formContainer = new VBox(20, userText, formImageView, submitButton);
        formContainer.setAlignment(Pos.CENTER);

        submitButton.setOnAction(event -> {
            FadeTransition fadeForm = new FadeTransition(Duration.seconds(0.5), formContainer);
            fadeForm.setToValue(0);
            FadeTransition fadeBackground = new FadeTransition(Duration.seconds(0.5), backgroundImageView);
            fadeBackground.setToValue(0);

            fadeForm.play();
            fadeBackground.play();

            fadeForm.setOnFinished(e -> System.out.println("Formulaire et fond disparus."));
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, formContainer);
        return root;
    }

    private VBox createMainInterface() {
        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db;");
        banner.setPrefHeight(100);
        banner.setAlignment(Pos.CENTER_LEFT);

        Image appImage = new Image("com/hamNews/Vue/images/Profile.png");
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(100);
        appImageView.setFitHeight(100);
        appImageView.setPreserveRatio(true);
        appImageView.setTranslateY(-130);

        Text userTextBanner = new Text("Bonjour Walid");
        userTextBanner.setFill(Color.WHITE);
        userTextBanner.setFont(Font.font("Arial", 20));
        banner.getChildren().add(userTextBanner);
        HBox.setMargin(userTextBanner, new javafx.geometry.Insets(0, 0, 0, 20));

        Circle circle = new Circle(90);
        circle.setFill(Color.rgb(52, 152, 219));
        circle.setTranslateY(-130);

        StackPane circleWithImage = new StackPane();
        circleWithImage.getChildren().addAll(circle, appImageView);

        Text articleText = new Text("Liste des articles");
        articleText.setFont(Font.font("Arial", 24));
        articleText.setFill(Color.BLACK);

        Image likeImage = new Image("com/hamNews/Vue/images/like.png");
        ImageView likeImageView = new ImageView(likeImage);
        likeImageView.setFitWidth(40);
        likeImageView.setFitHeight(40);

        Image downloadImage = new Image("com/hamNews/Vue/images/download.png");
        ImageView downloadImageView = new ImageView(downloadImage);
        downloadImageView.setFitWidth(40);
        downloadImageView.setFitHeight(40);

        HBox imagesBox = new HBox(20, likeImageView, downloadImageView);
        imagesBox.setAlignment(Pos.CENTER);
        imagesBox.setTranslateX(-10);

        VBox content = new VBox(20, circleWithImage, articleText, imagesBox);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #ecf0f1;");
        content.setPrefHeight(400);

        VBox rootContainer = new VBox(banner, content);
        rootContainer.setPrefHeight(550);


        return rootContainer;
    }




    public static void main(String[] args) {
        launch(args);
    }
}

