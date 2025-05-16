package controller;

import db.DatabaseConnection;
import model.Pengeluaran;

import java.sql.*;
import java.util.ArrayList;

public class PengeluaranController {
    public void tambahPengeluaran(Pengeluaran pl) {
        String sql = "INSERT INTO pengeluaran (tanggal, total, barang_id, jumlah) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(pl.getTanggal().getTime()));
            ps.setDouble(2, pl.getTotal());
            ps.setInt(3, pl.getBarangId());
            ps.setInt(4, pl.getJumlah());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pengeluaran> getAllPengeluaran() {
        ArrayList<Pengeluaran> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pengeluaran")) {
            while (rs.next()) {
                list.add(new Pengeluaran(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getDouble("total"),
                    rs.getInt("barang_id"),
                    rs.getInt("jumlah")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Perhitungan Khusus Untuk TGL saat ini(CURDATE)
    public ArrayList<Pengeluaran> getPengeluaranHariIni() {
    ArrayList<Pengeluaran> list = new ArrayList<>();
    String sql = "SELECT * FROM pengeluaran WHERE DATE(tanggal) = CURDATE()";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Pengeluaran p = new Pengeluaran(
                rs.getInt("id"),
                rs.getDate("tanggal"),
                rs.getDouble("total"),
                rs.getInt("barang_id"),
                rs.getInt("jumlah")
            );
            list.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

}
