package com.hamNews.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SearchBar {



    public HBox ShowSideBar(){

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
