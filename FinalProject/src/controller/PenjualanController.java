package controller;

import db.DatabaseConnection;
import model.Penjualan;

import java.sql.*;
import java.util.ArrayList;

public class PenjualanController {

    // Menambahkan data ke tabel penjualan dan mengembalikan ID-nya
    public int tambahPenjualan(Penjualan p) {
        String sql = "INSERT INTO penjualan (tanggal, total, jenis_pembayaran, status_hutang, nama_pelanggan) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, new Timestamp(p.getTanggal().getTime()));
            ps.setDouble(2, p.getTotal());
            ps.setString(3, p.getJenisPembayaran()); // "tunai" atau "hutang"
            ps.setString(4, p.getStatusHutang());    // "lunas" atau "belum lunas"
            ps.setString(5, p.getNamaPelanggan());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // ID penjualan yang baru dibuat
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Menambahkan data ke tabel detail_penjualan
    public void tambahDetailPenjualan(int penjualanId, int barangId, int jumlah, double hargaSatuan) {
        String sql = "INSERT INTO detail_penjualan (penjualan_id, barang_id, jumlah, harga_satuan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, penjualanId);
            ps.setInt(2, barangId);
            ps.setInt(3, jumlah);
            ps.setDouble(4, hargaSatuan);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil semua data penjualan
    public ArrayList<Penjualan> getAllPenjualan() {
        ArrayList<Penjualan> list = new ArrayList<>();
        String sql = "SELECT * FROM penjualan";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Penjualan(
                    rs.getInt("id"),
                    rs.getDate("tanggal"),
                    rs.getDouble("total"),
                    rs.getString("jenis_pembayaran"),
                    rs.getString("status_hutang"),
                    rs.getString("nama_pelanggan")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mendapatkan penjualan berdasarkan status hutang
    public ArrayList<Penjualan> getPenjualanByStatus(String status) {
        ArrayList<Penjualan> list = new ArrayList<>();
        String sql = "SELECT * FROM penjualan WHERE status_hutang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Penjualan p = new Penjualan(
                        rs.getInt("id"),
                        rs.getDate("tanggal"),
                        rs.getDouble("total"),
                        rs.getString("jenis_pembayaran"),
                        rs.getString("status_hutang"),
                        rs.getString("nama_pelanggan")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update status penjualan (misalnya dari "belum lunas" jadi "lunas")
    public void updateStatusPenjualan(int idPenjualan, String status) {
        String sql = "UPDATE penjualan SET status_hutang = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idPenjualan);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
