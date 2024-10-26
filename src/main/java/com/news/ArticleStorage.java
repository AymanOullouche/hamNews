package com.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleStorage {
    public void storeArticle(Article article, String content, String publishDate, String category) {
        String insertArticleSQL = "INSERT INTO Articles (title, description, content, url, image, categories, publishDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertArticleSQL)) {

            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, article.getUrl());
            preparedStatement.setString(5, article.getImageUrl());
            preparedStatement.setString(6, category);
            preparedStatement.setString(7, publishDate);
            preparedStatement.executeUpdate();
            System.out.println("Article stored successfully: " + article.getTitle());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLastFetchedArticleIdByCategory(String category) {
        String selectSQL = "SELECT lastArticleId FROM LastFetchedByCategory WHERE category = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("lastArticleId");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Return -1 if no last article ID found
    }

    public void updateLastFetchedArticleByCategory(String category, int articleId) {
        String updateSQL = "INSERT INTO LastFetchedByCategory (category, lastArticleId, lastFetchDate) VALUES (?, ?, NOW()) ON DUPLICATE KEY UPDATE lastArticleId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, articleId);
            preparedStatement.setInt(3, articleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
