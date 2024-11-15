package com.hamNews.Vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the image
        Image appImage = new Image("com/hamNews/Vue/images/Register.png");
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(180);
        appImageView.setFitHeight(200);

        // Create welcome text
        Text welcomeText = new Text("Register");
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setStyle("-fx-font-weight: bold; -fx-fill: black;");

        // Arrange the components in a VBox layout
        VBox mainLayout = new VBox(20); // 20 px spacing between elements
        mainLayout.getChildren().addAll(appImageView, welcomeText);
        mainLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 850, 550);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
