package controller;

import db.DatabaseConnection;
import model.AuditLog;

import java.sql.*;
import java.util.ArrayList;

public class AuditLogController {
    public void tambahLog(AuditLog log) {
        String sql = "INSERT INTO audit_log (aksi) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, log.getAksi());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AuditLog> getAllLog() {
        ArrayList<AuditLog> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM audit_log ORDER BY tanggal DESC")) {
            while (rs.next()) {
                list.add(new AuditLog(
                    rs.getInt("id"),
                    rs.getTimestamp("tanggal"),
                    rs.getString("aksi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
