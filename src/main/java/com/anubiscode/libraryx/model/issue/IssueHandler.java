package main.java.com.anubiscode.libraryx.model.issue;

import main.java.com.anubiscode.libraryx.model.database.Database;

import java.sql.*;

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

    public IssueWrapper getIssued(String bookID) {
        IssueWrapper issueWrapper = null;
        String sql = "SELECT * FROM ISSUE WHERE BOOKID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                issueWrapper = new IssueWrapper(
                        bookID,
                        rs.getString("memberID"),
                        rs.getTimestamp("issueTime"),
                        rs.getInt("renewCount")
                );
            }
            return issueWrapper;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }

    @SuppressWarnings("Duplicates")
    public IssueWrapper incrementRenewCount(String bookID) {
        String sql = "UPDATE ISSUE\n" +
                "SET RENEWCOUNT = RENEWCOUNT + 1, ISSUETIME = CURRENT_TIMESTAMP\n" +
                "WHERE BOOKID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookID);
            int i = ps.executeUpdate();
            if (i != 1) return null;
            return getIssued(bookID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean deleteIssued(String bookID) {
        String sql = "DELETE FROM ISSUE WHERE BOOKID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookID);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
