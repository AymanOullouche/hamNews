package com.hamNews.Views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Account extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db;");
        banner.setPrefHeight(100);
        banner.setAlignment(Pos.CENTER_LEFT);

        // Image appImage = new Image("com/hamNews/Views/images/Profile.png");
        Image appImage = new Image(getClass().getResource("/com/hamNews/Views/images/Profile.png").toExternalForm());
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(100);
        appImageView.setFitHeight(100);
        appImageView.setPreserveRatio(true);
        appImageView.setTranslateY(-130);

        Circle circle = new Circle(90);
        circle.setFill(Color.rgb(52, 152, 219));
        circle.setTranslateY(-130);

        StackPane circleWithImage = new StackPane();
        circleWithImage.getChildren().addAll(circle, appImageView);

        Text articleText = new Text("article Détail");
        articleText.setFont(Font.font("Arial", 24));
        articleText.setFill(Color.BLACK);

        VBox content = new VBox(20, circleWithImage, articleText);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #ecf0f1;");
        content.setPrefHeight(400);

        VBox root = new VBox(banner, content);

        Scene scene = new Scene(root, 1050, 550);
        primaryStage.setTitle("Votre Compte");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
