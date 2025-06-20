package model;

public class Barang {
    private int id;
    private String nama;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private String foto;

    public Barang(int id, String nama, double hargaBeli, double hargaJual, int stok, String foto) {
        this.id = id;
        this.nama = nama;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.foto = foto;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public double getHargaBeli() { return hargaBeli; }
    public double getHargaJual() { return hargaJual; }
    public int getStok() { return stok; }
    public String getFoto() { return foto; }

    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }
    public void setStok(int stok) { this.stok = stok; }
    public void setFoto(String foto) { this.foto = foto; }
}
