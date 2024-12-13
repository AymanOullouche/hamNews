package com.hamNews.Views;

import com.hamNews.Controler.UserController;
import com.hamNews.Model.User.User;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {

    private HBox errorHBox;
    private String MessageErreur;
    private UserController userController = new UserController();

    public VBox createLoginInterface() {
        Text welcomeText = new Text("Connexion");
        welcomeText.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 26));
        welcomeText.setFill(Color.web("#2c3e50"));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #5271ff; -fx-background-color: #ecf0f1;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setStyle(
                "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #5271ff; -fx-background-color: #ecf0f1;");

        CheckBox rememberMeCheckBox = new CheckBox("Se souvenir de moi ");

        Button logBtton = new Button("Se connecter");
        logBtton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        Label MessageE = new Label("");
        MessageE.setTextFill(Color.RED);
        logBtton.setPrefWidth(200);
        logBtton.setOnAction(e -> {
            handleLogin(emailField, passwordField, rememberMeCheckBox); // Modification ici pour passer la case à cocher
            MessageE.setText(MessageErreur);
        });

        Image Danger = new Image(getClass().getResource("/com/hamNews/Views/images/alerte.png").toExternalForm());
        ImageView dangericon = new ImageView(Danger);
        dangericon.setFitWidth(20);
        dangericon.setFitHeight(20);

        errorHBox = new HBox(5, dangericon, MessageE);
        errorHBox.setAlignment(Pos.CENTER);
        errorHBox.setVisible(false);

        double radius = 50;
        Arc arcWhite = new Arc(0, 0, radius, radius, 90, 180);
        arcWhite.setFill(Color.web("#5271ff"));
        arcWhite.setType(ArcType.ROUND);

        StackPane leftCirclePane = new StackPane(arcWhite);
        leftCirclePane.setAlignment(Pos.CENTER_RIGHT);
        leftCirclePane.setTranslateX(24);
        leftCirclePane.setTranslateY(-53);
        leftCirclePane.setPrefWidth(radius);

        VBox loginFields = new VBox(20, welcomeText, emailField, passwordField, rememberMeCheckBox, logBtton, errorHBox,
                leftCirclePane);
        loginFields.setAlignment(Pos.CENTER);
        loginFields.setStyle("-fx-background-color: white; -fx-padding: 20;");
        loginFields.setPrefWidth(575);

        userController.loadEmail(emailField, rememberMeCheckBox);

        return loginFields;
    }

    private void handleLogin(TextField emailField, PasswordField passwordField, CheckBox rememberMeCheckBox) {
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
        if (!password.matches("[A-Za-z0-9_@]+")) {
            MessageErreur = "Le mot de passe  ne doivent contenir que des lettres, chiffres, _ ou @.";
            errorHBox.setVisible(true);

            return;
        }

        if (password.length() < 8) {
            MessageErreur = "Le mot de passe doit contenir au moins 8 caractères.";
            errorHBox.setVisible(true);
            return;
        }

        User user = userController.loginUser(email, password);

        if (user != null) {
            if (rememberMeCheckBox.isSelected()) {
                userController.saveEmail(email);
            }
            openHome();
        } else {
            MessageErreur = "Email ou mot de passe incorrect !";
            errorHBox.setVisible(true);
        }
    }

    private void openHome() {
        WindowManager.closeAllWindows();
        Dashboard articleListView = new Dashboard();
        Stage articleListStage = new Stage();
        articleListView.start(articleListStage);
        articleListStage.show();
    }

}
