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
            while (rs.next()) {
                list.add(new Barang(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getDouble("harga_beli"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
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
                if (rs.next()) {
                    return new Barang(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getDouble("harga_beli"),
                        rs.getDouble("harga_jual"),
                        rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mengurangi stok barang setelah penjualan
    public void kurangiStok(int barangId, int jumlah) {
        String sql = "UPDATE barang SET stok = stok - ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, barangId);
            ps.executeUpdate();
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
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateHargaBeli(int barangId, double hargaBeliBaru) {
        String sql = "UPDATE barang SET harga_beli = ?, harga_jual = ? WHERE id = ?";
        double hargaJualBaru = hargaBeliBaru * 1.15;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, hargaBeliBaru);
            ps.setDouble(2, hargaJualBaru);
            ps.setInt(3, barangId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
