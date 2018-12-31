package main.java.com.anubiscode.libraryx.model.book;

import main.java.com.anubiscode.libraryx.model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookHandler {

    public BookHandler() {
        setupBookTable();
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
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean addBook(String id, String title, String author, String publisher) {
        String sql = "INSERT INTO BOOKS(bookid, title, author, publisher) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, publisher);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM BOOKS";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getString("bookid"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getBoolean("availability")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;
    }

    public Book getBookByID(String id) {
        Book book = null;
        String sql = "SELECT * FROM BOOKS WHERE BOOKID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book = new Book(
                        id,
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getBoolean("availability")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @SuppressWarnings("Duplicates")
    public boolean updateIssuedBook(String bookID, IssueType issueType) {
        String sql = "UPDATE BOOKS\n" +
                "SET AVAILABILITY = ? WHERE BOOKID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            switch (issueType) {
                case ISSUING:
                    ps.setBoolean(1, false);
                    break;
                case RETURNING:
                    ps.setBoolean(1, true);
                    break;
            }
            ps.setString(2, bookID);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
