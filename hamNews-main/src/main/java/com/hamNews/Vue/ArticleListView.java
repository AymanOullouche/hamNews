package com.hamNews.Vue;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ArticleListView extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db;");
        banner.setPrefHeight(100);
        banner.setAlignment(Pos.CENTER_LEFT);

        Text userText = new Text("Bonjour Walid");
        userText.setFill(Color.WHITE);
        userText.setFont(Font.font("Arial", 20));
        banner.getChildren().add(userText);
        HBox.setMargin(userText, new javafx.geometry.Insets(0, 0, 0, 20));

        Circle circle = new Circle(90);
        circle.setFill(Color.rgb(52, 152, 219));
        circle.setTranslateY(-130);

        Text articleText = new Text("Liste des article");
        articleText.setFont(Font.font("Arial", 24));
        articleText.setFill(Color.BLACK);

        VBox content = new VBox(20, circle, articleText);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #ecf0f1;");
        content.setPrefHeight(400);

        VBox root = new VBox(banner, content);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Interface principale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
