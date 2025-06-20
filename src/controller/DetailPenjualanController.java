// Merupakan file dari Controller
package controller;

import model.DetailPenjualan;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailPenjualanController {

    // Mengambil daftar detail penjualan untuk transaksi yang terjadi pada hari ini
    public List<DetailPenjualan> getDetailPenjualanHariIni() {
        List<DetailPenjualan> list = new ArrayList<>();
        // Query SQL untuk mengambil data detail penjualan yang bergabung dengan tabel penjualan dan barang, dengan filter tanggal transaksi yang sama dengan tanggal hari ini (CURDATE())
        String sql = "SELECT dp.*, b.nama AS nama_barang FROM detail_penjualan dp " +
                     "JOIN penjualan p ON dp.penjualan_id = p.id " +
                     "JOIN barang b ON dp.barang_id = b.id " +
                     "WHERE DATE(p.tanggal) = CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            // Menelusuri hasil query dan membuat objek DetailPenjualan untuk setiap baris hasil
            while (rs.next()) {
                DetailPenjualan dp = new DetailPenjualan(
                    rs.getInt("id"),
                    rs.getInt("penjualan_id"),
                    rs.getInt("barang_id"),
                    rs.getInt("jumlah"),
                    rs.getDouble("harga_satuan")
                );
                // Menambahkan nama barang dari hasil JOIN ke objek detail penjualan
                dp.setNamaBarang(rs.getString("nama_barang"));
                list.add(dp); // Menambahkan ke dalam list hasil
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
