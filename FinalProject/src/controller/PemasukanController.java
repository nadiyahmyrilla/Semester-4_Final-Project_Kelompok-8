package controller;

import db.DatabaseConnection;
import model.Pemasukan;

import java.sql.*;
import java.util.ArrayList;

public class PemasukanController {
    public void tambahPemasukan(Pemasukan pm) {
        String sql = "INSERT INTO pemasukan (tanggal, total, keterangan) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(pm.getTanggal().getTime()));
            ps.setDouble(2, pm.getTotal());
            ps.setString(3, pm.getKeterangan());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pemasukan> getAllPemasukan() {
        ArrayList<Pemasukan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pemasukan")) {
            while (rs.next()) {
                list.add(new Pemasukan(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getDouble("total"),
                    rs.getString("keterangan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Perhitungan Khusus Untuk TGL saat ini(CURDATE)
    public ArrayList<Pemasukan> getPemasukanHariIni() {
    ArrayList<Pemasukan> list = new ArrayList<>();
    String sql = "SELECT * FROM pemasukan WHERE DATE(tanggal) = CURDATE()";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Pemasukan p = new Pemasukan(
                rs.getInt("id"),
                rs.getDate("tanggal"),
                rs.getDouble("total"),
                rs.getString("keterangan")
            );
            list.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
}
