package com.hamNews.Controler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.hamNews.Model.Article.ArticleSelect;

public class DownloadController {

    private static final Logger LOGGER = Logger.getLogger(DownloadController.class.getName());

    // List to store downloaded articles
    private List<ArticleSelect> downloadedArticles = new ArrayList<>();

    /**
     * Downloads the article and saves its data in a JSON file, as well as the image
     * locally.
     * 
     * @param article The article to download and save.
     * @throws IOException If an error occurs during the download or file saving
     *                     process.
     */
    public void downloadArticle(ArticleSelect article) throws IOException {
        // Mark the article as downloaded
        article.setDownloaded(!article.isDownloaded());

        if (article.isDownloaded()) {
            // Save article data as JSON
            saveArticleAsJson(article);

            // Download the article image
            String imageUrl = article.getImageUrl(); // Get the image URL from the article
            String imageSaveDirectory = "resources/images"; // Directory where images will be saved
            String localImagePath = downloadImage(imageUrl, imageSaveDirectory);

            // Log the image path (optional)
            System.out.println("Image saved to: " + localImagePath);

            // Add the article to the list of downloaded articles
            downloadedArticles.add(article);

            // Update the entire articles list as JSON
            saveAllArticlesAsJson();
        } else {
            // If the article is not downloaded, revert the download state
            // Example: updateButtonGraphic("Download");
        }
    }

    /**
     * Serializes the article data to a JSON file and saves it locally.
     * 
     * @param article The article to save as JSON.
     * @throws IOException If an error occurs during the file writing process.
     */
    private void saveArticleAsJson(ArticleSelect article) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(article); // Serialize the article object to JSON

        // Create a directory to save the JSON if it doesn't exist
        String jsonDirectory = "local_articles"; // You can change this directory
        Path directoryPath = Paths.get(jsonDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Define the JSON file path (you could use the article title or URL for
        // uniqueness)
        String jsonFileName = article.getTitle().replaceAll("\\s+", "_") + "_data.json"; // Use title to create a unique
                                                                                         // file name
        Path jsonFilePath = Paths.get(jsonDirectory, jsonFileName);

        // Write the JSON data to the file
        try (BufferedWriter writer = Files.newBufferedWriter(jsonFilePath)) {
            writer.write(json);
        }

    }

    /**
     * Saves all downloaded articles into a single JSON file.
     * 
     * @throws IOException If an error occurs during the file writing process.
     */
    private void saveAllArticlesAsJson() throws IOException {
        // Serialize the list of all downloaded articles to JSON
        Gson gson = new Gson();
        String json = gson.toJson(downloadedArticles); // Convert the list of articles to JSON

        // Create a directory to save the JSON if it doesn't exist
        String jsonDirectory = "local_articles"; // You can change this directory
        Path directoryPath = Paths.get(jsonDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Define the file path for the all articles JSON file
        Path jsonFilePath = Paths.get(jsonDirectory, "all_articles_data.json");

        // Write the JSON data (all articles) to the file
        try (BufferedWriter writer = Files.newBufferedWriter(jsonFilePath)) {
            writer.write(json);
        }

        System.out.println("All articles data saved to: " + jsonFilePath);
    }

    /**
     * Downloads an image from the provided URL and saves it locally as PNG.
     * 
     * @param imageUrl      The URL of the image.
     * @param saveDirectory The local directory where the image should be saved.
     * @return The local file path of the saved image.
     * @throws IOException If an error occurs during the download or file saving
     *                     process.
     */
    private String downloadImage(String imageUrl, String saveDirectory) throws IOException {
        // Create the save directory if it does not exist
        Path directoryPath = Paths.get(saveDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Get the image file name from the URL and force it to be a .png file
        String imageName = getFileNameFromURL(imageUrl);
        String pngFileName = imageName.substring(0, imageName.lastIndexOf(".")) + ".png"; // Force .png extension

        // Create the local file path for the image with .png extension
        Path savePath = Paths.get(saveDirectory, pngFileName);

        // Open an input stream to the image URL and save it to the file
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the local path to the saved image
        return savePath.toString();
    }

    /**
     * Extracts the file name from the image URL.
     * 
     * @param url The image URL.
     * @return The file name extracted from the URL.
     */
    private String getFileNameFromURL(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
