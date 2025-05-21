package controller;

import model.DetailPenjualan;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailPenjualanController {

    public List<DetailPenjualan> getDetailPenjualanHariIni() {
        List<DetailPenjualan> list = new ArrayList<>();
        String sql = "SELECT dp.*, b.nama AS nama_barang FROM detail_penjualan dp " +
                     "JOIN penjualan p ON dp.penjualan_id = p.id " +
                     "JOIN barang b ON dp.barang_id = b.id " +
                     "WHERE DATE(p.tanggal) = CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetailPenjualan dp = new DetailPenjualan(
                    rs.getInt("id"),
                    rs.getInt("penjualan_id"),
                    rs.getInt("barang_id"),
                    rs.getInt("jumlah"),
                    rs.getDouble("harga_satuan")
                );
                dp.setNamaBarang(rs.getString("nama_barang"));
                list.add(dp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
