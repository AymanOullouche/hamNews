package com.hamNews;

import com.hamNews.Controler.NewsScraperTask;
import com.hamNews.Views.LoginView;
import com.hamNews.Views.Authentification;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        StackPane welcomePane = createWelcomePane();
        VBox mainContent = createMainContent();
        VBox loginPrompt = createLoginPrompt();

        loginPrompt.setVisible(false);
        // Start the background scraping process immediately after the application
        // launches
        startScraping();
        StackPane mainLayout = new StackPane(mainContent, welcomePane, loginPrompt);

        Scene scene = new Scene(mainLayout, 1150, 550);
        primaryStage.setTitle("Interface Principale");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button startButton = (Button) welcomePane.lookup("#startButton");
        startButton.setOnAction(event -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), welcomePane);
            transition.setToY(-600);
            transition.setOnFinished(e -> welcomePane.setVisible(false));
            transition.play();

            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(e2 -> loginPrompt.setVisible(true));
            delay.play();
        });
    }

    private void startScraping() {
        NewsScraperTask scraperTask = new NewsScraperTask();

        scraperTask.setOnSucceeded(event -> {
            System.out.println("Scraping complete!");
        });

        scraperTask.setOnFailed(event -> {
            System.out.println("Error during scraping!");
            System.out.println(scraperTask.getException().getMessage());
        });

        Thread scraperThread = new Thread(scraperTask);
        scraperThread.setDaemon(true);
        scraperThread.start();
    }

    private StackPane createWelcomePane() {
        Image welcomeImage = new Image(
                getClass().getResource("/com/hamNews/Views/images/welcome.jpeg").toExternalForm());

        ImageView welcomeImageView = new ImageView(welcomeImage);
        welcomeImageView.setFitWidth(1150);
        welcomeImageView.setFitHeight(550);

        Text welcomeText = new Text("Bienvenue à HamNews");
        welcomeText.setFont(Font.font("Arial", 48));
        welcomeText.setFill(Color.LIGHTBLUE);

        Text subtitleText = new Text("Conservez vos articles scrappés, téléchargez et gérez-les facilement !");
        subtitleText.setFont(Font.font("Arial", 20));
        subtitleText.setFill(Color.BLACK);

        Button startButton = new Button("C'est Parti");
        startButton.setId("startButton"); // Pour la récupération dans la fonction principale
        startButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 20; -fx-padding: 10 30;");
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);

        VBox welcomeLayout = new VBox(20, welcomeText, subtitleText, startButton);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setTranslateY(-50);

        return new StackPane(welcomeImageView, welcomeLayout);
    }

    private VBox createMainContent() {
        HBox banner = new HBox();
        banner.setStyle("-fx-background-color: #3498db;");
        banner.setPrefHeight(100);
        banner.setAlignment(Pos.CENTER_LEFT);

        Circle circle = new Circle(90);
        circle.setFill(Color.rgb(52, 152, 219));
        circle.setTranslateY(-130);

        // Image appImage = new Image("com/hamNews/Views/images/Profile.png");
        Image appImage = new Image(getClass().getResource("/com/hamNews/Views/images/Profile.png").toExternalForm());
        ImageView appImageView = new ImageView(appImage);
        appImageView.setFitWidth(100);
        appImageView.setFitHeight(100);
        appImageView.setPreserveRatio(true);
        appImageView.setTranslateY(-130);

        StackPane circleWithImage = new StackPane(circle, appImageView);

        Text articleText = new Text("la Liste des articles");
        articleText.setFont(Font.font("Arial", 24));
        articleText.setFill(Color.BLACK);

        VBox content = new VBox(20, banner, circleWithImage, articleText);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #ecf0f1;");
        content.setPrefHeight(400);

        return content;
    }

    // ======== Fonction pour créer le panneau de connexion ========
    private VBox createLoginPrompt() {
        VBox loginPrompt = new VBox(20);
        loginPrompt.setAlignment(Pos.CENTER);
        loginPrompt.setStyle("-fx-background-color: white; -fx-padding: 50; -fx-background-radius: 10;");
        loginPrompt.setPrefSize(400, 300);
        loginPrompt.setTranslateY(-50);

        // Ajouter l'image au-dessus du texte
        // Image connectImage = new Image("com/hamNews/Views/images/ConnectPlease.png");
        Image connectImage = new Image(
                getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        ImageView connectImageView = new ImageView(connectImage);
        connectImageView.setFitWidth(150);
        connectImageView.setFitHeight(100);
        connectImageView.setPreserveRatio(true);

        Text promptText = new Text("Pour continuer à explorer et sauvegarder,\nconnectez-vous !");
        promptText.setFont(Font.font("Arial", 24));
        promptText.setFill(Color.DODGERBLUE);

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18; -fx-padding: 10 30;");
        loginButton.setOnAction(event -> openLogin());

        // Ajouter l'image, le texte et le bouton dans l'ordre souhaité
        loginPrompt.getChildren().addAll(connectImageView, promptText, loginButton);
        return loginPrompt;
    }

    private void openLogin() {
        System.out.println("Ouverture de l'interface Login...");
        Authentification Auth = new Authentification();
        Stage AuthStage = new Stage();
        Auth.start(AuthStage);
        AuthStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
