package main.java.com.anubiscode.libraryx.model.member;

import main.java.com.anubiscode.libraryx.model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberHandler {

    public MemberHandler() {
        setupMemberTable();
    }

    private void setupMemberTable() {
        String sql = "CREATE TABLE IF NOT EXISTS members(\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  memberID VARCHAR(255),\n" +
                "  name VARCHAR(255),\n" +
                "  phone VARCHAR(255),\n" +
                "  email VARCHAR(255),\n" +
                "  PRIMARY KEY (memberID)\n" +
                ")";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean addMember(String id, String name, String contactNo, String email) {
        String sql = "INSERT INTO MEMBERS(memberid, name, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, contactNo);
            ps.setString(4, email);
            int i = ps.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Member> getMembers() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM MEMBERS";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Member(
                        rs.getString("name"),
                        rs.getString("memberid"),
                        rs.getString("phone"),
                        rs.getString("email")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
