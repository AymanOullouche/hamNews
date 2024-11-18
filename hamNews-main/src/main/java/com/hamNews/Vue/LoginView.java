package com.hamNews.Vue;

import com.hamNews.Controler.UserController;
import com.hamNews.Model.User.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class LoginView extends Application {

    private UserController userController = new UserController();

    @Override
    public void start(Stage primaryStage) {

        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db; -fx-padding: 10;");
        Text bannerText = new Text("Login Interface");
        bannerText.setFont(Font.font("Arial", 24));
        bannerText.setFill(Color.WHITE);
        banner.setAlignment(Pos.CENTER);
        banner.getChildren().add(bannerText);

        Image appImage = new Image("com/hamNews/Vue/images/Login.png");
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(100);
        appImageView.setFitHeight(120);

        Text welcomeText = new Text("Connectez-vous");
        welcomeText.setFont(Font.font("Arial", 26));
        welcomeText.setStyle("-fx-font-weight: bold; -fx-fill: #2c3e50;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-font-size: 14; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        emailField.setMaxWidth(500);
        emailField.setPrefHeight(40);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        passwordField.setMaxWidth(500);
        passwordField.setPrefHeight(40);

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        loginButton.setPrefWidth(200);
        loginButton.setPrefHeight(40);

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.WARNING);
                return;
            }

            if (!email.contains("@")) {
                showAlert("Erreur", "Veuillez entrer un email valide.", Alert.AlertType.WARNING);
                return;
            }

            User loggedUser = userController.loginUser(email, password);

            if (loggedUser != null) {
                openArticleListe();
            } else {
                showAlert("Échec", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
            }
        });

        Text forgotPasswordText = new Text("Vous avez oublié votre mot de passe ?");
        forgotPasswordText.setFill(Color.BLACK);

        Hyperlink recoverLink = new Hyperlink("Récupérez-le maintenant");
        recoverLink.setStyle("-fx-text-fill: #3498db; -fx-font-size: 14;");

        VBox forgotPasswordLayout = new VBox(5, forgotPasswordText, recoverLink);
        forgotPasswordLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, banner, appImageView, welcomeText, emailField, passwordField, loginButton, forgotPasswordLayout);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene scene = new Scene(mainLayout, 850, 550);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void openArticleListe() {
        System.out.println("Ouverture de l'interface ArticleListe...");
        ArticleListView articleListView = new ArticleListView();
        Stage articleStageView = new Stage();
        articleListView.start(articleStageView);

        articleStageView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
