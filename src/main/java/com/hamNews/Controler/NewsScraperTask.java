package com.hamNews.Controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hamNews.Model.Article.Article;

import javafx.concurrent.Task;

public class NewsScraperTask extends Task<Void> {

    private final ArticleController articleController;

    public NewsScraperTask() {
        this.articleController = new ArticleController();
    }

    @Override
    protected Void call() throws Exception {
        ArrayList<String> categories = new ArrayList<>(
                Arrays.asList(
                        "economie",
                        "monde",
                        "societe",
                        "emploi",
                        "sports",
                        "automobile"));

        // Loop through each category
        for (String category : categories) {
            String gridUrl = "https://lematin.ma/" + category; // URL for the article grid
            int lastFetchedArticleId = articleController.getLastFetchedArticleIdByCategory(category);

            try {
                // Scrape articles from the grid
                List<Article> articles = articleController.scrapeArticleGrid(gridUrl, lastFetchedArticleId);
                boolean isFirstIteration = true;

                for (Article article : articles) {
                    // Fetch the content for the article
                    String articleContent = articleController.fetchContent(article.getUrl());

                    // Store the article along with its content and published date (from Article)
                    articleController.storeArticle(article, articleContent, category);

                    if (isFirstIteration) {
                        articleController.updateLastFetchedArticleByCategory(category,
                                ArticleController.extractArticleIdFromUrl(article.getUrl()));
                        isFirstIteration = false; // Set the flag to false after the first iteration
                    }
                }
            } catch (IOException e) {
                System.out.println("Error fetching articles for category: " + category);
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public ArticleController getArticleController() {
        return this.articleController;
    }
}