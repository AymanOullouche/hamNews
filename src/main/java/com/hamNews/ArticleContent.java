package com.hamNews;

public class ArticleContent {
    private final String content;
    private final String publishDate;

    public ArticleContent(String content, String publishDate) {
        this.content = content;
        this.publishDate = publishDate;
    }

    public String getContent() {
        return content;
    }

    public String getPublishDate() {
        return publishDate;
    }
}