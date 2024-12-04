// package com.hamNews.Views;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// public class NewsAggregatorApp extends Application {

// private BorderPane mainLayout;

// public static void main(String[] args) {
// launch(args);
// }

// @Override
// public void start(Stage primaryStage) {
// primaryStage.setTitle("News Aggregator");

// // Create the main layout with BorderPane
// mainLayout = new BorderPane();

// // Set up Sidebar
// VBox sidebar = createSidebar();
// mainLayout.setLeft(sidebar);

// // Set up Navbar by using NavbarView class
// NavbarView navbar = new NavbarView(); // Using the custom NavbarView
// mainLayout.setTop(navbar);

// // Set up Main Content area (initial view)
// mainLayout.setCenter(createMainContent("Home"));

// Scene scene = new Scene(mainLayout, 1800, 900);
// primaryStage.setScene(scene);
// primaryStage.show();
// }

// // Sidebar creation
// private VBox createSidebar() {
// VBox sidebar = new VBox(10);
// sidebar.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 10;");

// Button homeButton = new Button("Home");
// Button offlineButton = new Button("Offline News");
// Button profileButton = new Button("Profile & Settings");

// homeButton.setOnAction(e -> updateContent("Home"));
// offlineButton.setOnAction(e -> updateContent("Offline"));
// profileButton.setOnAction(e -> updateContent("Profile"));

// sidebar.getChildren().addAll(homeButton, offlineButton, profileButton);
// return sidebar;
// }

// // Main content area - dynamic change based on navigation
// private VBox createMainContent(String view) {
// VBox content = new VBox(20);

// switch (view) {
// case "Home" -> content.getChildren().add(new Label("Home Content: News
// Feed"));
// // Here you can add the logic to show a list of articles
// case "Offline" -> content.getChildren().add(new Label("Offline News
// Content"));
// // Here, show articles that are saved for offline reading
// case "Profile" -> content.getChildren().add(new Label("Profile Settings"));
// // Add your profile and settings components here
// }

// return content;
// }

// // Update content dynamically
// private void updateContent(String view) {
// mainLayout.setCenter(createMainContent(view));
// }
// }