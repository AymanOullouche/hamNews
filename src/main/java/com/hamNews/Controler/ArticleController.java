package com.hamNews.Controler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.*;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hamNews.Model.Article.Article;
import com.hamNews.Model.Article.ArticleSelect;
import com.hamNews.Model.DB.DatabaseConnection;

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
            // Convert the image to PNG and save it
            String imageName = saveImageAsPng(imageUrl);
            // Extract description
            String description = articleElement.select("a.article-body").text();
            // Extract publish date
            String publishDate = articleElement.select("span.publishing-date").text(); // Moved here

            // Create a new Article object with publish date
            articles.add(new Article(title, articleUrl, imageUrl, description, publishDate, imageName)); // Include
                                                                                                         // publish date
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

//    public void storeArticle(Article article, String content, String category) {
//        String insertArticleSQL = "INSERT INTO Articles (title, description, content, url, image, categories, imageName) VALUES (?, ?, ?, ?, ?,  ?, ?)";
//
//        try (Connection connection = DatabaseConnection.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(insertArticleSQL)) {
//
//            preparedStatement.setString(1, article.getTitle());
//            preparedStatement.setString(2, article.getDescription());
//            preparedStatement.setString(3, content);
//            preparedStatement.setString(4, article.getUrl());
//            preparedStatement.setString(5, article.getImageUrl());
//            preparedStatement.setString(6, category);
////            preparedStatement.setString(7, );
//            preparedStatement.setString(7, article.getImageName());
//            preparedStatement.executeUpdate();
//            System.out.println("Article stored successfully: " + article.getTitle());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
private String formatContent(String content) {
    // Ne pas remplacer si le point est après "M", "Mme", ou "Mlle"
    return content.replaceAll("(?<!\\b(M|Mme|Mlle))\\.\\s*", ".\n");
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
            // Extraire le contenu brut
            String rawContent = contentElement.text();
            // Appliquer le formatage
            return formatContent(rawContent);
        } else {
            return "No content found for the specified selector.";
        }
    }
    public void storeArticle(Article article, String content, String category) {
        String formattedContent = formatContent(content); // Formater le contenu
        String insertArticleSQL = "INSERT INTO Articles (title, description, content, url, image, categories, imageName) VALUES (?, ?, ?, ?, ?,  ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertArticleSQL)) {

            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, formattedContent); // Utiliser le contenu formaté
            preparedStatement.setString(4, article.getUrl());
            preparedStatement.setString(5, article.getImageUrl());
            preparedStatement.setString(6, category);
            preparedStatement.setString(7, article.getImageName());
            preparedStatement.executeUpdate();
            System.out.println("Article stored successfully: " + article.getTitle());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ArticleSelect> getArticles() {
        List<ArticleSelect> articles = new ArrayList<>();
        String selectSQL = "SELECT title, description, url, image, categories, publishDate, content, categories, imageName FROM Articles";

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
                String imageName = resultSet.getString("imageName");

                articles.add(
                        new ArticleSelect(title, url, image, description, publishDate, content, category, imageName));

            }
            Collections.reverse(articles);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return articles;
    }

    private String getFileNameFromURL(String url) {
        // Extract the file name from the URL
        String fileName = url.substring(url.lastIndexOf("/") + 1);

        // Remove the extension if present
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            fileName = fileName.substring(0, dotIndex);
        }

        return fileName;
    }

//    private String saveImageAsPng(String imageUrl) throws IOException {
//        String imageName = getFileNameFromURL(imageUrl);
//        String uniqueImageName = "image_" + imageName + ".png";
//
//        // Create the save directory if it does not exist
//        Path directoryPath = Paths.get("src/main/resources/com/hamNews/Views/images/");
//        if (!Files.exists(directoryPath)) {
//            Files.createDirectories(directoryPath);
//        }
//
//        // Create the local file path for the image with .png extension
//        Path savePath = Paths.get("src/main/resources/com/hamNews/Views/images/", uniqueImageName);
//
//        // Open an input stream to the image URL and save it to the file
//        try (InputStream in = new URL(imageUrl).openStream()) {
//            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
//        }
//
//        // Return the local path to the saved image
//        return uniqueImageName;
//    }
public String saveImageAsPng(String imageUrl) throws IOException {
    String imageName = getFileNameFromURL(imageUrl);
    String uniqueImageName = "image_" + imageName + ".png";

    // Create the save directory if it does not exist
    Path directoryPath = Paths.get("src/main/resources/com/hamNews/Views/images/");
    if (!Files.exists(directoryPath)) {
        Files.createDirectories(directoryPath);
    }

    // Create the local file path for the image with .png extension
    Path savePath = Paths.get("src/main/resources/com/hamNews/Views/images/", uniqueImageName);

    // Open an input stream to the image URL and read it into a BufferedImage
    BufferedImage originalImage = null;
    try (InputStream in = new URL(imageUrl).openStream()) {
        originalImage = ImageIO.read(in); // ImageIO will now support WebP if TwelveMonkeys is added
    }

    // Check if the image was successfully loaded
    if (originalImage == null) {
        throw new IOException("Failed to load image from URL: " + imageUrl);
    }

    // Calculate the new height to maintain the aspect ratio
    int newWidth = 400;
    int newHeight = (int) ((double) originalImage.getHeight() / originalImage.getWidth() * newWidth);

    // Resize the image
    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();
    g2d.drawImage(scaledImage, 0, 0, null);
    g2d.dispose();

    // Save the resized image as PNG
    ImageIO.write(resizedImage, "PNG", savePath.toFile());

    // Return the local path to the saved image
    return uniqueImageName;
}

    public static void convertWebPToPng(String webpUrl, String outputPath) throws IOException {
        // Download the WebP image
        InputStream in = new URL(webpUrl).openStream();
        File tempFile = File.createTempFile("tempImage", ".webp");
        try (OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }

        // Use ProcessBuilder to call cwebp to convert the WebP to PNG
        ProcessBuilder processBuilder = new ProcessBuilder("cwebp", tempFile.getAbsolutePath(), "-o", outputPath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Error during WebP to PNG conversion. Exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException("Error during WebP to PNG conversion", e);
        } finally {
            tempFile.delete();
        }
    }


}
