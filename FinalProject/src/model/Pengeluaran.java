package model;

import java.util.Date;

public class Pengeluaran {
    private int id;
    private Date tanggal;
    private double total;
    private int barangId;
    private int jumlah;

    public Pengeluaran(int id, Date tanggal, double total, int barangId, int jumlah) {
        this.id = id;
        this.tanggal = tanggal;
        this.total = total;
        this.barangId = barangId;
        this.jumlah = jumlah;
    }
    
    public int getId() { return id; }
    public Date getTanggal() { return tanggal; }
    public double getTotal() { return total; }
    public int getBarangId() { return barangId; }
    public int getJumlah() { return jumlah; }

}
