// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.Pemasukan;

import java.sql.*;
import java.util.ArrayList;

public class PemasukanController {

    // Menambahkan data pemasukan baru ke dalam tabel 'pemasukan'
    public void tambahPemasukan(Pemasukan pm) {
        String sql = "INSERT INTO pemasukan (tanggal, total, keterangan) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Menetapkan nilai parameter: tanggal, total pemasukan, dan keterangan
            ps.setDate(1, new java.sql.Date(pm.getTanggal().getTime()));
            ps.setDouble(2, pm.getTotal());
            ps.setString(3, pm.getKeterangan());
            // Menjalankan perintah untuk menyimpan data ke database
            ps.executeUpdate();
        } catch (SQLException e) {
            // Menangani kesalahan SQL jika terjadi
            e.printStackTrace();
        }
    }

    // Mengambil seluruh data pemasukan dari tabel 'pemasukan'
    public ArrayList<Pemasukan> getAllPemasukan() {
        ArrayList<Pemasukan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pemasukan")) {
            // Menelusuri seluruh data hasil query dan menambahkannya ke dalam list
            while (rs.next()) {
                list.add(new Pemasukan(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getDouble("total"),
                    rs.getString("keterangan")
                ));
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL jika terjadi
            e.printStackTrace();
        }
        return list;
    }

    // Mengambil data pemasukan khusus untuk tanggal hari ini (CURDATE)
    public ArrayList<Pemasukan> getPemasukanHariIni() {
        ArrayList<Pemasukan> list = new ArrayList<>();
        String sql = "SELECT * FROM pemasukan WHERE DATE(tanggal) = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Menelusuri hasil query dan menambahkan data pemasukan ke list
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
            // Menangani kesalahan SQL jika terjadi
            e.printStackTrace();
        }
        return list;
    }
}
