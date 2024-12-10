package com.hamNews;

import com.hamNews.Controler.NewsScraperTask;
import com.hamNews.Views.Dashboard;

import javafx.application.Application;
import javafx.stage.Stage;

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
