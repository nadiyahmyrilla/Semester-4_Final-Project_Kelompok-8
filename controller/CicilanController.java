package controller;

import db.DatabaseConnection;
import model.Cicilan;

import java.sql.*;
import java.util.ArrayList;

public class CicilanController {
    public void tambahCicilan(Cicilan c) {
        String sql = "INSERT INTO cicilan (penjualan_id, tanggal, jumlah_bayar) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getPenjualanId());
            ps.setDate(2, new java.sql.Date(c.getTanggal().getTime()));
            ps.setDouble(3, c.getJumlahBayar());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Cicilan> getAllCicilan() {
        ArrayList<Cicilan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cicilan")) {
            while (rs.next()) {
                list.add(new Cicilan(
                    rs.getInt("id"),
                    rs.getInt("penjualan_id"),
                    rs.getDate("tanggal"),
                    rs.getDouble("jumlah_bayar")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
