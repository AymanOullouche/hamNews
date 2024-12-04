package com.hamNews;

import com.hamNews.Controler.ArticleController;
import com.hamNews.Model.Article.*;

import java.io.IOException;
import java.util.List;

    public class Main {
        public static void main(String[] args) {
            ArticleController articleController = new ArticleController();

            String gridUrl = "https://lematin.ma/monde"; // URL for the article grid
            String category = "monde";
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
                                articleController.extractArticleIdFromUrl(article.getUrl()));
                        isFirstIteration = false; // Set the flag to false after the first iteration
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
