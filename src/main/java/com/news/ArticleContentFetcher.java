package com.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ArticleContentFetcher {
    public ArticleContent fetchContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        // Select the main content section
        Element contentElement = doc.select("div.article-desc").first();
        String publishDate = doc.select("span.publishing-date").text();

        if (contentElement != null) {
            // Return both content and publish date
            return new ArticleContent(contentElement.outerHtml(), publishDate);
        } else {
            return new ArticleContent("No content found for the specified selector.", publishDate);
        }
    }
}