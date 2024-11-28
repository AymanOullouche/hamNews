package com.hamNews.Views;

import com.hamNews.Controler.UserController;
import com.hamNews.Model.User.User;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginView extends Application {

    private UserController userController = new UserController();
    private HBox mainLayout;
    private VBox loginFields;
    private VBox signUpFields;

    private String MessageErreur;

    private HBox errorHBox;

    @Override
    public void start(Stage primaryStage) {
        showLoginInterface(primaryStage);
    }

    public void showLoginInterface(Stage primaryStage) {
        // Configuration des champs de connexion
        Text welcomeText = new Text("Connexion");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        welcomeText.setFill(Color.web("#2c3e50"));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        Button loginButton = new Button("Se connecter");
        loginButton
                .setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        loginButton.setPrefWidth(200);
        loginButton.setFocusTraversable(true);
        // Image Danger = new Image("com/hamNews/Views/images/alerte.png");
        Image Danger = new Image(getClass().getResource("/com/hamNews/Views/images/alerte.png").toExternalForm());
        ImageView dangericon = new ImageView(Danger);
        dangericon.setFitWidth(20);
        dangericon.setFitHeight(20);

        Label MessageE = new Label("");
        MessageE.setTextFill(Color.RED);

        errorHBox = new HBox(5, dangericon, MessageE);
        errorHBox.setAlignment(Pos.CENTER);
        errorHBox.setVisible(false);
        loginButton.requestFocus();
        loginButton.setOnAction(e -> {

            handleLogin(emailField, passwordField);
            MessageE.setText(MessageErreur);
        });

        double radius = 50;
        Arc arcWhite = new Arc(0, 0, radius, radius, 90, 180);
        arcWhite.setFill(Color.web("#2980b9"));
        arcWhite.setType(ArcType.ROUND);

        StackPane leftCirclePane = new StackPane(arcWhite);
        leftCirclePane.setAlignment(Pos.CENTER_RIGHT);
        leftCirclePane.setTranslateX(24);
        leftCirclePane.setTranslateY(-53);
        leftCirclePane.setPrefWidth(radius);

        loginFields = new VBox(15, welcomeText, emailField, passwordField, loginButton, leftCirclePane, errorHBox);
        loginFields.setAlignment(Pos.CENTER);
        loginFields.setStyle("-fx-background-color: white; -fx-padding: 20;");
        loginFields.setPrefWidth(575);

        Text signUpText = new Text("Vous ne possédez pas encore un compte ?");
        signUpText.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        signUpText.setFill(Color.WHITE);

        Button signUpButton = new Button("Rejoindre nous !");
        signUpButton
                .setStyle("-fx-background-color: white; -fx-text-fill: #2980b9; -fx-font-size: 14px; -fx-padding: 10;");
        signUpButton.setPrefWidth(200);

        signUpButton.setOnAction(e -> {
            openSignUpFormWithAnimation();

        });

        Arc arcBlue = new Arc(0, 0, radius, radius, 270, 180);
        arcBlue.setFill(Color.WHITE);
        arcBlue.setType(ArcType.ROUND);

        StackPane rightCirclePane = new StackPane(arcBlue);
        rightCirclePane.setAlignment(Pos.CENTER_LEFT);
        rightCirclePane.setTranslateX(-21);
        rightCirclePane.setPrefWidth(radius);

        VBox signUpContainer = new VBox(15, signUpText, signUpButton, rightCirclePane);
        signUpContainer.setAlignment(Pos.CENTER);
        signUpContainer.setStyle("-fx-background-color: #2980b9; -fx-padding: 20;");
        signUpContainer.setPrefWidth(575);

        mainLayout = new HBox(loginFields, signUpContainer);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(mainLayout, 1150, 550);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(TextField emailField, PasswordField passwordField) {
        System.out.println("login");

        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            MessageErreur = "Veuillez remplir tous les champs.";
            errorHBox.setVisible(true);

            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            MessageErreur = "Veuillez entrer une adresse email valide.";
            errorHBox.setVisible(true);

            return;
        }

        if (password.length() < 8) {
            MessageErreur = "Le mot de passe doit contenir au moins 8 caractères.";
            errorHBox.setVisible(true);

            return;
        }
        UserController userController = new UserController();
        User user = userController.loginUser(email, password);

        if (user != null) {
            openHome();
        } else {
            MessageErreur = "Email ou mot de passe incorrect !";
            errorHBox.setVisible(true);

        }
    }

    private void openSignUpForm() {
        Text signUpTitle = new Text("Inscription");
        signUpTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        signUpTitle.setFill(Color.web("#2c3e50"));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom");
        nameField.setMaxWidth(300);
        nameField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Prénom");
        surnameField.setMaxWidth(300);
        surnameField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        PasswordField signUpPasswordField = new PasswordField();
        signUpPasswordField.setPromptText("Mot de passe");
        signUpPasswordField.setMaxWidth(300);
        signUpPasswordField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        PasswordField signUpCPasswordField = new PasswordField();
        signUpCPasswordField.setPromptText("Confirmer le mot de passe");
        signUpCPasswordField.setMaxWidth(300);
        signUpCPasswordField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        Button registerButton = new Button("S'inscrire");
        registerButton
                .setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        registerButton.setPrefWidth(200);

        // Image Danger = new Image("com/hamNews/Views/images/alerte.png");
        Image Danger = new Image(getClass().getResource("/com/hamNews/Views/images/alerte.png").toExternalForm());
        ImageView dangericon = new ImageView(Danger);
        dangericon.setFitWidth(20);
        dangericon.setFitHeight(20);

        Label MessageE = new Label("");

        MessageE.setTextFill(Color.RED);

        errorHBox = new HBox(5, dangericon, MessageE);
        errorHBox.setAlignment(Pos.CENTER);
        errorHBox.setVisible(false);

        signUpFields = new VBox(15, signUpTitle, nameField, surnameField, emailField, signUpPasswordField,
                signUpCPasswordField, registerButton, MessageE, errorHBox);
        signUpFields.setAlignment(Pos.CENTER);
        signUpFields.setStyle("-fx-background-color: white; -fx-padding: 20;");
        signUpFields.setPrefWidth(575);

        mainLayout.getChildren().set(0, signUpFields);

        registerButton.setOnAction(e -> {
            handleRegister(nameField, surnameField, emailField, signUpPasswordField, signUpCPasswordField);
            MessageE.setText(MessageErreur);

        });
    }

    private void handleRegister(TextField firstNameField, TextField lastNameField, TextField emailField,
            PasswordField passwordField, PasswordField confirmPasswordField) {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {

            MessageErreur = "Veuiller remplir les champs Vide";
            errorHBox.setVisible(true);
            return;
        }

        if (!firstName.matches("[A-Za-z0-9_@]+") || !lastName.matches("[A-Za-z0-9_@]+")) {
            MessageErreur = "Le nom et le prénom ne doivent contenir que des lettres, chiffres, _ ou @.";
            errorHBox.setVisible(true);

            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            MessageErreur = "Veuillez entrer une adresse email valide.";
            errorHBox.setVisible(true);

            return;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            MessageErreur = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.";
            errorHBox.setVisible(true);

            return;
        }

        if (!password.equals(confirmPassword)) {

            MessageErreur = "Les mots de passe ne correspondent pas";
            errorHBox.setVisible(true);

            return;
        }

        userController = new UserController();
        boolean success = userController.registerUser(firstName, lastName, password, email);

        if (success) {
            reverseAnimation();
        } else {
        }
    }

    private void openSignUpFormWithAnimation() {
        openSignUpForm();

        signUpFields.setTranslateX(600);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), signUpFields);
        transition.setFromX(600);
        transition.setToX(0);

        transition.play();
    }

    private void reverseAnimation() {
        signUpFields.setTranslateX(0);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), signUpFields);
        transition.setFromX(0);
        transition.setToX(600);

        transition.setOnFinished(e -> showLoginInterface((Stage) mainLayout.getScene().getWindow()));

        transition.play();
    }

    private void openHome() {
        System.out.println("Ouverture de l'interface Login...");
        ArticleListView articleListView = new ArticleListView();
        Stage articleListStage = new Stage();
        articleListView.start(articleListStage);
        articleListStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
