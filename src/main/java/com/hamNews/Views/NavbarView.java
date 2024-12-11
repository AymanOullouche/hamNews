package com.hamNews.Views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class NavbarView extends HBox {

    private final TextField searchField;
    private final Button searchButton;
    private final Button refreshButton;

    public NavbarView() {
        // Set the layout to use Flexbox equivalent (HBox)
        this.setSpacing(10); // Space between elements
        this.setStyle("-fx-padding: 10; -fx-background-color: #444444;");

        // Create the search field
        searchField = new TextField();
        searchField.setPromptText("Search articles...");
        searchField.setStyle("-fx-pref-width: 200px;");

        // Create the search button
        searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Create the refresh button with an icon (SVGPath for refresh icon)
        refreshButton = new Button();
        SVGPath refreshIcon = new SVGPath();
        refreshIcon.setContent(
                "M12 4V1L16 5L12 9V6C8.1 6 5 8.5 5 11C5 13.5 8.1 16 12 16C15.9 16 19 13.5 19 11H16C16 12.7 14.2 14 12 14C9.8 14 8 12.7 8 11C8 9.3 9.8 8 12 8C13.1 8 14 8.4 14 9H16C16 6.5 13.9 4 12 4Z");
        refreshIcon.setFill(Color.WHITE);

        // Style the refresh button
        refreshButton.setGraphic(refreshIcon);
        refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 5;");

        // Add all components to the HBox (navbar)
        this.getChildren().addAll(searchField, searchButton, refreshButton);

        // Set the layout to `justify-between` (flexbox behavior)
        this.setStyle("-fx-alignment: center-left; -fx-justify-content: space-between; -fx-pref-width: 100%;");

        // Add an event handler for the search functionality (Optional)
        searchButton.setOnAction(event -> handleSearch());
        searchField.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleSearch();
            }
        });

        // Add a refresh button action (Optional)
        refreshButton.setOnAction(event -> handleRefresh());
    }

    // Method to handle search action
    private void handleSearch() {
        String searchText = searchField.getText();

        searchField.clear();
    }

    // Method to handle refresh action
    private void handleRefresh() {
        searchField.clear();
    }
}