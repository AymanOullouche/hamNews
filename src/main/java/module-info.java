module com.hamNews {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires com.google.gson;

    opens com.hamNews.Views to javafx.fxml;
    opens com.hamNews.Model.Article to com.google.gson;

    exports com.hamNews;
    exports com.hamNews.Views;
}
