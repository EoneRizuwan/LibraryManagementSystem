package main.java.com.anubiscode.libraryx.model.issue;

import main.java.com.anubiscode.libraryx.model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IssueHandler {

    public IssueHandler() {
        setupIssueTable();
    }

    private void setupIssueTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ISSUE(\n" +
                "  bookID VARCHAR(255) NOT NULL,\n" +
                "  memberID VARCHAR(255) NOT NULL,\n" +
                "  issueTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                "  renewCount INT DEFAULT 0,\n" +
                "  PRIMARY KEY (bookID),\n" +
                "  FOREIGN KEY (bookID) REFERENCES BOOKS(BOOKID),\n" +
                "  FOREIGN KEY (memberID) REFERENCES MEMBERS(MEMBERID)\n" +
                ")";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addIssue(String bookID, String memberID) {
        String sql = "INSERT INTO ISSUE(bookid, memberid) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookID);
            ps.setString(2, memberID);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
