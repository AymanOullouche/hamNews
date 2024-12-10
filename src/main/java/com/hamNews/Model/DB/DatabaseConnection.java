package com.hamNews.Model.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // private static final String DB_URL = "jdbc:mysql://" +
    // System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT")
    // + "/" + System.getenv("DB_NAME");
    // private static final String USER = System.getenv("DB_USER");
    // private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hamnews";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        System.out.println("All environment variables:");
        System.getenv().forEach((key, value) -> System.out.println(key + "=" + value));

        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
