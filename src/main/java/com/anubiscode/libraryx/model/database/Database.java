package main.java.com.anubiscode.libraryx.model.database;

import javafx.scene.control.Alert;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() {
        String db_url = "jdbc:h2:./database/library_db;create=true";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            AlertBox.show(Alert.AlertType.ERROR, null, "Error while connecting to database.");
            System.exit(-1);
        }
        return conn;
    }
}
