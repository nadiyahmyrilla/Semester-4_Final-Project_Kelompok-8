// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.Penjualan;

import java.sql.*;
import java.util.ArrayList;

public class PenjualanController {

    // Menambahkan data ke tabel penjualan dan mengembalikan ID penjualan yang baru dibuat
    public int tambahPenjualan(Penjualan p) {
        String sql = "INSERT INTO penjualan (tanggal, total, jenis_pembayaran, status_hutang, nama_pelanggan) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             // Mengaktifkan pengambilan ID otomatis (auto-generated key) setelah INSERT
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Mengatur nilai parameter untuk query SQL
            ps.setTimestamp(1, new Timestamp(p.getTanggal().getTime())); // Konversi tanggal ke format SQL
            ps.setDouble(2, p.getTotal()); // Total transaksi penjualan
            ps.setString(3, p.getJenisPembayaran()); // Jenis pembayaran (tunai/cicil)
            ps.setString(4, p.getStatusHutang()); // Status hutang (lunas/belum)
            ps.setString(5, p.getNamaPelanggan()); // Nama pelanggan

            ps.executeUpdate(); // Menjalankan perintah insert

            // Mengambil ID dari penjualan yang baru dimasukkan
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Mengembalikan ID penjualan
                }
            }

        } catch (SQLException e) {
            // Menangani error database jika terjadi
            e.printStackTrace();
        }
        return -1; // Jika gagal, kembalikan -1
    }

    // Menambahkan data ke tabel detail_penjualan berdasarkan ID penjualan
    public void tambahDetailPenjualan(int penjualanId, int barangId, int jumlah, double hargaSatuan) {
        String sql = "INSERT INTO detail_penjualan (penjualan_id, barang_id, jumlah, harga_satuan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Mengisi nilai parameter detail transaksi
            ps.setInt(1, penjualanId); // ID dari transaksi penjualan utama
            ps.setInt(2, barangId);    // ID barang yang dijual
            ps.setInt(3, jumlah);      // Jumlah unit barang
            ps.setDouble(4, hargaSatuan); // Harga satuan barang

            ps.executeUpdate(); // Jalankan perintah insert

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil seluruh data dari tabel penjualan
    public ArrayList<Penjualan> getAllPenjualan() {
        ArrayList<Penjualan> list = new ArrayList<>();
        String sql = "SELECT * FROM penjualan";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Menelusuri hasil query dan menambahkan data ke dalam list
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
        return list; // Mengembalikan seluruh data penjualan
    }

    // Mengambil data penjualan berdasarkan status hutang (misal: "LUNAS" atau "BELUM LUNAS")
    public ArrayList<Penjualan> getPenjualanByStatus(String status) {
        ArrayList<Penjualan> list = new ArrayList<>();
        String sql = "SELECT * FROM penjualan WHERE status_hutang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status); // Mengatur status hutang sebagai parameter

            try (ResultSet rs = ps.executeQuery()) {
                // Menambahkan data ke list jika ditemukan
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
        return list; // Mengembalikan daftar penjualan sesuai status
    }

    // Memperbarui status hutang suatu transaksi penjualan berdasarkan ID-nya
    public void updateStatusPenjualan(int idPenjualan, String status) {
        String sql = "UPDATE penjualan SET status_hutang = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status); // Status baru (misal: "LUNAS")
            ps.setInt(2, idPenjualan); // ID transaksi penjualan
            ps.executeUpdate(); // Jalankan perintah update

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
