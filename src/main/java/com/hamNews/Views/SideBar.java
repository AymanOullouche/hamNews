package com.hamNews.Views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SideBar extends Application {

    private String activeCategory = "";
    private String currentView = "Home";

    private Button profileButton;
    private Button Home;
    private Button Logout;


    @Override
    public void start(Stage primaryStage) {


    }

    public VBox AfficherNavBar(){

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        // Categories
        VBox categorySection = new VBox(10);
        categorySection.getChildren().addAll(
                Home=createSidebarButton("Home", "/com/hamNews/Views/images/home.png"),
                createSidebarButton("Economie", "/com/hamNews/Views/images/Economie.png"),
                createSidebarButton("Sports", "/com/hamNews/Views/images/Sport.png"),
                createSidebarButton("Automobile", "/com/hamNews/Views/images/Car.png"),
                createSidebarButton("Societé", "/com/hamNews/Views/images/Society.png"),
                createSidebarButton("Monde", "/com/hamNews/Views/images/World.png"),
                createSidebarButton("Emploie", "/com/hamNews/Views/images/Work.png")
        );

        // Ensure categories take all the available space
        VBox.setVgrow(categorySection, Priority.ALWAYS);

        // Footer Buttons
        VBox footer = new VBox(15);
        footer.getChildren().addAll(
                createSidebarButton("Offline News", "/com/hamNews/Views/images/Offline.png"),
                profileButton =createSidebarButton("Profile", "/com/hamNews/Views/images/Profile.png", "-fx-text-fill: red;"),
                Logout=createSidebarButton("Log Out", "/com/hamNews/Views/images/Logout.png", "-fx-text-fill: red;")

        );

        // Add sections to the sidebar

        sidebar.getChildren().addAll(categorySection, footer);
        return sidebar;

    }
    public Button getProfileButton() {
        return profileButton;
    }
    public Button getHomeButton() {
        return Home;
    }
    public Button getLogoutButton() {
        return Logout;
    }




    private Button createSidebarButton(String text, String iconPath) {
        return createSidebarButton(text, iconPath, "-fx-text-fill: black;");
    }

    private Button createSidebarButton(String text, String iconPath, String customStyle) {
        Button button = new Button(text);
        button.setGraphic(loadImage(iconPath, 20, 20));
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
