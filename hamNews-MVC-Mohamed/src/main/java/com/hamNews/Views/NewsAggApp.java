package com.hamNews.Views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewsAggApp extends Application {

    private String activeCategory = "";
    private String currentView = "Home";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NewsAgg");

        // Header
        HBox header = new HBox(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = loadImage("/com/hamNews/Views/images/newspaper.png", 32, 32);
        Label appName = new Label("NewsAgg");
        appName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search articles...");
        searchBar.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5; -fx-padding: 5;");

        // Enable stretching
        HBox.setHgrow(searchBar, Priority.ALWAYS);

        HBox root = new HBox(searchBar);
        root.setSpacing(10); // Optional spacing
        root.setPrefWidth(600);
        header.getChildren().addAll(logo, appName, searchBar);
        HBox.setMargin(searchBar, new Insets(0, 0, 0, 20));

        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        // Categories
        VBox categorySection = new VBox(10);
        categorySection.getChildren().addAll(
                createSidebarButton("Home", "/com/hamNews/Views/images/home.png"),
                createSidebarButton("Economie", "/icons/technology.png"),
                createSidebarButton("Sports", "/icons/science.png"),
                createSidebarButton("Automobile", "/icons/politics.png"),
                createSidebarButton("Societé", "/icons/entertainment.png"),
                createSidebarButton("Monde", "/icons/entertainment.png"),
                createSidebarButton("Emploie", "/icons/entertainment.png")
        );

        // Ensure categories take all the available space
        VBox.setVgrow(categorySection, Priority.ALWAYS);

        // Footer Buttons
        VBox footer = new VBox(15);
        footer.getChildren().addAll(
                createSidebarButton("Offline News", "/com/hamNews/Views/images/offline.png"),
                createSidebarButtonWithAction("Profile & Settings", "/com/hamNews/Views/images/profile.jpeg","-fx-text-fill: black;", primaryStage),
                createSidebarButton("Log Out", "/com/hamNews/Views/images/logout.png", "-fx-text-fill: red;")
        );

        // Add sections to the sidebar
        sidebar.getChildren().addAll(categorySection, footer);

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setLeft(sidebar);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createSidebarButton(String text, String iconPath) {
        return createSidebarButton(text, iconPath, "-fx-text-fill: black;");
    }

    private Button createSidebarButton(String text, String iconPath, String customStyle) {
        Button button = new Button(text);
        button.setGraphic(loadImage(iconPath, 16, 16));
        button.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left; -fx-font-size: 14px; " +
                        customStyle
        );
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }
    private Button createSidebarButtonWithAction(String text, String iconPath, String customStyle, Stage primaryStage) {
        Button button = new Button(text);
        button.setGraphic(loadImage(iconPath, 16, 16));
        button.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left; -fx-font-size: 14px; " +
                        customStyle
        );
        button.setMaxWidth(Double.MAX_VALUE);

        // Define behavior when button is clicked
        if (text.equals("Profile & Settings")) {
            button.setOnAction(e -> {
                // Load the Profile.java page
               ProfileSettingsView profilePage = new ProfileSettingsView(); // Assuming you have a Profile class
                try {
                    profilePage.start(primaryStage); // Navigate to the Profile page
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        return button;
    }

    private ImageView loadImage(String path, double width, double height) {
        try {
            Image img = new Image(getClass().getResource(path).toString());
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            return imageView;
        } catch (Exception e) {
            System.out.println("Image not found: " + path);
            return new ImageView(); // Empty placeholder
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
