package com.hamNews.Views;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Authentification extends Application {

    private Register registerView;
    private Login loginView;
    private AuthentificationBanner banner;
    private VBox loginForm;
    private VBox registerForm ;
    private HBox mainLayout;


    @Override
    public void start(Stage primaryStage) {


        registerView = new Register();
        loginView = new Login();
        banner=new AuthentificationBanner();

        loginForm = loginView.createLoginInterface();
        registerForm = registerView.openSignUpForm();

        mainLayout =  new HBox(loginForm, banner.ShowAuthenBanner());

        banner.getSignup().setOnAction(e -> openSignUpFormWithAnimation());
        banner.getLogin().setOnAction(e -> reverseAnimation());

        Scene scene = new Scene(mainLayout, 1150, 550);
        primaryStage.setTitle("Authentification");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Image icon = new Image(getClass().getResource("/com/hamNews/Views/images/ConnectPlease.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    public void openSignUpFormWithAnimation() {
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(registerForm, banner.ShowAuthenBanner());
        registerForm.setTranslateX(600);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), registerForm);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    public void reverseAnimation() {
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(loginForm, banner.ShowAuthenBanner());
        loginForm.setTranslateX(600);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), loginForm);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
