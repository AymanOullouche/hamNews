package com.hamNews.Views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Bienvenue extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        VBox loginPrompt = createLoginPrompt();
        WindowManager.addWindow(primaryStage);

        this.primaryStage = primaryStage;
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        primaryStage.setWidth(screenWidth * 0.5);
        primaryStage.setHeight(screenHeight * 0.5);
        Scene scene = new Scene(loginPrompt, screenWidth * 0.5, screenHeight * 0.5);

        // Scene scene = new Scene(loginPrompt, 1350, 650);
        primaryStage.setTitle("Authentification");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLoginPrompt() {
        VBox loginPrompt = new VBox(20);
        loginPrompt.setAlignment(Pos.CENTER);
        loginPrompt.setStyle("-fx-background-color: white; -fx-padding: 50; -fx-background-radius: 10;");
        // loginPrompt.setPrefSize(400, 300);
        loginPrompt.setTranslateY(-50);

        // Ajouter l'image au-dessus du texte
        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        ImageView connectImageView = new ImageView(icon);
        connectImageView.setFitWidth(150);
        connectImageView.setFitHeight(100);
        connectImageView.setPreserveRatio(true);

        Text promptText = new Text("Pour continuer à explorer et sauvegarder,\nconnectez-vous !");
        promptText.setFont(Font.font("Arial", 24));


        Button loginButton = new Button("Se connecter");
        loginButton.setStyle(
                "-fx-background-color: #5271ff; -fx-text-fill: white; -fx-font-size: 18; -fx-padding: 10 30;");
        loginButton.setOnAction(event -> openLogin());

        // Ajouter l'image, le texte et le bouton dans l'ordre souhaité
        loginPrompt.getChildren().addAll(connectImageView, promptText, loginButton);
        return loginPrompt;
    }

    private void openLogin() {
        WindowManager.closeAllWindows();
        Authentification login = new Authentification();
        Stage loginStage = new Stage();
        login.start(loginStage);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
