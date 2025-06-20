// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.AuditLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogController {

    // Fungsi untuk mencatat log aktivitas ke dalam tabel audit_log
    public void catatLog(String aksi) {
        String sql = "INSERT INTO audit_log (aksi) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mengatur parameter aksi
            ps.setString(1, aksi);
            // Menjalankan perintah insert
            ps.executeUpdate();
        } catch (SQLException e) {
            // Menangani error SQL jika terjadi
            e.printStackTrace();
        }
    }

    // Fungsi untuk mengambil semua data log dari tabel audit_log
    public List<AuditLog> getSemuaLog() {
        List<AuditLog> list = new ArrayList<>();
        String sql = "SELECT * FROM audit_log ORDER BY tanggal DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Menelusuri hasil query dan menambahkan ke dalam list
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
