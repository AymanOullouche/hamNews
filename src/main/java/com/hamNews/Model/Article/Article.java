package com.hamNews.Model.Article;

public class Article {
    private final String title;
    private final String url;
    private final String imageUrl;
    private final String description;
    private final String publishDate;
    private boolean isDownloaded;


    public Article(String title, String url, String imageUrl, String description,String publishDate) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publishDate = publishDate;


    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public boolean isDownloaded() {return isDownloaded;}

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }


    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                // ", publishDate='" + publishDate + '\'' +
                '}';
    }


    // public String getPublishDate() {
    // return publishDate;
    // }
}
