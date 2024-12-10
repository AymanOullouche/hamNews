package com.hamNews.Views;

import com.hamNews.Controler.UserController;
import com.hamNews.Model.User.User;
import com.hamNews.Model.DB.Session;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileSettingsView extends Application {
    private List<String> favoriteCategories = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    User theUser = Session.getLoggedInUser();


    @Override
    public void start(Stage primaryStage) {

//
    }

    public ScrollPane getProfileSettings(){

        // Initialize categories
        initializeCategories();

        // Main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #ffffff;");

        // Title
        Label titleLabel = new Label("Profil et paramètres");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #000;");
        titleLabel.setAlignment(Pos.CENTER);

        // User Profile and Settings Cards
        VBox profileCard = createProfileCard();
        VBox settingsCard = createSettingsCard();

        mainContainer.getChildren().addAll(titleLabel, profileCard, settingsCard);
        VBox passwordChangeCard = createPasswordChangeCard();
        mainContainer.getChildren().add(passwordChangeCard);

// Wrap the main container in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true); // Ensure the content stretches horizontally to fit the ScrollPane
        scrollPane.setPannable(true); // Allow scrolling by dragging the mouse
        scrollPane.setStyle("-fx-background: #f8f9fa;");

        return scrollPane;
    }

    private void initializeCategories() {
        categories.add(new Category("Economie"));
        categories.add(new Category("Sports"));
        categories.add(new Category("Automobile"));
        categories.add(new Category("Societé"));
        categories.add(new Category("Monde"));
        categories.add(new Category("Emploie"));
    }
    private String validateProfileForm(String firstName, String lastName, String email) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            return "Veuillez remplir tous les champs.";
        }

        if (!firstName.matches("[A-Za-z0-9_@]+") || !lastName.matches("[A-Za-z0-9_@]+")) {
            return "Le nom et le prénom ne doivent contenir que des lettres, chiffres, _ ou @.";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Veuillez entrer une adresse email valide.";
        }

        return null; // Aucun problème
    }
    //    private String validatePassword(String password) {
//        if (password.isEmpty()) {
//            return "Veuillez remplir tous les champs.";
//        }
//
//        if (!password.matches("[A-Za-z0-9_@]+")) {
//            return "Le mot de passe  ne doivent contenir que des lettres, chiffres, _ ou @.";
//        }
//
//        return null;
//    }
    private VBox createProfileCard() {
        VBox profileCard = new VBox(15);
        profileCard.setPadding(new Insets(20));
        profileCard.setStyle(getCardStyle());

        Label titleLabel = new Label("Profil d'utilisateur");
        styleTitleLabel(titleLabel);

        VBox profileContent = new VBox(15);

        User loggedInUser = Session.getLoggedInUser();
        String firstName = loggedInUser != null ? loggedInUser.getFirstName() : "";
        String lastName = loggedInUser != null ? loggedInUser.getLastName() : "";
        String email = loggedInUser != null ? loggedInUser.getEmail() : "";

        Label firstNameLabel = new Label("Nom");
        styleLabel(firstNameLabel);
        TextField firstNameField = createTextField(firstName);

        Label lastNameLabel = new Label("Prénom");
        styleLabel(lastNameLabel);
        TextField lastNameField = createTextField(lastName);

        Label emailLabel = new Label("Email");
        styleLabel(emailLabel);
        TextField emailField = createTextField(email);

        Button saveButton = new Button("Mettre à jour le profil");
        stylePrimaryButton(saveButton);

        // Bouton avec vérification et mise à jour
        saveButton.setOnAction(event -> {
            String updatedFirstName = firstNameField.getText();
            String updatedLastName = lastNameField.getText();
            String updatedEmail = emailField.getText();

            String validationError = validateProfileForm(updatedFirstName, updatedLastName, updatedEmail);
            if (validationError != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText(validationError);
                alert.showAndWait();
                return;
            }

            if (loggedInUser != null) {
                int userId = loggedInUser.getUserId();
                UserController userController = new UserController();

                boolean isUpdated = userController.updateUser(userId, updatedFirstName, updatedEmail, updatedLastName);
                Alert alert = new Alert(isUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setTitle(isUpdated ? "Succès" : "Erreur");
                alert.setHeaderText(null);
                alert.setContentText(isUpdated
                        ? "Profil mis à jour avec succès !"
                        : "Échec de la mise à jour. Veuillez réessayer.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur connecté. Veuillez d'abord vous connecter.");
                alert.showAndWait();
            }
        });

        HBox saveButtonContainer = new HBox(saveButton);
        saveButtonContainer.setAlignment(Pos.CENTER_RIGHT);

        profileContent.getChildren().addAll(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                emailLabel, emailField,
                saveButtonContainer
        );

        profileCard.getChildren().addAll(titleLabel, profileContent);

        return profileCard;
    }



    // Method to style the title label
    private void styleTitleLabel(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        label.setAlignment(Pos.CENTER);
    }


    private VBox createSettingsCard() {

        User theUser = Session.getLoggedInUser();
//        int idUser;
//        if (theUser != null) {
//             idUser = theUser.getUserId();
//        }
        System.out.println("the is is here " + theUser.getUserId());
        favoriteCategories = UserController.getUserCategories(theUser.getUserId());

        // Main container for the settings card
        VBox settingsCard = new VBox(15);
        settingsCard.setPadding(new Insets(20));
        settingsCard.setStyle(getCardStyle());

        // Title of the settings card
        Label titleLabel = new Label("Paramètres de l'application");
        styleTitleLabel(titleLabel); // Apply specific styling for the title

        // Settings content
        VBox settingsContent = new VBox(15);

        // Favorite Categories Label
        Label favCategoriesLabel = new Label("Catégories préférées");
        styleLabel(favCategoriesLabel);

        // Favorite Categories Display
        HBox favCategoriesDisplay = new HBox(10);
        favCategoriesDisplay.setAlignment(Pos.CENTER_LEFT);
        updateFavoriteCategoriesDisplay(favCategoriesDisplay);

        // Add Category Dropdown
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Ajouter une catégorie favorite");
        updateCategoryComboBox(categoryComboBox);

        categoryComboBox.setOnAction(event -> {
            String selectedCategory = categoryComboBox.getValue();
            if (selectedCategory != null) {
                addFavoriteCategory(selectedCategory);
                updateFavoriteCategoriesDisplay(favCategoriesDisplay);
                updateCategoryComboBox(categoryComboBox);
            }
        });

        // Add all settings elements to the content container
        settingsContent.getChildren().addAll(
                favCategoriesLabel,
                favCategoriesDisplay,
                categoryComboBox
        );
        Button saveButton = new Button("Mettre à jour les catégories");
        stylePrimaryButton(saveButton);
        saveButton.setOnAction(event -> {
            User loggedInUser = Session.getLoggedInUser();

            if (loggedInUser != null) {
                // Update the user's favorite categories
                UserController userController = new UserController();
                boolean isUpdated = userController.updateUserCategories(loggedInUser.getUserId(), favoriteCategories);

                // Show feedback alert
                Alert alert = new Alert(isUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setTitle(isUpdated ? "Success" : "Error");
                alert.setHeaderText(null);
                alert.setContentText(isUpdated
                        ? "Catégories favorites mises à jour avec succès !"
                        : "Impossible de mettre à jour les catégories favorites. Veuillez réessayer.");
                alert.showAndWait();
            } else {
                // Show error if no user is logged in
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur connecté n'a été trouvé. Veuillez d'abord vous connecter");
                alert.showAndWait();
            }
        });
        HBox saveButtonContainer = new HBox(saveButton);
        saveButtonContainer.setAlignment(Pos.CENTER_RIGHT);
        // Add all form elements to the content container
        settingsContent.getChildren().addAll(
                saveButtonContainer
        );

        // Add title and content to the main card
        settingsCard.getChildren().addAll(titleLabel, settingsContent);

        return settingsCard;
    }
    private VBox createPasswordChangeCard() {
        // Container principal pour le changement de mot de passe
        VBox passwordCard = new VBox(15);
        passwordCard.setPadding(new Insets(20));
        passwordCard.setStyle(getCardStyle()); // Utiliser le même style de carte

        // Titre
        Label titleLabel = new Label("Changer le mot de passe");
        styleTitleLabel(titleLabel); // Utiliser le même style de titre

        // Contenu du formulaire
        VBox passwordContent = new VBox(15);

        // Label pour le champ de mot de passe
        Label passwordLabel = new Label("Nouveau mot de passe");
        styleLabel(passwordLabel); // Utiliser le style des labels

        // Champ de saisie pour le mot de passe
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez un nouveau mot de passe");
        styleField(passwordField);

        // Label pour indiquer la force du mot de passe
        Label passwordStrengthLabel = new Label();
        styleLabel(passwordStrengthLabel);

        // Ajouter un gestionnaire d'événements pour le champ de mot de passe
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            String strength = evaluatePasswordStrength(newValue);
            passwordStrengthLabel.setText("Strength: " + strength);
            passwordStrengthLabel.setStyle(getStrengthLabelStyle(strength));
        });

        // Indications pour le mot de passe
        Label passwordGuidelines = new Label(
                "Password requirements:\n" +
                        "- At least 8 characters\n" +
                        "- Includes uppercase and lowercase letters\n" +
                        "- Includes a number\n" +
                        "- Includes a special character (e.g., @, #, $)"
        );
        styleLabel(passwordGuidelines);

        // Bouton pour sauvegarder le nouveau mot de passe
        Button savePasswordButton = new Button("Mettre à jour le mot_passe");
        stylePrimaryButton(savePasswordButton);

//        HBox saveButtonContainer = new HBox(savePasswordButton);
//        saveButtonContainer.setAlignment(Pos.CENTER_RIGHT);
        // Ajouter le bouton dans le conteneur

// Ajouter le conteneur à la liste
        // passwordContent.getChildren().add(saveButtonContainer);

        // Add all form elements to the content container
//        passwordContent.getChildren().addAll(
//                savePasswordButton
//        );

        // Action du bouton "Update Password"
        savePasswordButton.setOnAction(event -> {
            String newPassword = passwordField.getText();

            // Vérification que le champ n'est pas vide
            if (newPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Le mot de passe ne peut pas être vide !");
                return;
            }

            // Vérification que le mot de passe est complexe
            if (!isPasswordComplex(newPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Le mot de passe ne répond pas aux exigences!");
                return;
            }

            // Récupérer l'utilisateur connecté
            User loggedInUser = Session.getLoggedInUser();

            if (loggedInUser != null) {
                int userId = loggedInUser.getUserId();
                UserController userController = new UserController();

                // Appeler la méthode pour mettre à jour le mot de passe
                boolean isUpdated = userController.updatePassword(userId, newPassword);

                // Afficher une alerte en fonction du résultat
                showAlert(
                        isUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                        isUpdated ? "Success" : "Error",
                        isUpdated ? "Mot de passe mis à jour avec succès !" : "Impossible de mettre à jour le mot de passe. Veuillez réessayer."
                );

                // Si le mot de passe a été mis à jour, vider le champ
                if (isUpdated) {
                    passwordField.clear();
                    passwordStrengthLabel.setText("");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No logged-in user found. Please log in first.");
            }
        });
//        HBox saveButtonContainer = new HBox(savePasswordButton);
//        saveButtonContainer.setAlignment(Pos.CENTER_RIGHT);
        // Ajouter les composants au conteneur
        HBox saveButtonContainer = new HBox();
        saveButtonContainer.setAlignment(Pos.CENTER_RIGHT); // Aligner le contenu à droite
        saveButtonContainer.getChildren().add(savePasswordButton);
        passwordContent.getChildren().addAll(
                passwordLabel,
                passwordField,
                passwordStrengthLabel,
                passwordGuidelines,
                saveButtonContainer
        );

        // Ajouter le titre et le contenu au conteneur principal
        passwordCard.getChildren().addAll(titleLabel, passwordContent);

        return passwordCard;
    }

    /**
     * Évalue la force du mot de passe.
     */
    private String evaluatePasswordStrength(String password) {
        if (password.length() >= 12 && password.matches(".[A-Z].") && password.matches(".[a-z].")
                && password.matches(".\\d.") && password.matches(".[@#$%^&+=!].")) {
            return "Strong";
        } else if (password.length() >= 8 && password.matches(".[A-Z].")
                && password.matches(".[a-z].") && password.matches(".\\d.")) {
            return "Medium";
        } else {
            return "Weak";
        }
    }

    /**
     * Vérifie si le mot de passe est complexe.
     */
    private boolean isPasswordComplex(String password) {
        return password.length() >= 8 &&
                password.matches(".[A-Z].") &&
                password.matches(".[a-z].") &&
                password.matches(".\\d.") &&
                password.matches(".[@#$%^&+=!].");
    }

    /**
     * Renvoie le style pour le label de force du mot de passe.
     */
    private String getStrengthLabelStyle(String strength) {
        switch (strength) {
            case "Strong":
                return "-fx-text-fill: green;";
            case "Medium":
                return "-fx-text-fill: orange;";
            default:
                return "-fx-text-fill: red;";
        }
    }

    /**
     * Affiche une alerte avec un type, un titre et un message donnés.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Method to style the title label
    private void styleTitleLabel1(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        label.setAlignment(Pos.CENTER);
    }


    private void updateFavoriteCategoriesDisplay(HBox favCategoriesDisplay) {
        favCategoriesDisplay.getChildren().clear();
        for (String category : favoriteCategories) {
            Label categoryLabel = new Label(category);
            categoryLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 5px 10px; -fx-border-radius: 10px 0 0 10px; -fx-background-radius: 10px 0 0 10px; -fx-text-fill: #333;");

            Button removeButton = new Button("✖");
            removeButton.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 5px 10px; -fx-border-radius: 0 10px 10px 0; -fx-background-radius: 0 10px 10px 0; -fx-text-fill: #000; -fx-font-size: 12px;");
            removeButton.setOnAction(e -> {
                removeFavoriteCategory(category);
                updateFavoriteCategoriesDisplay(favCategoriesDisplay);
            });

            HBox categoryChip = new HBox(0, categoryLabel, removeButton);
            categoryChip.setAlignment(Pos.CENTER_LEFT);
            favCategoriesDisplay.getChildren().add(categoryChip);
        }
    }

    private void updateCategoryComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(
                categories.stream()
                        .map(Category::getName)
                        .filter(name -> !favoriteCategories.contains(name))
                        .collect(Collectors.toList())
        );
    }

    private void addFavoriteCategory(String category) {
        if (!favoriteCategories.contains(category)) {
            favoriteCategories.add(category);
        }
    }

    private void removeFavoriteCategory(String category) {
        favoriteCategories.remove(category);
    }

    private TextField createTextField(String placeholder) {
        TextField textField = new TextField(placeholder);
        styleField(textField);
        return textField;
    }

    private void styleField(Control field) {
        field.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 8px;");
    }

    private void stylePrimaryButton(Button button) {
        button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"));
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
    }

    private void styleCardContainer(VBox container, String titleText) {
        Label title = new Label(titleText);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 0 0 10px 0;");
        container.getChildren().add(0, title);
        container.setSpacing(10);
    }

    private String getCardStyle() {
        return "-fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-radius: 8px; -fx-background-radius: 8px;";
    }

    private static class Category {
        private String name;

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
