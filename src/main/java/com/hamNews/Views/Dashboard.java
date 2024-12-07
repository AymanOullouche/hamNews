package com.hamNews.Views;

import com.hamNews.Model.DB.Session;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class  Dashboard extends Application {

    private ArticleView ArticleView;
    private SideBar NewsAggApp;
    private com.hamNews.Views.SearchBar sideBar;
    private ProfileSettingsView profil;

    private VBox Articleview;
    private HBox SearchBar;
    private VBox NavBar;
    private ScrollPane profileSettings;

    private BorderPane mainLayout = new BorderPane();
    private Stage primaryStage; // Stocke le Stage principal

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Initialiser le Stage principal

        ArticleView = new ArticleView();
        NewsAggApp = new SideBar();
        sideBar = new SearchBar();
        profil = new ProfileSettingsView();

        Articleview = ArticleView.ShowArticleView();
        NavBar = NewsAggApp.AfficherNavBar();
        SearchBar = sideBar.ShowSideBar();
        profileSettings = profil.getProfileSettings();

        // Boutons et leurs actions
        NewsAggApp.getProfileButton().setOnAction(e -> openInterfaceWithAnimation(profileSettings));
        NewsAggApp.getHomeButton().setOnAction(e -> openInterfaceWithAnimation(Articleview));
        NewsAggApp.getLogoutButton().setOnAction(e -> SeDeconnecter());

        // Configuration du layout principal
        mainLayout.setTop(SearchBar);
        mainLayout.setLeft(NavBar);
        mainLayout.setCenter(Articleview);

        Scene scene = new Scene(mainLayout, 1350, 650);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public void openInterfaceWithAnimation(Node argument) {
        mainLayout.getChildren().clear();
        mainLayout.setTop(SearchBar);
        mainLayout.setLeft(NavBar);
        mainLayout.setCenter(argument);

        profileSettings.setTranslateX(600);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), profileSettings);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    private void SeDeconnecter() {
        Session.logout(); // Déconnexion
        openLogin(); // Ouvrir la page de connexion
    }

    private void openLogin() {
        primaryStage.close(); // Fermer l'interface actuelle
        System.out.println("Ouverture de l'interface Authentification...");
        Authentification authentification = new Authentification();
        Stage loginStage = new Stage();
        authentification.start(loginStage); // Démarrer l'interface de connexion
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
