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

