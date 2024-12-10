package com.hamNews.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SearchBar {

    public HBox ShowSideBar() {
        // Header container
        HBox header = new HBox(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        // Logo and application name
        ImageView logo = loadImage("/com/hamNews/Views/images/newspaper.png", 32, 32);
        Label appName = new Label("HamNews");
        appName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Empty spacer to push the search bar and button to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Let the spacer take up available space

        // Search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search articles...");
        searchBar.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5; -fx-padding: 3;");
        searchBar.setPrefWidth(180); // Reduced width
        searchBar.setPrefHeight(28); // Optional: Adjust height

        // Search button
        Button searchButton = new Button("Recherche");
        searchButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-border-radius: 5;");
        searchButton.setPadding(new Insets(5, 10, 5, 10)); // Reduced padding for a smaller button

        // Search button mouse click event
        searchButton.setOnMouseClicked(event -> {
            String query = searchBar.getText();
            if (!query.isEmpty()) {
                System.out.println("Mouse Clicked: Searching for " + query);
                ArticleListView.displayFilteredArticlesByTitle(query);

                // Add your search logic here
            } else {
                System.out.println("Mouse Clicked: Search bar is empty.");
            }
        });

        // Keyboard "Enter" key event for the search bar
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String query = searchBar.getText();
                if (!query.isEmpty()) {
                    System.out.println("Enter Pressed: Searching for " + query);
                    // Add your search logic here
                } else {
                    System.out.println("Enter Pressed: Search bar is empty.");
                }
            }
        });

        // Add elements to the header
        header.getChildren().addAll(logo, appName, spacer, searchBar, searchButton);
        HBox.setMargin(searchBar, new Insets(0, 0, 0, 20)); // Margin between the spacer and searchBar

        return header;
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
}
