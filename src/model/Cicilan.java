package model;

import java.util.Date;

public class Cicilan {
    private int id;
    private int penjualanId;
    private Date tanggal;
    private double jumlahBayar;

    public Cicilan(int id, int penjualanId, Date tanggal, double jumlahBayar) {
        this.id = id;
        this.penjualanId = penjualanId;
        this.tanggal = tanggal;
        this.jumlahBayar = jumlahBayar;
    }

    public int getId() { return id; }
    public int getPenjualanId() { return penjualanId; }
    public Date getTanggal() { return tanggal; }
    public double getJumlahBayar() { return jumlahBayar; }
}
