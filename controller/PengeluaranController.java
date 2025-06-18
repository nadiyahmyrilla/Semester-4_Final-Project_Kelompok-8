// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.Pengeluaran;

import java.sql.*;
import java.util.ArrayList;

public class PengeluaranController {

    // Menambahkan data pengeluaran ke dalam tabel 'pengeluaran'
    public void tambahPengeluaran(Pengeluaran pl) {
        String sql = "INSERT INTO pengeluaran (tanggal, total, barang_id, jumlah) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mengisi parameter query SQL berdasarkan objek pengeluaran yang diberikan
            ps.setDate(1, new java.sql.Date(pl.getTanggal().getTime())); // Mengubah java.util.Date ke java.sql.Date
            ps.setDouble(2, pl.getTotal()); // Total biaya pengeluaran
            ps.setInt(3, pl.getBarangId()); // ID barang yang dikeluarkan
            ps.setInt(4, pl.getJumlah()); // Jumlah barang yang dikeluarkan
            ps.executeUpdate(); // Menjalankan perintah insert ke database
        } catch (SQLException e) {
            // Menangani error jika terjadi kesalahan koneksi atau query
            e.printStackTrace();
        }
    }

    // Mengambil semua data pengeluaran dari tabel 'pengeluaran'
    public ArrayList<Pengeluaran> getAllPengeluaran() {
        ArrayList<Pengeluaran> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pengeluaran")) {
            // Menelusuri semua data hasil query dan membuat objek Pengeluaran
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
            // Menangani error jika terjadi kesalahan query
            e.printStackTrace();
        }
        return list; // Mengembalikan daftar seluruh pengeluaran
    }

    // Mengambil data pengeluaran khusus untuk tanggal hari ini (CURDATE)
    public ArrayList<Pengeluaran> getPengeluaranHariIni() {
        ArrayList<Pengeluaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pengeluaran WHERE DATE(tanggal) = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Menelusuri hasil query dan memasukkan data ke dalam list
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
            // Menangani kesalahan koneksi atau query
            e.printStackTrace();
        }
        return list; // Mengembalikan daftar pengeluaran untuk hari ini
    }
}
