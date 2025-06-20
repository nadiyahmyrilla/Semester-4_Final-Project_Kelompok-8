// Merupakan file dari Controller
package controller;

import db.DatabaseConnection;
import model.User;

import java.sql.*;

public class UserController {

    // Fungsi untuk melakukan autentikasi user berdasarkan username dan password
    public User authenticate(String username, String password) {
        // Query SQL untuk mencari user dengan username dan password tertentu
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection(); // Membuka koneksi ke database
             PreparedStatement ps = conn.prepareStatement(sql)) { // Mempersiapkan pernyataan SQL

            // Mengatur parameter query
            ps.setString(1, username); // Mengisi parameter pertama dengan username
            ps.setString(2, password); // Mengisi parameter kedua dengan password
            // Mengeksekusi query dan menyimpan hasilnya
            try (ResultSet rs = ps.executeQuery()) {
                // Jika user ditemukan, buat objek User dari hasil query
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),          // Ambil ID user
                        rs.getString("username"), // Ambil username
                        rs.getString("password")  // Ambil password
                    );
                }
            }
        } catch (SQLException e) {
            // Menampilkan error jika ada masalah saat mengakses database
            e.printStackTrace();
        }
        // Jika user tidak ditemukan, kembalikan null
        return null;
    }
}
