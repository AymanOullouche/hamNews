package com.hamNews.Vue;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) {
        Image appImage = new Image("com/hamNews/Vue/images/Logo.png");
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(180);
        appImageView.setFitHeight(200);

        VBox leftPane = new VBox();
        leftPane.setPrefWidth(300);
        leftPane.setStyle("-fx-background-color: #8AC7FF;");
        leftPane.setAlignment(Pos.CENTER);
        leftPane.getChildren().add(appImageView);

        VBox rightPane = new VBox(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20));
        rightPane.setStyle("-fx-background-color: #ffffff;");

        Text Logo = new Text("HamNews");
        Logo.setFont(Font.font("Arial", 50));
        Logo.setStyle("-fx-font-weight: bold; -fx-fill: #3498db;");

        Text welcomeText = new Text("Bienvenue à l'application des articles");
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setStyle("-fx-font-weight: bold; -fx-fill: black;");

        Text registerPromptText = new Text("Vous n'avez pas de compte ? Rejoignez-nous !");
        registerPromptText.setFont(Font.font("Arial", 14));
        registerPromptText.setStyle("-fx-font-weight: bold; -fx-fill: black;");

        Image loginImage = new Image("com/hamNews/Vue/images/Login.png");
        ImageView loginImageView = new ImageView(loginImage);
        loginImageView.setFitWidth(60);
        loginImageView.setFitHeight(60);

        Image registerImage = new Image("com/hamNews/Vue/images/Register.png");
        ImageView registerImageView = new ImageView(registerImage);
        registerImageView.setFitWidth(60);
        registerImageView.setFitHeight(60);

        Button btnLogin = new Button("Login");
        btnLogin.setFont(new Font("Arial", 16));
        btnLogin.setPrefSize(120, 40);
        btnLogin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnLogin.setOnAction(event -> openLogin());

        Button btnRegister = new Button("Register");
        btnRegister.setFont(new Font("Arial", 16));
        btnRegister.setPrefSize(120, 40);
        btnRegister.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnRegister.setOnAction(event -> openRegister());

        VBox loginSection = new VBox(10);
        loginSection.setAlignment(Pos.CENTER);
        loginSection.getChildren().addAll(Logo, welcomeText, loginImageView, btnLogin);

        loginSection.setSpacing(20);

        VBox registerSection = new VBox(10);
        registerSection.setAlignment(Pos.CENTER);
        registerSection.getChildren().addAll(registerPromptText, registerImageView, btnRegister);

        registerSection.setSpacing(20);

        rightPane.getChildren().addAll(loginSection, registerSection);

        HBox mainLayout = new HBox(leftPane, rightPane);

        HBox.setHgrow(rightPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(mainLayout, 850, 550);
        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openLogin() {
        System.out.println("Ouverture de l'interface Login...");

        LoginView login = new LoginView();
        Stage loginStage = new Stage();

        // If Login has a start method taking a Stage, you can call it
        login.start(loginStage);

        loginStage.show();
    }

    private void openRegister() {
        System.out.println("Ouverture de l'interface Register...");
        RegisterView Register = new RegisterView();
        Stage RegisterStage = new Stage();

        // If Login has a start method taking a Stage, you can call it
        Register.start(RegisterStage);

        RegisterStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
