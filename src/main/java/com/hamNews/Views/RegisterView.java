package com.hamNews.Views;

import com.hamNews.Controler.UserController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterView extends Application {

    @Override
    public void start(Stage primaryStage) {

        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db; -fx-padding: 10;");
        Text bannerText = new Text("Register Interface");
        bannerText.setFont(Font.font("Arial", 24));
        bannerText.setFill(Color.WHITE);
        banner.setAlignment(Pos.CENTER);
        banner.getChildren().add(bannerText);

        // Image appImage = new Image("com/hamNews/Views/images/Register.png");
        Image appImage = new Image(getClass().getResource("/com/hamNews/Views/images/Register.png").toExternalForm());
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(120);
        appImageView.setFitHeight(120);

        Text welcomeText = new Text("Créer un compte");
        welcomeText.setFont(Font.font("Arial", 24));
        welcomeText.setStyle("-fx-font-weight: bold; -fx-fill: #333;");

        TextField firstNameField = createCustomTextField("Prénom");
        TextField lastNameField = createCustomTextField("Nom");
        TextField emailField = createCustomTextField("Email");
        PasswordField passwordField = createCustomPasswordField("Mot de passe");

        Button registerButton = new Button("S'inscrire");
        registerButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        registerButton.setPrefHeight(40);
        registerButton.setPrefWidth(200);
        registerButton.setOnAction(e -> handleRegister(firstNameField, lastNameField, emailField, passwordField));

        Text returnToLogin = new Text("Vous avez déjà un compte ?");
        returnToLogin.setFill(Color.BLACK);

        Hyperlink loginLink = new Hyperlink("Connectez-vous ici");
        loginLink.setStyle("-fx-text-fill: #3498db; -fx-font-size: 14;");
        HBox bottomLinkBox = new HBox(5, returnToLogin, loginLink);
        bottomLinkBox.setAlignment(Pos.CENTER);

        VBox formLayout = new VBox(15, banner, appImageView, welcomeText, firstNameField, lastNameField, emailField,
                passwordField, registerButton, bottomLinkBox);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));
        formLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene scene = new Scene(formLayout, 890, 580);
        primaryStage.setTitle("Inscription");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createCustomTextField(String placeholder) {
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setStyle(
                "-fx-font-size: 14; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        textField.setMaxWidth(500);
        textField.setPrefHeight(40);
        return textField;
    }

    private PasswordField createCustomPasswordField(String placeholder) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(placeholder);
        passwordField.setStyle(
                "-fx-font-size: 14; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        passwordField.setMaxWidth(500);
        passwordField.setPrefHeight(40);
        return passwordField;
    }

    private void handleRegister(TextField firstNameField, TextField lastNameField, TextField emailField,
            PasswordField passwordField) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        if (!email.contains("@")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un email valide.");
            return;
        }

        if (password.length() <= 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe doit contenir plus de 8 caractères.");
            return;
        }

        UserController userController = new UserController();
        boolean success = userController.registerUser(firstName, lastName, password, email);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte créé avec succès !");
            openLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la création du compte.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openLogin() {
        System.out.println("Ouverture de l'interface Login...");
        LoginView login = new LoginView();
        Stage loginStage = new Stage();
        login.start(loginStage);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
