package com.hamNews.Controler;

import com.hamNews.Model.Article.Article;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.DB.DatabaseConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleController {

    public List<Article> scrapeArticleGrid(String url, int lastFetchedArticleId) throws IOException {
        List<Article> articles = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();

        // Select the main article section widget
        for (Element articleElement : doc.select("div.matin-article-section-widget .article")) {

            // Extract title
            String title = articleElement.select("h2 a.article-title").text();
            // Extract URL
            String articleUrl = articleElement.select("h2 a.article-title").attr("href");
            int articleId = extractArticleIdFromUrl(articleUrl);
            if (articleId == lastFetchedArticleId) {
                break;
            }
            // Extract image URL
            String imageUrl = articleElement.select("div.article-image img").attr("data-src");
            // Extract description
            String description = articleElement.select("a.article-body").text();
            // Extract publish date
            String publishDate = articleElement.select("span.publishing-date").text(); // Moved here

            // Create a new Article object with publish date
            articles.add(new Article(title, articleUrl, imageUrl, description, publishDate)); // Include publish date
        }

        return articles;
    }

    public static int extractArticleIdFromUrl(String url) {
        // Split the URL by "/" and retrieve the last part
        String[] urlParts = url.split("/");
        // Get the last part of the URL which should be the article ID
        String articleIdStr = urlParts[urlParts.length - 1];

        // Try to convert it to an integer and return it
        try {
            return Integer.parseInt(articleIdStr);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing article ID from URL: " + url);
            return -1; // Return -1 if the ID cannot be parsed
        }
    }

    public void storeArticle(Article article, String content, String category) {
        String insertArticleSQL = "INSERT INTO Articles (title, description, content, url, image, categories, publishDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertArticleSQL)) {

            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, article.getUrl());
            preparedStatement.setString(5, article.getImageUrl());
            preparedStatement.setString(6, category);
            preparedStatement.setString(7, article.getPublishDate()); // Now taken from Article
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

    public String fetchContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element contentElement = doc.select("div.article-desc").first();

        if (contentElement != null) {
            return contentElement.outerHtml(); // Return content as HTML
        } else {
            return "No content found for the specified selector."; // Fallback content
        }
    }
    public List<ArticleSelect> getArticles() {
        List<ArticleSelect> articles = new ArrayList<>();
        String selectSQL = "SELECT title, description, url, image, categories, publishDate, content, categories FROM Articles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String url = resultSet.getString("url");
                String image = resultSet.getString("image");
                String category = resultSet.getString("categories");
                String content = resultSet.getString("content");
                String publishDate = resultSet.getString("publishDate");

                articles.add(new ArticleSelect(title, url, image, description, publishDate, content, category));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return articles;
    }


}



