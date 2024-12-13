package com.hamNews.Views;

import com.hamNews.Model.DB.Session;
import com.hamNews.Model.User.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SideBar extends Application {

    private String activeCategory = "";
    private String currentView = "Acceuil";

    private Button profileButton;
    private Button homeButton;
    private Button logoutButton;
    private Button offlineButton;

    @Override
    public void start(Stage primaryStage) {
        VBox sidebar = AfficherNavBar();

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
//        primaryStage.setTitle("Sidebar Example");
        primaryStage.show();
    }

    public VBox AfficherNavBar() {
        // Sidebar container
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        // Categories section
        VBox categorySection = new VBox(10);
        categorySection.getChildren().addAll(
                homeButton = createSidebarButton("Acceuil", "/com/hamNews/Views/images/home.png"),
                createSidebarButton("Economie", "/com/hamNews/Views/images/Economie.png"),
                createSidebarButton("Sports", "/com/hamNews/Views/images/Sport.png"),
                createSidebarButton("Automobile", "/com/hamNews/Views/images/Car.png"),
                createSidebarButton("Societe", "/com/hamNews/Views/images/Society.png"),
                createSidebarButton("Monde", "/com/hamNews/Views/images/World.png"),
                createSidebarButton("Emploi", "/com/hamNews/Views/images/Work.png"));

        // Ensure categories take available vertical space
        VBox.setVgrow(categorySection, Priority.ALWAYS);

        // Footer buttons
        VBox footer = new VBox(15);
        footer.setAlignment(Pos.BOTTOM_CENTER);

        User theUser = Session.getLoggedInUser();
        if (theUser != null) {
            footer.getChildren().addAll(
                    offlineButton = createSidebarButton("HorsLigne Nouvelle", "/com/hamNews/Views/images/Offline.png"),
                    profileButton = createSidebarButton("Profile & Paramètres", "/com/hamNews/Views/images/Profile.png",
                            "-fx-text-fill: black;"),
                    logoutButton = createSidebarButton("Déconnexion", "/com/hamNews/Views/images/Logout.png",
                            "-fx-text-fill: black;"));
        }

        // Add sections to the sidebar
        sidebar.getChildren().addAll(categorySection, footer);
        return sidebar;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    private Button createSidebarButton(String text, String iconPath) {
        return createSidebarButton(text, iconPath, "-fx-text-fill: black;");
    }

    private Button createSidebarButton(String text, String iconPath, String customStyle) {
        Button button = new Button(text);
        button.setGraphic(loadImage(iconPath, 25, 25));
        button.setStyle(
                "-fx-background-color: transparent; -fx-alignment: center-left; -fx-font-size: 14px; " +
                        customStyle);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(event -> {
            activeCategory = text;
            if (activeCategory.equals("Acceuil")) {
                ArticleView.sectionTitle.setText("Acceuil");
                ArticleListView.currentIndex = 0;
                ArticleListView.filteredArticles.clear();
                ArticleListView.displayArticles();
            } else {

                ArticleListView.displayFilteredArticlesByCategory(activeCategory);
            }
            // Further navigation logic here...
        });
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
