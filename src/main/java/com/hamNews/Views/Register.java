package com.hamNews.Views;

import com.hamNews.Controler.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Register {
    private String MessageErreur;
    private HBox errorHBox;
    private Label MessageE = new Label("");
    private ImageView dangericon;
    private UserController userController = new UserController();
    private  TextField nameField;
    private  TextField surnameField;
    private  TextField EmailField;
    private  PasswordField SignUpPasswordField;
    private  PasswordField SignUpCPasswordField;






    public VBox openSignUpForm() {
        Text signUpTitle=new Text("Inscription");
        signUpTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        signUpTitle.setFill(Color.web("#2c3e50"));

        nameField = new TextField();
        nameField.setPromptText("Nom");
        nameField.setMaxWidth(300);
        nameField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #5271ff; -fx-background-color: #ecf0f1;");

        surnameField = new TextField();
        surnameField.setPromptText("Prénom");
        surnameField.setMaxWidth(300);
        surnameField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #5271ff; -fx-background-color: #ecf0f1;");

        EmailField = new TextField();
        EmailField.setPromptText("Email");
        EmailField.setMaxWidth(300);
        EmailField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        SignUpPasswordField = new PasswordField();
        SignUpPasswordField.setPromptText("Mot de passe");
        SignUpPasswordField.setMaxWidth(300);
        SignUpPasswordField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        SignUpCPasswordField = new PasswordField();
        SignUpCPasswordField.setPromptText("Confirmer le mot de passe");
        SignUpCPasswordField.setMaxWidth(300);
        SignUpCPasswordField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #2980b9; -fx-background-color: #ecf0f1;");

        Button registerButton = new Button("S'inscrire");
        registerButton.setStyle("-fx-background-color: #5271ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        registerButton.setPrefWidth(200);

        Image Danger = new Image(getClass().getResource("/com/hamNews/Views/images/alerte.png").toExternalForm());
        dangericon = new ImageView(Danger);
        dangericon.setFitWidth(20);
        dangericon.setFitHeight(20);

        MessageE.setTextFill(Color.RED);

        errorHBox = new HBox(5, dangericon, MessageE);
        errorHBox.setAlignment(Pos.CENTER);
        errorHBox.setVisible(false);

        VBox signUpFields = new VBox(15, signUpTitle, nameField, surnameField, EmailField, SignUpPasswordField,
                SignUpCPasswordField, registerButton, MessageE, errorHBox);
        signUpFields.setAlignment(Pos.CENTER);
        signUpFields.setStyle("-fx-background-color: white; -fx-padding: 20;");
        signUpFields.setPrefWidth(575);


        registerButton.setOnAction(e -> {
            handleRegister(nameField, surnameField, EmailField, SignUpPasswordField, SignUpCPasswordField);
            MessageE.setText(MessageErreur);

        });
        return signUpFields;
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
            MessageErreur = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, \n un chiffre et un caractère spécial.";
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

            MessageErreur = "votre compte est crée avec succées , vous pouvez connecter !";

             MessageE.setText(MessageErreur);
            MessageE.setTextFill(Color.GREEN);
            dangericon.setVisible(false);

            nameField.setText("");
            surnameField.setText("");
            EmailField.setText("");
            SignUpPasswordField.setText("");
            SignUpCPasswordField.setText("");



        } else {
        }
    }
}
