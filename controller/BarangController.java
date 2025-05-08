package controller;

import db.DatabaseConnection;
import model.Barang;

import java.sql.*;
import java.util.ArrayList;

public class BarangController {
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
}
