package com.hamNews.Model.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hamNews.Model.Article.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ArticleScraper {
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

            // Create a new Article object and add it to the list
            articles.add(new Article(title, articleUrl, imageUrl, description));

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

}
