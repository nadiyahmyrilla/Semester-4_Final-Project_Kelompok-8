package model;

import java.util.Date;

public class Pemasukan {
    private int id;
    private Date tanggal;
    private double total;
    private String keterangan;

    public Pemasukan(int id, Date tanggal, double total, String keterangan) {
        this.id = id;
        this.tanggal = tanggal;
        this.total = total;
        this.keterangan = keterangan;
    }

    public int getId() { return id; }
    public Date getTanggal() { return tanggal; }
    public double getTotal() { return total; }
    public String getKeterangan() { return keterangan; }
}
