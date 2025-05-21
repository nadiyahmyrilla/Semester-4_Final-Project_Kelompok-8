package model;

public class Barang {
    private int id;
    private String nama;
    private double hargaBeli;
    private double hargaJual;
    private int stok;

    public Barang(int id, String nama, double hargaBeli, double hargaJual, int stok) {
        this.id = id;
        this.nama = nama;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public double getHargaBeli() { return hargaBeli; }
    public double getHargaJual() { return hargaJual; }
    public int getStok() { return stok; }

    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }
    public void setStok(int stok) { this.stok = stok; }
}
