package com.hamNews.Controler;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamNews.Model.Article.ArticleSelect;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DownloadController {



    //    private void saveArticleAsJson(ArticleSelect article) throws IOException {
//        Gson gson = new Gson();
//        String json = gson.toJson(article); // Serialize the article object to JSON
//
//        // Create a directory to save the JSON if it doesn't exist
//        String jsonDirectory = "local_articles"; // You can change this directory
//        Path directoryPath = Paths.get(jsonDirectory);
//        if (!Files.exists(directoryPath)) {
//            Files.createDirectories(directoryPath);
//        }
//
//        // Define the JSON file path (you could use the article title or URL for
//        // uniqueness)
//        String jsonFileName = article.getTitle().replaceAll("\\s+", "_") + "_data.json"; // Use title to create a unique
//                                                                                         // file name
//        Path jsonFilePath = Paths.get(jsonDirectory, jsonFileName);
//
//        // Write the JSON data to the file
//        try (BufferedWriter writer = Files.newBufferedWriter(jsonFilePath)) {
//            writer.write(json);
//        }
//
//    }
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
        System.out.println("I'm downloading");

        // Mark the article as downloaded
        article.setDownloaded(!article.isDownloaded());

        if (article.isDownloaded()) {

            try {
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

                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Téléchargement réussi", "L'article a été téléchargés avec succès.");

            } catch (IOException e) {
                // Show error alert if something goes wrong during download
                LOGGER.severe("Erreur pendant le téléchargement de l'article : " + e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Échec du téléchargement", "Une erreur s'est produite lors du téléchargement de l'article.");
            }

        } else {
            // If the article is not downloaded, revert the download state
            // Example: updateButtonGraphic("Download");
            showAlert(Alert.AlertType.WARNING, "Téléchargement annulé", "Le téléchargement de l'article a été annulé.");
        }
    }


    public List<ArticleSelect> ArticleLoader() {
        List<ArticleSelect> loadedArticles = new ArrayList<>();
        Gson gson = new Gson();
        String jsonDirectory = "local_articles";
        Path jsonFilePath = Paths.get(jsonDirectory, "all_articles_data.json");

        if (Files.exists(jsonFilePath)) {
            try (FileReader reader = new FileReader(jsonFilePath.toFile())) {
                Type listType = new TypeToken<List<ArticleSelect>>() {}.getType();
                loadedArticles = gson.fromJson(reader, listType);
                System.out.println("Articles loaded successfully.");

                Collections.reverse(loadedArticles);
            } catch (IOException e) {
                LOGGER.severe("Error loading articles from JSON file: " + e.getMessage());
            }
        } else {
            System.out.println("No articles found to load.");
        }

        return loadedArticles;
    }


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
     * Shows a JavaFX alert dialog.
     *
     * @param type     The type of the alert (information, warning, error).
     * @param title    The title of the alert.
     * @param message  The message to display.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.showAndWait();
    }

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


    private String getFileNameFromURL(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
