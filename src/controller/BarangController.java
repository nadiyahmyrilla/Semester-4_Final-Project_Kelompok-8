// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.Barang;

import java.sql.*;
import java.util.ArrayList;

public class BarangController {

    // Mengambil semua data barang dari tabel 'barang'
    public ArrayList<Barang> getAllBarang() {
        ArrayList<Barang> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM barang")) {
            // Menelusuri semua baris hasil query dan menambahkan ke dalam list
            while (rs.next()) {
                list.add(new Barang(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getDouble("harga_beli"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok"), 
                    rs.getString("foto")
                ));
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL
            e.printStackTrace();
        }
        return list;
    }

    // Mengambil data barang berdasarkan nama
    public Barang getBarangByNama(String nama) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM barang WHERE nama=?")) {
            ps.setString(1, nama);
            try (ResultSet rs = ps.executeQuery()) {
                // Jika data ditemukan, kembalikan objek Barang
                if (rs.next()) {
                    return new Barang(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getDouble("harga_beli"),
                        rs.getDouble("harga_jual"),
                        rs.getInt("stok"),
                        rs.getString("foto") 
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Jika tidak ditemukan
    }

    //Menampilkan nama berdasar ID(update laporan)
    public String getNamaBarangById(int id) {
        String nama = "";
        String sql = "SELECT nama FROM barang WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nama = rs.getString("nama");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nama;
    }

    // Mengurangi stok barang setelah penjualan
    public void kurangiStok(int barangId, int jumlah) {
        String sql = "UPDATE barang SET stok = stok - ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, barangId);
            ps.executeUpdate(); // Eksekusi update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menambah stok barang setelah pembelian
    public void tambahStok(int barangId, int jumlah) {
        String sql = "UPDATE barang SET stok = stok + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, barangId);
            ps.executeUpdate(); // Eksekusi update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Memperbarui harga beli dan menghitung harga jual otomatis (15% margin)
    public void updateHargaBeli(int barangId, double hargaBeliBaru) {
        String sql = "UPDATE barang SET harga_beli = ?, harga_jual = ? WHERE id = ?";
        double hargaJualBaru = hargaBeliBaru * 1.15; // Harga jual = 115% dari harga beli
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, hargaBeliBaru);
            ps.setDouble(2, hargaJualBaru);
            ps.setInt(3, barangId);
            ps.executeUpdate(); // Eksekusi update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menambahkan data barang baru ke dalam database
    public boolean tambahBarang(String nama, double hargaBeli, double hargaJual, int stok, String foto) {
        String sql = "INSERT INTO barang (nama, harga_beli, harga_jual, stok, foto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setDouble(2, hargaBeli);
            ps.setDouble(3, hargaJual);
            ps.setInt(4, stok);
            ps.setString(5, foto);
            return ps.executeUpdate() > 0; // Mengembalikan true jika berhasil ditambahkan
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Gagal menambahkan
    }

}
