package main.java.com.anubiscode.libraryx.model.database;

import javafx.scene.control.Alert;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Database database;

    private static String db_url = "jdbc:h2:./database/library_db;create=true";
    private static Connection conn = null;
    private static PreparedStatement ps = null;

    public Database() {
        createConnection();
        setupBookTable();
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(db_url, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            AlertBox.show(Alert.AlertType.ERROR, null, "Error while connecting to database.");
            System.exit(-1);
        }
    }

    private void setupBookTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books(\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  bookID VARCHAR(255) NOT NULL,\n" +
                "  title VARCHAR(255),\n" +
                "  author VARCHAR(255),\n" +
                "  publisher VARCHAR(255),\n" +
                "  availability BOOLEAN DEFAULT TRUE,\n" +
                "  PRIMARY KEY (bookID)\n" +
                ")";
        try {
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
