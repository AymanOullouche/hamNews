package com.hamNews.Controler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Platform;
// import javafx.scene.text.Text;

public class ConnectivityController {

    private static boolean wasOnline = true; // Track the previous connection status
    // private static StackPane currentScene; // To update the current scene in
    // JavaFX

    // Method to start the connectivity monitor
    public static void startConnectivityMonitor() {
        new Thread(() -> {
            while (true) {
                boolean isOnline = isUserOnline();
                if (isOnline != wasOnline) {
                    // Update UI thread on connectivity change
                    Platform.runLater(() -> {
                        if (isOnline) {
                            showMainPage();
                        } else {
                            showConnectionLostPage();
                        }
                    });
                    wasOnline = isOnline; // Update the previous status
                }
                try {
                    Thread.sleep(5000); // Check every 5 seconds
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        }).start();
    }

    // Check if the user is online by pinging an external server
    private static boolean isUserOnline() {
        try {
            // Ping Google (or any reliable server)
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(1000); // 1-second timeout
            urlConnect.connect();
            return urlConnect.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false; // User is offline if an exception occurs
        }
    }

    // Show the main page (when the user is online)
    private static void showMainPage() {
        System.out.println("you are online!");
    }

    // Show the "Connection Lost" page (when the user is offline)
    private static void showConnectionLostPage() {
        System.out.println("connection lost");
    }
}
