// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.Cicilan;

import java.sql.*;
import java.util.ArrayList;

public class CicilanController {

    // Menambahkan data cicilan baru ke dalam tabel 'cicilan'
    public void tambahCicilan(Cicilan c) {
        String sql = "INSERT INTO cicilan (penjualan_id, tanggal, jumlah_bayar) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mengatur nilai parameter untuk query insert
            ps.setInt(1, c.getPenjualanId());
            ps.setDate(2, new java.sql.Date(c.getTanggal().getTime()));
            ps.setDouble(3, c.getJumlahBayar());
            // Menjalankan perintah insert ke database
            ps.executeUpdate();
        } catch (SQLException e) {
            // Menangani kesalahan SQL jika terjadi
            e.printStackTrace();
        }
    }

    // Mengambil daftar cicilan berdasarkan ID penjualan tertentu
    public ArrayList<Cicilan> getCicilanByPenjualan(int idPenjualan) {
        ArrayList<Cicilan> list = new ArrayList<>();
        String sql = "SELECT * FROM cicilan WHERE id_penjualan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mengatur parameter ID penjualan
            ps.setInt(1, idPenjualan);
            try (ResultSet rs = ps.executeQuery()) {
                // Menelusuri hasil query dan menambahkan ke dalam list
                while (rs.next()) {
                    Cicilan c = new Cicilan(
                        rs.getInt("id"),
                        rs.getInt("id_penjualan"),
                        rs.getDate("tanggal"),
                        rs.getDouble("jumlah")
                    );
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Fungsi untuk mendapatkan total cicilan berdasarkan penjualan
    public double getTotalCicilanByPenjualan(int idPenjualan) {
        double total = 0.0;
        String sql = "SELECT SUM(jumlah_bayar) FROM cicilan WHERE penjualan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mengatur parameter ID penjualan
            ps.setInt(1, idPenjualan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Mengambil hasil jumlah total dari kolom 'jumlah_bayar'
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
