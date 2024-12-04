package com.hamNews.Controler;

import com.hamNews.Model.DB.DatabaseConnection;
import com.hamNews.Model.DB.Session;
import com.hamNews.Model.User.User;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class UserController {
    private static final String CONFIG_FILE = "login.properties";

    public boolean registerUser(String firstName, String lastName, String password, String email) {
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            return false;
        }

        String insertUserSQL = "INSERT INTO Users (firstName, lastName, password, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, hashedPassword); // Utilisez le mot de passe haché
            preparedStatement.setString(4, email);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public User loginUser(String email, String password) {
        String selectUserSQL = "SELECT * FROM Users WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectUserSQL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");
                String hashedPassword = hashPassword(password);
                if (hashedPassword != null && hashedPassword.equals(storedHashedPassword)) {
                    int userId = resultSet.getInt("userId");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    User user=new User(userId, firstName, lastName, null, email, null, null);

                    Session.setLoggedInUser(user);

                    return user;
                } else {
                    return null;
                }
            } else {

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUser(int userId,String name, String email,String LastName) {
        String query = "UPDATE users SET firstName = ?, email = ?, LastName = ? WHERE userId  = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
           // String hashedPassword = hashPassword(password);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, LastName);
            preparedStatement.setInt(4, userId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserCategories(int userId, List<String> favoriteCategories) {
        String fav = String.join(", ", favoriteCategories);
        String updateQuery = "UPDATE users SET likedCategories = ? WHERE userId  = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Update each category in the list
//            for (String category : favoriteCategories) {
//                updateStmt.setString(1, category);
//                updateStmt.setInt(2, userId);
//                updateStmt.setString(3, category); // Update where category matches
//                updateStmt.addBatch(); // Add to batch for efficiency
//            }
            updateStmt.setString(1, fav);
                updateStmt.setInt(2, userId);
//                updateStmt.setString(3, category); // Update where category matches
updateStmt.addBatch();

            // Execute all updates in batch
            int[] results = updateStmt.executeBatch();
            return results.length > 0; // Return true if updates were executed

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<String> getUserCategories(int userId) {
        List<String> categories = new ArrayList<>();
        String query = "SELECT likedCategories FROM users WHERE userId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Get the likedCategories column as a string
                String likedCategories = resultSet.getString("likedCategories");

                // Check for null and split the string if it's not null
                if (likedCategories != null) {
                    // Split the string by the delimiter (comma) and add to the list
                    String[] categoriesArray = likedCategories.split(",\\s*");
                    Collections.addAll(categories, categoriesArray);
                } else {
                    System.out.println("No liked categories found for userId: " + userId);
                }
            } else {
                System.out.println("No user found with userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    public boolean updatePassword(int userId, String newPassword) {
        try {
            // Connexion à la base de données
            Connection connection = DatabaseConnection.getConnection();

            // Requête SQL pour mettre à jour le mot de passe
            String updateQuery = "UPDATE users SET password = ? WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            String hashedPassword = hashPassword(newPassword);
            // Associer les paramètres (le nouveau mot de passe et l'ID de l'utilisateur)
            preparedStatement.setString(1, hashedPassword); // Cryptage recommandé ici
            preparedStatement.setInt(2, userId);

            // Exécuter la mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Fermer la connexion
            preparedStatement.close();
            connection.close();

            // Vérifier si une ligne a été affectée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logout() {
        Session.logout();
    }

    private String hashPassword (String password){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(password.getBytes());

                StringBuilder hexString = new StringBuilder();
                for (byte b : encodedHash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

    public void saveEmail(String email) {
        Properties properties = new Properties();

        properties.setProperty("email", email);

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmail(TextField emailField,CheckBox rememberMeCheckBox) {
        Properties properties = new Properties();
        File file = new File(CONFIG_FILE);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);

            String email = properties.getProperty("email");

            if (email != null ) {
                emailField.setText(email);
                rememberMeCheckBox.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

