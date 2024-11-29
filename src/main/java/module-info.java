module MainJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.jsoup;

    opens com.hamNews.Views to javafx.fxml;
    exports com.hamNews.Views to javafx.graphics;

    exports com.hamNews;
}
