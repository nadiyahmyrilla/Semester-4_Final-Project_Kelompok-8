package model;

import java.util.Date;

public class Penjualan {
    private int id;
    private int barangId;
    private int jumlah;
    private double total;
    private Date tanggal;

    public Penjualan(int id, int barangId, int jumlah, double total, Date tanggal) {
        this.id = id;
        this.barangId = barangId;
        this.jumlah = jumlah;
        this.total = total;
        this.tanggal = tanggal;
    }

    public int getId() { return id; }
    public int getBarangId() { return barangId; }
    public int getJumlah() { return jumlah; }
    public double getTotal() { return total; }
    public Date getTanggal() { return tanggal; }

    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setTotal(double total) { this.total = total; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
}
