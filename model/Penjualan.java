package model;

import java.util.Date;

public class Penjualan {
    private int id;
    private Date tanggal;
    private double total;
    private String jenisPembayaran;
    private String statusHutang;
    private String namaPelanggan;

    public Penjualan(int id, Date tanggal, double total, String jenisPembayaran, String statusHutang, String namaPelanggan) {
        this.id = id;
        this.tanggal = tanggal;
        this.total = total;
        this.jenisPembayaran = jenisPembayaran;
        this.statusHutang = statusHutang;
        this.namaPelanggan = namaPelanggan;
    }

    public int getId() { return id; }
    public Date getTanggal() { return tanggal; }
    public double getTotal() { return total; }
    public String getJenisPembayaran() { return jenisPembayaran; }
    public String getStatusHutang() { return statusHutang; }
    public String getNamaPelanggan() {return namaPelanggan; }

    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public void setTotal(double total) { this.total = total; }
    public void setJenisPembayaran(String jenisPembayaran) { this.jenisPembayaran = jenisPembayaran; }
    public void setStatusHutang(String statusHutang) { this.statusHutang = statusHutang; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
}
