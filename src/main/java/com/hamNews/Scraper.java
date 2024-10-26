// package com.hamNews;

// import java.io.IOException;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;

// public class Scraper {
// public String fetchData(String url) throws IOException {
// Document doc = Jsoup.connect(url).get();

// // Select the main article section widget
// Element articleDesc = doc.select("div.article-desc").first();
// if (articleDesc != null) {
// // Return the outer HTML of the selected element
// return articleDesc.outerHtml();
// } else {
// return "No content found for the specified selector.";
// }

// }

// // Class to hold article data
// // public static class Article {
// // private String title;
// // private String url;
// // private String description;
// // private String imageUrl;

// // public Article(String title, String url, String imageUrl, String
// description)
// // {
// // this.title = title;
// // this.url = url;
// // this.description = description;
// // this.imageUrl = imageUrl;
// // }

// // public String getTitle() {
// // return title;
// // }

// // public String getUrl() {
// // return url;
// // }

// // public String getImageUrl() {
// // return imageUrl;
// // }

// // @Override
// // public String toString() {
// // return "Article{" +
// // "title='" + title + '\'' +
// // ", url='" + url + '\'' +
// // ", imageUrl='" + imageUrl + '\'' +
// // ", description='" + description + '\'' +
// // '}';
// // }

// // public String getDescription() {
// // return description;
// // }
// // }

// // public List<Article> fetchData(String url) throws IOException {
// // Document doc = Jsoup.connect(url).get();
// // List<Article> articles = new ArrayList<>();

// // // Select the main article section widget
// // for (Element articleElement : doc.select("div.matin-article-section-widget
// >
// // .article")) {
// // // Extract title
// // String title = articleElement.select("h2 a.article-title").text();
// // // Extract URL
// // String articleUrl = articleElement.select("h2
// a.article-title").attr("href");
// // // Extract image URL
// // String imageUrl = articleElement.select("div.article-image
// // img").attr("data-src");
// // String description = articleElement.select("a.article-body").text();

// // // Create a new Article object and add it to the list
// // articles.add(new Article(title, articleUrl, imageUrl, description));
// // }

// // return articles;
// // }

// public static void main(String[] args) {
// Scraper scraper = new Scraper();
// // try {
// // List<Article> articles = scraper.fetchData("https://lematin.ma/nation");
// // for (Article article : articles) {
// // System.out.println(article);
// // }
// // } catch (IOException e) {
// // e.printStackTrace();
// // }

// try {
// // Call fetchData and print the returned HTML
// String htmlContent = scraper.fetchData(
// "https://lematin.ma/nation/dgsn-hammouchi-nomme-de-nouveaux-responsables-dans-plusieurs-villes/248593");
// System.out.println(htmlContent);
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// }
