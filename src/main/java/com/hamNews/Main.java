package com.hamNews;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArticleScraper articleScraper = new ArticleScraper();
        ArticleContentFetcher contentFetcher = new ArticleContentFetcher();
        ArticleStorage articleStorage = new ArticleStorage();

        String gridUrl = "https://lematin.ma/nation"; // URL for the article grid
        String category = "nation";
        int lastFetchedArticleId = articleStorage.getLastFetchedArticleIdByCategory(category);

        try {
            // Scrape articles from the grid
            List<Article> articles = articleScraper.scrapeArticleGrid(gridUrl, lastFetchedArticleId);
            boolean isFirstIteration = true;

            for (Article article : articles) {
                // Fetch the content for the article
                ArticleContent articleContent = contentFetcher.fetchContent(article.getUrl());

                // Store the article along with its content and published date
                articleStorage.storeArticle(article, articleContent.getContent(), articleContent.getPublishDate(),
                        category);

                if (isFirstIteration) {
                    articleStorage.updateLastFetchedArticleByCategory(category,
                            ArticleScraper.extractArticleIdFromUrl(article.getUrl()));
                    isFirstIteration = false; // Set the flag to false after the first iteration
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
