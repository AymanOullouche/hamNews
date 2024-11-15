module MainJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.jsoup;

    opens com.hamNews.Vue to javafx.fxml;

    exports com.hamNews.Vue;
}
