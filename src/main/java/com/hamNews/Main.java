package com.hamNews;

import com.hamNews.Controler.NewsScraperTask;
import com.hamNews.Views.Authentification;

import com.hamNews.Views.Dashboard;
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

        startScraping();
        openDashboard();
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



    private void openDashboard() {
        System.out.println("Ouverture de l'interface Login...");
        Dashboard Auth = new Dashboard();
        Stage AuthStage = new Stage();
        Auth.start(AuthStage);
        AuthStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

