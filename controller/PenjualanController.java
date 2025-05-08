package controller;

import db.DatabaseConnection;
import model.Penjualan;

import java.sql.*;
import java.util.ArrayList;

public class PenjualanController {
    public void tambahPenjualan(Penjualan p) {
        String sql = "INSERT INTO penjualan (barang_id, jumlah, total, tanggal, jenis_pembayaran, status_hutang) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getBarangId());
            ps.setInt(2, p.getJumlah());
            ps.setDouble(3, p.getTotal());
            ps.setTimestamp(4, new Timestamp(p.getTanggal().getTime()));
            ps.setString(5, "tunai");
            ps.setString(6, "lunas");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Penjualan> getAllPenjualan() {
        ArrayList<Penjualan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM penjualan")) {
            while (rs.next()) {
                list.add(new Penjualan(
                    rs.getInt("id"),
                    rs.getInt("barang_id"),
                    rs.getInt("jumlah"),
                    rs.getDouble("total"),
                    rs.getTimestamp("tanggal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
