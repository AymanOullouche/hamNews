package com.hamNews.Model.Article;

public class ArticleSelect {
    private final String title;
    private final String url;
    private final String imageUrl;
    private final String imageName;

    public String getImageName() {
        return imageName;
    }

    private final String description;
    private final String publishDate;
    private final String content;

    private final String categorie;

    private boolean isDownloaded;

    public ArticleSelect(String title, String url, String imageUrl, String description, String publishDate,
            String content, String categorie, String imageName) {
        this.imageName = imageName;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publishDate = publishDate;
        this.content = content;
        this.categorie = categorie;

    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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

    public String getCategorie() {
        return categorie;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

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
