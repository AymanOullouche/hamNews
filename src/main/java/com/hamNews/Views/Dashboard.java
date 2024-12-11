package com.hamNews.Views;

import com.hamNews.Model.DB.Session;
import com.hamNews.Model.User.User;

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

public class Dashboard extends Application {

    private ArticleView ArticleView;
    private SideBar NewsAggApp;
    private SearchBar sideBar;
    private ProfileSettingsView profil;
    private VBox Articleview;
    private HBox SearchBar;
    private VBox NavBar;
    private ScrollPane profileSettings;
    private BorderPane mainLayout = new BorderPane();
    private Stage primaryStage;
    private User theUser;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        theUser = Session.getLoggedInUser();
        ArticleView = new ArticleView();
        NewsAggApp = new SideBar();
        sideBar = new SearchBar();
        profil = new ProfileSettingsView();

        theUser = Session.getLoggedInUser();

        Articleview = ArticleView.ShowArticleView();
        NavBar = NewsAggApp.AfficherNavBar();
        SearchBar = sideBar.ShowSideBar();
        WindowManager.addWindow(primaryStage);
        if (theUser != null) {

            profil = new ProfileSettingsView();

            profileSettings = profil.getProfileSettings();

            NewsAggApp.getProfileButton().setOnAction(e -> openInterfaceWithAnimation(profileSettings));
            NewsAggApp.getHomeButton().setOnAction(e -> openInterfaceWithAnimation(Articleview));
            NewsAggApp.getLogoutButton().setOnAction(e -> SeDeconnecter());
        }
        // if (theUser == null) {

        // openBienvenue();

        // }

        // Configuration du layout principal
        mainLayout.setTop(SearchBar);
        mainLayout.setLeft(NavBar);
        mainLayout.setCenter(Articleview);

        Scene scene = new Scene(mainLayout);
        primaryStage.setMaximized(true);

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);

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
        Session.logout();
        openLogin();
    }

    private void openLogin() {
        primaryStage.close();
        Authentification authentification = new Authentification();
        Stage loginStage = new Stage();
        authentification.start(loginStage);
        loginStage.show();
    }

    // public void openBienvenue() {
    // System.out.println("Ouverture de l'interface Home après 10 secondes...");

    // PauseTransition pause = new PauseTransition(Duration.seconds(10));

    // pause.setOnFinished(event -> {
    // Bienvenue bienvenue = new Bienvenue();
    // Stage bienvenueStage = new Stage();
    // bienvenue.start(bienvenueStage);
    // bienvenueStage.show();
    // });

    // pause.play();
    // }

    public static void main(String[] args) {
        launch(args);
    }
}
